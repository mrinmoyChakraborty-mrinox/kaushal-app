# KAUSHAL Worker App — Codebase Context for ChatGPT

> Copy-paste this whole file into ChatGPT as context. Last scanned: 2026-09-30, branch `main`, HEAD `27bc852`.

You are an expert Android + Jetpack Compose developer. This is the existing codebase. Answer / code against THIS code, do not invent new architecture. Respect the constraints in Section 9.

---

## 1. What this project is

**KAUSHAL — Learn • Practice • Stay Safe**
Mobile safety-training for mining / steel / mica workers (Jharkhand focus).

Current milestone = **UI + navigation validation ONLY**. No real backend, no real AR, no real certs.

Repo: `https://github.com/mrinmoyChakraborty-mrinox/kaushal-app.git`
Root: `KAUSHAL_WORKER_APP`, module `:app`
Package: `com.kaushal.worker`, appId `com.kaushal.worker`
License: MIT
Branches: `main` (default), `origin/Banashree` (merged PR #1)

Root README is outdated — it describes a Unity AR / Flutter / FastAPI vision. The **real implemented code** is native Android Kotlin + Compose (see below). Trust `docs/KAUSHAL_WORKER_PRD.md` + source files over `README.md`.

Docs (source of truth for product rules):
- `docs/KAUSHAL_WORKER_PRD.md` (1343 lines, full PRD)
- `docs/KAUSHAL_WORKER_TASK.md` (1493 lines, task spec)
- `docs/KAUSHAL_IMPLEMENTATION_PLAN.md` (102 lines)
- `docs/REFERENCE_ASSETS.md`
- `reference/01_welcome.png`, `02_language.png`, `03_auth_flow.png`, `04_main_screens.png` (UI reference, not app data)

---

## 2. Tech stack (exact)

From `build.gradle.kts` / `app/build.gradle.kts` / `settings.gradle.kts`:

```
AGP 8.7.3, Kotlin 2.0.21, Compose plugin 2.0.21
compileSdk 35, targetSdk 35, minSdk 26, Java 17, jvmTarget 17
compose-bom 2024.12.01
androidx.core:core-ktx 1.15.0
androidx.activity:activity-compose 1.10.0
lifecycle-runtime-compose 2.8.7, lifecycle-viewmodel-compose 2.8.7
navigation-compose 2.8.5
compose ui, ui-tooling-preview, material3, material-icons-extended
```

No Firebase, no `google-services.json`, no Retrofit, no Hilt, no Room, no DataStore. State = ViewModel + StateFlow + `remember`.

`gradle.properties`: `android.useAndroidX=true`, `kotlin.code.style=official`
`app/src/main/res/values/themes.xml`: `Theme.Kaushal` parent `Theme.Material.Light.NoActionBar`, statusBar `#FFFDF9`
`AndroidManifest.xml`: single `MainActivity`, label `KAUSHAL`, theme `@style/Theme.Kaushal`

---

## 3. Source tree (excluding build/.gradle/.idea — those are accidentally committed, ignore them)

```
app/src/main/
  AndroidManifest.xml
  java/com/kaushal/worker/
    MainActivity.kt
    TemporaryAppViewModel.kt
    navigation/Routes.kt, AppNavigation.kt
    data/model/Models.kt
    ui/theme/Color.kt, Theme.kt, Type.kt
    ui/components/Common.kt
    screens/onboarding/OnboardingScreens.kt (Welcome, Language, TrainingBenefits)
    screens/auth/AuthScreens.kt (Login, LoginOtp, Register, RegisterOtp + private OtpScreen)
    screens/trainingprofile/TrainingProfileScreen.kt
    screens/home/DashboardScreen.kt
    screens/modules/ModuleScreens.kt (LearningModulesScreen, ModuleDetailScreen)
    screens/fieldbook/FieldBookScreen.kt
    screens/ar/ArTrainingScreen.kt
    screens/assessment/AssessmentScreen.kt
    screens/certificates/CertificatesScreen.kt
    screens/progress/ProgressScreen.kt
    screens/profile/ProfileScreen.kt
    screens/OtherScreens.kt (generic PlaceholderScreen, currently unused)
  res/drawable/welcome_background.png, language_background.png, training_background.png
  res/values/themes.xml
```

Total Kotlin source: 21 files. No tests.

---

## 4. Entry + state (read these first)

**`MainActivity.kt`:**
```kotlin
class MainActivity : ComponentActivity() {
  setContent { KaushalTheme { val appState: TemporaryAppViewModel = viewModel(); AppNavigation(appState) } }
}
```

**`TemporaryAppViewModel.kt` — THE session store:**
```kotlin
data class TemporaryUserProfile(name="", mobileNumber="", language="",
  userType=UserType.LEARNER, industrialSector="", subSector="",
  department="", role="", workerId="",
  workerVerificationStatus=NOT_SUBMITTED)
data class TemporarySession(isTemporarilyAuthenticated=false, isNewUser=false, profile=TemporaryUserProfile())
// methods: setLanguage(), setRegistration(name,mobile), authenticateExistingUser(mobile),
// authenticateNewUser(), updateProfile(language,sector,subSector,workerId) -> PENDING if workerId non-blank,
// logout() -> reset to TemporarySession()
```

**`data/model/Models.kt`:**
```kotlin
enum class UserType { LEARNER, WORKER }
enum class WorkerVerificationStatus { NOT_APPLICABLE, NOT_SUBMITTED, PENDING, VERIFIED }
data class TrainingModule(id,title,description,icon,hasAr=false)
val staticModules = listOf(
  fire/Fire & Explosion Response/🔥/hasAr=true,
  gas/Gas Leak & Confined Space/🫁/hasAr=true,
  machinery/Machinery Safety/⚙️,
  ppe/PPE & Workplace Safety/🦺/hasAr=true,
  laws/Mine Laws & Workers' Rights/⚖️,
  emergency/Emergency Response/🚨
)
```

---

## 5. Navigation graph (`navigation/Routes.kt` + `AppNavigation.kt`)

Routes object strings:
`welcome, language, training_benefits, login, login_otp, register, register_otp, training_profile, dashboard, learning_modules, module_detail/{moduleId}, field_book, ar_training/{moduleId}, assessment/{moduleId}, certificates, progress, profile`

Single `NavHost(startDestination=welcome)` in `AppNavigation(appState)`.

Flows implemented:
- New: Welcome(GET STARTED)->Language->Benefits(CONTINUE->Register)->Register(CREATE->RegisterOtp)->RegisterOtp(VERIFY->TrainingProfile, popUpTo Register)->TrainingProfile(CONTINUE->Dashboard, popUpTo Welcome)
- Existing: Welcome(I ALREADY HAVE)->Login(SEND OTP->login_otp?mobile=)->LoginOtp(VERIFY->Dashboard, popUpTo Welcome)
- Dashboard onNavigate: Learn->learning_modules, Progress->progress, Profile->profile, Field Book->field_book, AR Training->ar_training/fire, Certificates->certificates
- LearningModules->module_detail/{id}-> AR (ar_training/{id}) / Assessment (assessment/{id})
- Profile Logout -> logout() + navigate Welcome popUpTo(0)

Note: `userType`, `department`, `role` fields exist in ViewModel but TrainingProfileScreen currently only edits sector/subSector/workerId — department/role/userType pickers are missing vs PRD. LanguageScreen uses `en/hi/sat` (Santali) while PRD says `English/Hindi/Bengali` — known mismatch.

---

## 6. Screens — current behavior

- `OnboardingScreens.kt` (408 lines): Welcome (welcome_background + GET STARTED / I ALREADY HAVE), Language (en/hi/sat cards, CONTINUE enabled only if selected), Benefits (5 rows: hazard, emergency, PPE, AR, cert + CONTINUE). Local colors Orange #FFFF6800, Navy #FF12385F.
- `AuthScreens.kt` (252 lines): Login (10-digit mobile validation), shared private `OtpScreen` (6 boxes, Resend no-op, "UI testing only • no real SMS"), Register (mobile+name validation).
- `TrainingProfileScreen.kt` (298 lines): read-only name/mobile, Worker ID optional TextField, sector dialog (Mining/Steel Manufacturing/Mica Processing), dependent subSector (Open Cast/Underground, Hot Metal/Rolling Mills, Scrap Mining/Splitting), validation, `onContinue(language,sector,subSector,workerId)`.
- `DashboardScreen.kt`: greeting from `profile.name`, sector text, "No training modules assigned yet" + EXPLORE TRAINING, 6 QuickActions (Learning Modules, Progress, Certificates, Field Book, AR Training, Safety Passport placeholder).
- `ModuleScreens.kt`: LearningModules lists `staticModules` via `ModuleCard`; ModuleDetail shows icon/title/desc + START LEARNING (no-op) / AR TRAINING / TAKE ASSESSMENT / VIEW CERTIFICATE (locked) + "Certificate remains locked..." note.
- `FieldBookScreen.kt`: Layer 1 Book + Layer 2 Interactive cards, "Training assets are not connected...".
- `ArTrainingScreen.kt`: placeholder "AR training ... will be available soon" + COMING SOON button (no-op).
- `AssessmentScreen.kt`: "Assessment content coming soon / No real questions...".
- `CertificatesScreen.kt`: "No Certificates Yet".
- `ProgressScreen.kt`: "0% / No modules completed yet / Progress is calculated only from real state" + BottomNav.
- `ProfileScreen.kt`: shows all TemporaryUserProfile fields, "Verification pending" if workerId set, menu Language/Notification/Help (no-ops) + Logout.
- `Common.kt`: `KaushalButton`, `OutlinedKaushalButton`, `KaushalTextField`, `ScreenTopBar`, `WorkerHero` (👷 placeholder), `SelectionRow`, `BottomNavigationBar(Home/Learn/Progress/Profile)`, `ModuleCard`, `IndustrialBackdrop`.
- `ui/theme/`: Orange #FFFF6A00, Navy #FF0B3158, Cream #FFFFFCF7, Border #FFE5E8EC, Muted #FF687587, Green #FF2E9B63; `KaushalTheme` lightColorScheme; custom Typography.

---

## 7. How to build / run

```
./gradlew :app:assembleDebug  (or open in Android Studio, Sync Gradle)
adb install app/build/outputs/apk/debug/app-debug.apk
```
minSdk 26 (Android 8+). No env vars, no google-services.json needed.

---

## 8. Known gaps vs PRD (do not reintroduce as bugs)

- README describes Unity/Flutter/FastAPI — stale, ignore for Android task.
- `build/`, `.gradle/`, `.idea/` are tracked in git despite `.gitignore` — do not copy them into answers; suggest `git rm -r --cached` cleanup separately.
- Missing per PRD: Learner/Worker selector (Step 2), Department/Operation + Role pickers, notification permission prompt, Safety Passport screen, empty-state variant of LearningModules.
- OTP is local simulation; no Firebase Auth / Firestore / FCM / FastAPI in this phase.

---

## 9. Hard constraints for any new code (from PRD §4, §15, §30)

1. Use `TemporaryAppViewModel` local state only. NEVER add `fake Firebase UID / token / user`, `google-services.json`, Firebase Auth, Firestore, FastAPI auth in this phase.
2. NEVER hardcode mock user data: no `Ravi Kumar`, no `60%`, no `3/5 modules`, no fake certs/scores/employer/compliance. Empty states: `0%`, `No Certificates Yet`, `No training assigned yet`, `Verification pending`.
3. Static module catalog names ARE allowed (they are product content, not user data).
4. `WorkerVerificationStatus`: `PENDING` when Worker ID entered; `VERIFIED` reserved for future backend only.
5. Reuse `Routes`, `AppNavigation`, `Common.kt` components, `KaushalTheme` colors. One NavHost, one BottomNavigationBar. Keep screens scrollable, large touch targets, orange/navy identity.
6. Keep architecture ready for future Firebase/FastAPI/Firestore behind interfaces — do not hardwire backend singletons.

---

## 10. Prompt to give ChatGPT (example)

> Paste the above context, then add:
> "Task: [e.g. add Learner/Worker selector to TrainingProfileScreen wired to TemporaryAppViewModel, keep PENDING logic]. Return unified diff / full file contents for affected Kotlin files only, following existing style in Common.kt. Do not add Firebase dependencies."

---

## Appendix: full file paths to attach if ChatGPT supports file upload

Attach these 12 files for 95% context: `MainActivity.kt`, `TemporaryAppViewModel.kt`, `navigation/Routes.kt`, `navigation/AppNavigation.kt`, `data/model/Models.kt`, `ui/components/Common.kt`, `ui/theme/Color.kt`, `ui/theme/Theme.kt`, `screens/auth/AuthScreens.kt`, `screens/onboarding/OnboardingScreens.kt`, `screens/trainingprofile/TrainingProfileScreen.kt`, `screens/home/DashboardScreen.kt`. Rest are small placeholders as described in §6.
