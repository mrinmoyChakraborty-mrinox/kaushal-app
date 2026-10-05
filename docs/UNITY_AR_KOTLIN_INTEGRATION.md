# UNITY AR ↔ KOTLIN INTEGRATION (implemented)

This document describes what was actually implemented to stitch the native
Kotlin worker app with the real Unity AR module. Host-driven model: Kotlin
owns questions/answers/feedback/score/progress/assessment/navigation;
Unity owns the AR camera, placement, 3D models, VFX.

## Sources

- Kotlin repository: `D:\KAUSHAL\kaushal-app` (branch `main`)
- Unity project: `D:\My project` (Unity 6000.6.3f1)
- Unity library source path:
  `D:\My project\Builds\UnityLibraryExport\unityLibrary`
- Kotlin integrated copy:
  `D:\KAUSHAL\kaushal-app\libs\unityLibrary`
  (also `libs/shared/` for the export's `shared/*.gradle` scripts, which the
  library's `build.gradle` applies via `../shared/...`)

The copy is byte-identical to the export except for the reconciled items
listed under "Reconciled integration differences" below. The Unity project
itself (assets, scenes, C# code, scenario data) was not modified.

## Reconciled integration differences

1. **Build inputs Unity's export expects but does not ship.**
   The exported `buildIl2Cpp` Gradle task references two files that are
   absent from `Builds\UnityLibraryExport` (they exist in the editor install
   and in Unity's own build artifacts for this exact project/version):
   - `src/main/Il2CppOutputProject/usymtool.exe`
     → copied from
     `C:\Program Files\Unity\Hub\Editor\6000.6.3f1\Editor\Data\Tools\usymtool.exe`
   - `src/main/Il2CppOutputProject/Source/il2cppOutput/data/`
     (`Metadata/global-metadata.dat`, `Resources/mscorlib.dll-resources.dat`)
     → copied from
     `D:\My project\Library\Bee\artifacts\Android\il2cppOutput\data`
   Without these, the export's own `buildIl2Cpp` task cannot run. The
   generated `libil2cpp.so` (56.7 MB, arm64) is produced by the real task;
   no `.so` file was hand-copied.
2. **Library manifest** (`libs/unityLibrary/src/main/AndroidManifest.xml`):
   removed the `UnityPlayerActivity` declaration (the host embeds
   `UnityPlayerForActivityOrService` inside `MainActivity`; Unity must not
   own navigation), dropped `tools:replace` for label/icon/theme and the
   `android:label="@string/app_name"` / `android:icon="@mipmap/app_icon"`
   references (those resources only exist in the reference launcher, so the
   merged app keeps the KAUSHAL branding), and dropped the conflicting
   `android:allowBackup`. Unity runtime meta-data and ARCore meta-data kept.
   The same activity removal was applied to `xrmanifest.androidlib` and its
   `package=` attribute removed (namespace is in its `build.gradle`, AGP 8
   rejects the manifest attribute).
3. **`api fileTree` for `unity-classes.jar`** (was `implementation` in the
   export): the host app compiles against the `UnityPlayer` API, so the jar
   must be visible to consumers. AARs stay `implementation`.
4. **`implementation(name: ..., ext: 'aar')`** entries kept; a matching
   `flatDir` repository was added to the host `settings.gradle.kts`.

## Gradle changes (host)

- `settings.gradle.kts`: `include(":unityLibrary")` → `libs/unityLibrary`,
  `include(":unityLibrary:xrmanifest.androidlib")` → its subdirectory,
  plus `flatDir { dirs("libs/unityLibrary/libs") }`.
- Root `build.gradle.kts`: added `id("com.android.library") version "8.7.3"
  apply false` (the library's Groovy `apply plugin` resolves against it).
- Root `gradle.properties`: added the `unity.*` keys the library's build
  script reads (`unityStreamingAssets=`, `unity.installInBuildFolder=false`,
  `unity.androidSdkPath`, `unity.androidNdkPath` → the Unity install paths).
- Wrapper now points at **Gradle 9.3.0** (already present in the local
  wrapper cache; matches the Gradle this machine previously used for this
  repo). AGP 8.7.3 / Kotlin 2.0.21 / Java 17 are unchanged.
- `local.properties` (gitignored, machine-local): `sdk.dir` → the local
  Android SDK. The `platforms/android-36` folder was copied from the Unity
  SDK into that SDK because the library declares `compileSdk 36` (the user
  SDK only had `android-35` and `android-36.1`).
- `app/build.gradle.kts`:
  - `implementation(project(":unityLibrary"))`
  - `minSdk 26 → 29` (the Unity library declares `minSdk 29`; manifest
    merger rejects anything lower)
  - `ndk { abiFilters += "arm64-v8a" }` (Unity ships arm64 only)
  - `packaging.jniLibs.useLegacyPackaging = true` (consistent with the
    library manifest's `extractNativeLibs=true`)
  - `androidResources.noCompress` for Unity data extensions
    (`.unity3d .ress .resource .obb .bundle .unityexp`)
  - Java 17 / Kotlin / Compose unchanged.

## Android changes (host app)

- `AndroidManifest.xml`: added `android.permission.CAMERA`; added the
  standard config-change list to `MainActivity` so rotation never recreates
  the Activity while the Unity runtime is attached.
- `MainActivity.kt`: forwards the Activity lifecycle to `UnityArHost`
  (onStart/onStop/onResume/onPause/onWindowFocusChanged/
  onConfigurationChanged/onLowMemory/onTrimMemory/onDestroy). Forwarding is
  a no-op until the AR screen creates the runtime.
- New `com.kaushal.ar.bridge.UnityBridge`
  (`app/src/main/java/com/kaushal/ar/bridge/UnityBridge.kt`):
  object with `@JvmStatic fun onUnityEvent(json: String)`, parses the
  `{"type","payload"}` envelope with `JSONObject`, dispatches to a
  thread-safe listener list on the main thread, never crashes on malformed
  JSON.
- New `com.kaushal.ar.UnityArHost`: owns the single
  `UnityPlayerForActivityOrService` instance (created once per Activity via
  `ensurePlayer`), attaches/detaches its view, sends commands via static
  `UnityPlayer.UnitySendMessage("KAUSHALRuntime", "OnHostCommand", json)`,
  forwards lifecycle.
- New `com.kaushal.ar.ArSessionController`: Kotlin-owned session state
  machine. Sends `OPEN_AR`, retries it every 1.5 s until a real `AR_READY`
  arrives (Unity silently drops messages sent before the runtime exists),
  then sends `LOAD_STEP`. Handles `STEP_READY`, `STEP_VISUAL_COMPLETE`,
  `OBJECT_INTERACTED` (Kotlin acknowledgment/logging only), `AR_ERROR`
  (Kotlin error state, no crash), `AR_EXITED`. `EXIT_AR` is sent on Back /
  "BACK TO DECISION". Also implements `RESET_STEP`, `HIDE_AR`,
  `ANSWER_SELECTED`, `SHOW_CONSEQUENCE` senders; they fire only where the
  content supports them (see below).
- New `com.kaushal.ar.bridge.ArScenarioMapping`: the only Kotlin-ID →
  Unity-step translation. Verified mappings (from
  `Assets/KAUSHAL/Data/Scenarios/Scenario_Fire_Escape.asset`, module
  `fire_explosion`, scenario `fire_emergency_escape`):
  - `m1_extinguisher` → `select_extinguisher` (hero path)
  - `m1_smoke_equipment` → `notice_early_warning`
  - `m1_emergency_route` → `identify_exit`
  - `m1_shortcut_route` → `identify_exit`
  - `m1_fire_emergency_priority` → `reach_assembly`
  - `m1_flammable_atmosphere` → **unmapped** (no defensible fire-module
    visual step in Unity; the AR screen shows a Kotlin-owned "AR scene
    unavailable for this scenario" state instead of sending a wrong step).
- `screens/ar/ArTrainingScreen.kt` rewritten: `ScreenTopBar("Practice AR")`
  + Unity viewport (`AndroidView` hosting the single Unity view) + small
  Kotlin-owned status area + Back. Camera permission gate: not granted →
  Kotlin error/request state, Unity is never created. Unknown scenario →
  unavailable state, nothing sent to Unity. On entering with a mapped step,
  OPEN_AR carries
  `{command, moduleId, scenarioId, stepId, sector: "mining",
  variant: "underground", language}` where language is the real Kotlin
  session language (`en`/`hi`/`sat`).
- Route: `ar_training/{moduleId}?scenarioId=&chapterId=&screenId=&`
  with defaults, built by the new `Routes.arTraining(...)` helper.
  `Module1StoryPlayerScreen.onPracticeAr` now passes
  `(scenarioId, screenId)`; navigation adds `moduleId="fire"` + `chapterId`.
  Dashboard and Module-Detail entries navigate with moduleId only
  (scenario-less → honest unavailable state until the hero chapter drives it).
- `Module1Content.kt` was **not** modified. The story question engine,
  answer checking, feedback and progress paths are byte-identical.

## Content decisions (why two commands do not auto-fire)
- `ch04_s03` asks what Raju must *consider* before using an extinguisher
  (training/authorization). The content does not assert that answering
  correctly extinguishes the fire, so `SHOW_CONSEQUENCE fire_suppressed` is
  implemented but intentionally not auto-sent on that screen.
- The ch04 options have no asset-level mapping, so `ANSWER_SELECTED` is
  implemented but intentionally not auto-sent. `OBJECT_INTERACTED` events
  (e.g. `PROP_FIRE_EXTINGUISHER`, `PROP_ELECTRICAL_PANEL`) are received,
  logged and shown in the Kotlin status area; Kotlin correctness stays on
  the story screen.

## Build instructions

Prerequisites on this machine: Unity Hub Editor 6000.6.3f1 (OpenJDK 17,
NDK r27c) at the default install path, Android SDK at
`C:\Users\ASUS\AppData\Local\Android\Sdk` (with `local.properties`
`sdk.dir` set accordingly), JDK 17 for Gradle:

```powershell
$env:JAVA_HOME = "C:\Program Files\Unity\Hub\Editor\6000.6.3f1\Editor\Data\PlaybackEngines\AndroidPlayer\OpenJDK"
.\gradlew.bat :app:assembleDebug
```

Result: `app\build\outputs\apk\debug\app-debug.apk` (~196 MB).
First build compiles IL2CPP for arm64 (~6–7 min on this machine); later
builds reuse it. Install on an arm64 device:

```powershell
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

Device test instructions (hero path): open KAUSHAL → Learn → Fire &
Explosion Response → Start → Chapter 4 → screen 3 ("Should Raju use the
extinguisher?") → PRACTICE AR → allow camera → Unity viewport shows the
electrical box, extinguisher, fire/smoke → tap Place → tap extinguisher →
Back → answer the Kotlin question → feedback → Continue. Watch logcat tags
`KAUSHAL_AR` / `KAUSHAL_UNITY` for the OPEN_AR → AR_READY → LOAD_STEP →
STEP_READY → (OBJECT_INTERACTED) → EXIT_AR → AR_EXITED sequence.

## 3D preview fallback (host render modes)

Unity now supports two render modes behind the unchanged host command set:

- AR � real ARCore session, plane detection, tap-to-place (unchanged).
- PREVIEW � no ARCore, no planes, no camera permission required. The same
  ScenarioManager/AssetCatalog step spawning renders on a virtual floor;
  a dedicated preview camera auto-frames the spawned bounds and supports
  one-finger orbit, pinch/mouse-wheel zoom, two-finger/middle-mouse pan,
  double-tap/double-click reset.

Implementation (Unity project, D:\My project):

- KAUSHAL/Core/Runtime/HostRenderMode.cs � Unknown/AR/PREVIEW enum.
- KAUSHALHostReceiver � resolves the mode on OPEN_AR with a bounded wait
  for bootstrap startup (never indefinite), reports it in AR_READY
  (payload.mode: "ar" or "preview"), re-sends LOAD_STEP handling
  unchanged, RESET_STEP respawns + reframes in preview (clear-only in AR),
  HIDE_AR/EXIT_AR work in both modes, OBJECT_INTERACTED fires in both.
- KaushalBootstrap � exposes StartupComplete/IsARAvailable, suppresses
  its own unavailable panel in host mode, skips its camera-permission wait
  once the host takes over, EnterPreviewForHost() (session off, preview
  stage, no panels). Standalone (non-host) flow is untouched.
- KAUSHAL/AR/Runtime/KAUSHALPreviewStage.cs � preview activation: virtual
  floor/lighting via the existing simulation stage, swaps the standalone
  aim-rig for PreviewCameraRig, anchors ARContentRoot at the origin.
- KAUSHAL/AR/Runtime/PreviewCameraRig.cs � orbit/zoom/pan/reset + mouse,
  size-aware clamps, floor clamp, FrameOn(Bounds).
- ScenarioManager.ShowStepVisuals � in host preview, anchors content and
  frames the camera on the spawned step; exposes SpawnedStepObjects.
- ARPlacementManager � placement input/reticle/content pinch-scaling are
  skipped in host preview (real AR path unchanged).

Kotlin (no contract change):

- ArSessionController captures mode from the AR_READY payload and shows
  it as an AR/PREVIEW chip in the AR status bar (extra payload fields are
  ignored by the parser otherwise).
- ArTrainingScreen: camera denial no longer dead-ends � a
  CONTINUE IN 3D PREVIEW action proceeds into Unity without the permission;
  Unity falls back to preview and reports mode=preview.

Validation performed (see D:\My project\Logs\host_preview_validation.txt):

- Headless editor playmode: OPEN_AR ? AR_READY(mode=preview) ?
  LOAD_STEP(select_extinguisher) ? STEP_READY with the REAL electrical panel
  and REAL extinguisher (RealAssetMarker-verified, not procedural) + fire /
  smoke particles ? RESET_STEP respawns ? EXIT_AR clears state ? catalog
  still resolves all three REAL props. All PASS.
- Fresh library export re-integrated, APK rebuilt, static checks repeated:
  real FBX-derived names, scenario/step IDs, new preview code strings and the
  KAUSHALRuntime endpoint all present in the APK player data.
- Touch gestures and on-device AR placement were NOT tested (no device).
