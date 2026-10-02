# KAUSHAL — UI Bug Fixes, Notification Popup & Full Language Switching

## Purpose

This file is the implementation task for the current `completed kaushal` Android/Jetpack Compose project.

The goal is to fix the existing navigation/UI issues and make the selected language apply to the **entire worker app**, not only the language-selection screen.

---

# 1. Current Problems Found

## Problem A — Bottom navigation disappears after opening Learn and Profile

### Current cause

The dashboard uses:

```kotlin
Scaffold(
    bottomBar = {
        BottomNavigationBar(
            current = "Home",
            onNavigate = onNavigate
        )
    }
)
```

But `LearningModulesScreen()` and `ProfileScreen()` are currently plain `Column(...)` screens, so the bottom navigation is not rendered there.

### Files to change

```text
app/src/main/java/com/kaushal/worker/screens/modules/ModuleScreens.kt
app/src/main/java/com/kaushal/worker/screens/profile/ProfileScreen.kt
app/src/main/java/com/kaushal/worker/navigation/AppNavigation.kt
```

### Required change

Change the Learn screen to use:

```text
Scaffold
 ├── content = Learning Modules
 └── bottomBar = BottomNavigationBar(current = "Learn")
```

Change the Profile screen to use:

```text
Scaffold
 ├── content = Profile
 └── bottomBar = BottomNavigationBar(current = "Profile")
```

Do **not** create another copy of the bottom-navigation component. Reuse:

```kotlin
BottomNavigationBar(...)
```

from:

```text
app/src/main/java/com/kaushal/worker/ui/components/Common.kt
```

### Required navigation callback

`LearningModulesScreen` should receive:

```kotlin
onNavigate: (String) -> Unit
```

in addition to the existing back/module callback.

`ProfileScreen` should also receive:

```kotlin
onNavigate: (String) -> Unit
```

Then both screens must pass the callback into:

```kotlin
BottomNavigationBar(...)
```

### AppNavigation changes

In:

```text
app/src/main/java/com/kaushal/worker/navigation/AppNavigation.kt
```

update the `Routes.LearningModules` and `Routes.Profile` destinations so they pass a common navigation handler.

The navigation mapping must remain:

```text
Home     -> Dashboard
Learn    -> LearningModules
Progress -> Progress
Profile  -> Profile
```

### Expected result

```text
Dashboard
   ↓ Learn
Learning Modules
   ↓
Bottom navigation is STILL visible
   ↓ Profile
Profile
   ↓
Bottom navigation is STILL visible
```

The selected tab must also be visually highlighted correctly:

```text
Dashboard  -> Home selected
Learn      -> Learn selected
Progress   -> Progress selected
Profile    -> Profile selected
```

---

# 2. Insert Profile Icon in Dashboard

## File

```text
app/src/main/java/com/kaushal/worker/screens/home/DashboardScreen.kt
```

## Current issue

The dashboard header currently has only the notification button:

```kotlin
IconButton(onClick = { }) {
    Icon(
        Icons.Default.NotificationsNone,
        ...
    )
}
```

There is no profile icon in the header.

## Required UI

Add a profile icon beside the notification icon.

Recommended order:

```text
[ Greeting / Name                         ]
                                    [ 🔔 ] [ 👤 ]
```

Use:

```kotlin
Icons.Default.NotificationsNone
Icons.Default.Person
```

## Required behavior

Notification icon:

```text
tap → notification popup
```

Profile icon:

```text
tap → Profile screen
```

The profile icon should call:

```kotlin
onNavigate("Profile")
```

Do not create a second profile route.

---

# 3. Notification Icon Must Show a Popup

## File

```text
app/src/main/java/com/kaushal/worker/screens/home/DashboardScreen.kt
```

## Current issue

The current notification click handler is empty:

```kotlin
IconButton(onClick = { })
```

## Required implementation

Use Compose local UI state:

```kotlin
var showNotifications by rememberSaveable { mutableStateOf(false) }
```

Notification button:

```kotlin
IconButton(
    onClick = { showNotifications = true }
)
```

When `showNotifications == true`, show a Material 3 dialog.

Recommended structure:

```text
┌──────────────────────────────┐
│ Notifications            X   │
│                              │
│ No new notifications         │
│                              │
│                         OK   │
└──────────────────────────────┘
```

Use `AlertDialog`.

### Important

Do **not** create fake training notifications or fake backend records.

For the current UI/testing phase, an empty state is enough:

```text
No new notifications
```

Later the same dialog can display real notification data from the backend.

The dialog text itself must also be localized once the language system is implemented.

---

# 4. Remove Emergency Response from Learning Modules

## Main file

```text
app/src/main/java/com/kaushal/worker/data/model/Models.kt
```

## Current problem

`staticModules` currently contains:

```kotlin
TrainingModule(
    "emergency",
    "Emergency Response",
    "Know what to do when a workplace emergency occurs.",
    "🚨"
)
```

## Required change

Delete the entire Emergency Response entry from `staticModules`.

The final list must contain only:

```text
1. Fire & Explosion Response
2. Gas Leak & Confined Space
3. Machinery Safety
4. PPE & Workplace Safety
5. Mine Laws & Workers' Rights
```

Keep the existing AR flags exactly as they are unless a separate requirement changes them.

### Verification

Search the source tree after the change:

```text
Emergency Response
"emergency"
TrainingModule("emergency"
```

There should be no remaining Learning Module reference to the removed standalone Emergency Response module.

Do not remove emergency-related wording that belongs to valid training content such as:

```text
Fire & Explosion Response
```

or other safety descriptions.

---

# 5. Fix Language Selection — English / Hindi / Santali

## Current issue

The language page already displays three options:

```text
English
Hindi
Santali
```

and stores:

```text
en
hi
sat
```

through `TemporaryAppViewModel.setLanguage()`.

However, almost all application text is hard-coded English.

Therefore selecting Hindi/Santali currently changes the stored code but does **not** translate the application.

---

# 6. Required Language Architecture

The selected language must be an application-level state.

Supported languages:

```text
English → en
Hindi   → hi
Santali → sat-Olck
```

For Santali, use the Ol Chiki script because the current UI already uses Ol Chiki text.

Do not solve this by adding many `if (language == "hi")` / `if (language == "sat")` checks inside every screen.

Use Android string resources + a single application locale mechanism.

---

# 7. Create Localized Android String Resources

## Create / update

```text
app/src/main/res/values/strings.xml
app/src/main/res/values-hi/strings.xml
app/src/main/res/values-b+sat+Olck/strings.xml
```

### `values/strings.xml`

Contains the English source strings.

### `values-hi/strings.xml`

Contains Hindi translations.

### `values-b+sat+Olck/strings.xml`

Contains Santali translations in Ol Chiki.

---

# 8. Move Hard-coded UI Text into `strings.xml`

The following screens currently contain hard-coded English and must be converted to `stringResource(...)`.

## Dashboard

File:

```text
screens/home/DashboardScreen.kt
```

Move strings such as:

```text
Good morning,
KAUSHAL Learner
Learner
Start Your Safety Learning Journey
No training modules assigned yet.
EXPLORE TRAINING
Quick Actions
Learning Modules
My Progress
Certificates
Field Book
AR Training
Safety Passport
Notifications
Profile
Notifications dialog text
```

into resources.

---

## Learning Modules

File:

```text
screens/modules/ModuleScreens.kt
```

Move:

```text
Learning Modules
Your safety training modules
Available product content is shown below...
START LEARNING
AR TRAINING
TAKE ASSESSMENT
VIEW CERTIFICATE
Certificate remains locked...
```

into resources.

The module title and description must also become language-aware.

For example, do not keep this as only:

```kotlin
val staticModules = listOf(
    TrainingModule("fire", "Fire & Explosion Response", ...)
)
```

Instead, keep stable module IDs in the model and resolve the visible title/description from localized resources.

Recommended model direction:

```text
id
titleResId
descriptionResId
icon
hasAr
```

This prevents the module list from staying English when Hindi/Santali is selected.

---

## Profile

File:

```text
screens/profile/ProfileScreen.kt
```

Move all visible strings into resources:

```text
My Profile
Your Name
Learner
Mobile Number
Worker ID
Not entered
Not added
Status
Verification pending
Industrial Sector
Sub-Sector
Department / Operation
Role
Language
Not selected
Notification Settings
Help & Support
Logout
```

---

## Training Profile

File:

```text
screens/trainingprofile/TrainingProfileScreen.kt
```

Move all visible labels and buttons into localized resources, including:

```text
Worker ID (Optional)
CONTINUE
CLOSE
```

and every other visible English label in this file.

---

## Authentication

File:

```text
screens/auth/AuthScreens.kt
```

Localize all visible strings including:

```text
Welcome back!
SEND OTP
Register
Create Your KAUSHAL Account
CREATE ACCOUNT
Already have an account? Login
Didn't receive code? Resend OTP
```

Also localize labels/placeholders used by text fields.

---

## Progress

File:

```text
screens/progress/ProgressScreen.kt
```

Localize:

```text
0%
No modules completed yet
Progress is calculated only from real local/backend state.
```

---

## Certificates

File:

```text
screens/certificates/CertificatesScreen.kt
```

Localize:

```text
No Certificates Yet
Complete module assessments to earn your certificates.
```

---

## Field Book

File:

```text
screens/fieldbook/FieldBookScreen.kt
```

Localize all visible strings.

---

## AR Training

File:

```text
screens/ar/ArTrainingScreen.kt
```

Localize all visible strings.

---

## Assessment

File:

```text
screens/assessment/AssessmentScreen.kt
```

Localize all visible strings.

---

## Onboarding

File:

```text
screens/onboarding/OnboardingScreens.kt
```

Localize:

```text
Welcome to
KAUSHAL
Learn • Practice • Stay Safe
GET STARTED
I ALREADY HAVE AN ACCOUNT

What language do you speak?

I speak English
मैं हिन्दी बोलता/बोलती हूँ
[existing Santali text]

Here's what you'll be able to do after your training!

Spot hazards in your workplace
Respond correctly to emergencies
Choose the right PPE and equipment
Practice safely with AR simulations
Pass your safety assessment & get certified

CONTINUE
```

The language option labels can remain written in their native language if desired, but all explanatory UI surrounding them must change with the selected app language.

---

# 9. Create a Single App Language Controller

## Recommended new file

Create:

```text
app/src/main/java/com/kaushal/worker/localization/AppLanguage.kt
```

Responsibilities:

```text
1. Define supported languages.
2. Convert stored language code → Locale.
3. Apply the locale to the Compose application context.
4. Keep language handling in one place.
```

Recommended conceptual enum:

```text
ENGLISH
HINDI
SANTALI
```

with mapping:

```text
ENGLISH → en
HINDI   → hi
SANTALI → sat-Olck
```

Do not spread locale logic across individual screens.

---

# 10. Apply the Selected Locale to the Whole App

The root Compose content should observe the current language state.

The `AppNavigation()` content should be inside the locale-aware context.

Conceptually:

```text
TemporaryAppViewModel
        │
        └── selected language
                │
                ▼
        App language controller
                │
                ▼
        Compose root / LocalConfiguration
                │
                ▼
        NavHost + every screen
```

When the language changes:

```text
English selected
      ↓
setLanguage("en")
      ↓
app recomposes
      ↓
all text uses English resources
```

Hindi:

```text
Hindi selected
      ↓
setLanguage("hi")
      ↓
locale becomes Hindi
      ↓
all screens show Hindi strings
```

Santali:

```text
Santali selected
      ↓
setLanguage("sat")
      ↓
locale becomes Santali / Ol Chiki
      ↓
all screens show Santali strings
```

No restart of the whole app should be required for normal UI switching.

---

# 11. Make Language Selection Persistent During Navigation

Current code:

```kotlin
fun setLanguage(language: String) {
    _session.value = _session.value.copy(
        profile = _session.value.profile.copy(language = language)
    )
}
```

Keep the selected language in the central session state.

Do not create a separate local language state inside `LanguageScreen`.

The language selector must always read:

```kotlin
selected = session.profile.language
```

from the central state.

---

# 12. Make Profile → Language Functional

## Current problem

`ProfileScreen.kt` currently has:

```kotlin
TextButton(onClick = {}) {
    Text("Language")
}
```

This button does nothing.

## Required behavior

Change the Profile screen so the Language option opens the existing language-selection UI.

Recommended navigation flow:

```text
Profile
   ↓
Language
   ↓
LanguageScreen
   ↓
Select English / Hindi / Santali
   ↓
Save immediately
   ↓
Return to previous screen
```

### Add a route

Use the existing:

```text
Routes.Language
```

Do not create duplicate language routes.

### Important

The same `LanguageScreen` can be reused for:

```text
Onboarding language selection
```

and:

```text
Change language from Profile
```

Add a suitable flag if needed, for example:

```text
fromSettings = true
```

so onboarding and profile behavior can differ without duplicating the screen.

---

# 13. After Selecting a Language from Profile

When the user changes language from Profile:

```text
1. Update session.profile.language.
2. Apply the new application locale.
3. Recompose the current app UI.
4. Keep the user authenticated.
5. Return to Profile / previous screen as appropriate.
```

Do **not** navigate back to Welcome, Login, Register, or Training Profile.

Changing language must never log the user out.

---

# 14. Bottom Navigation + Language Interaction

After changing the language, the bottom navigation labels must also change.

Examples:

```text
English:
Home | Learn | Progress | Profile

Hindi:
localized Hindi equivalents

Santali:
localized Santali equivalents
```

The navigation target values should remain stable internal keys:

```text
"Home"
"Learn"
"Progress"
"Profile"
```

Only the visible labels should be localized.

Do not use translated text as route identifiers.

---

# 15. Important Rule for Navigation

Keep this separation:

```text
INTERNAL NAVIGATION KEY
        ↓
"Profile"

VISIBLE UI LABEL
        ↓
localized stringResource(...)
```

Never change:

```kotlin
navController.navigate(...)
```

to use a translated label.

For example, do NOT do:

```text
navigate("प्रोफ़ाइल")
```

Use the fixed internal route/key.

---

# 16. Bottom Navigation Component

File:

```text
app/src/main/java/com/kaushal/worker/ui/components/Common.kt
```

`BottomNavigationBar()` should remain the shared component.

The labels currently defined as:

```kotlin
"Home"
"Learn"
"Progress"
"Profile"
```

must become localized visible strings.

Keep the internal key separate from the visible label.

Recommended conceptual structure:

```text
NavigationItem(
    key = "Home",
    icon = Icons.Default.Home,
    label = stringResource(R.string.nav_home)
)
```

Then:

```kotlin
selected = current == item.key
```

and:

```kotlin
onClick = { onNavigate(item.key) }
```

This keeps navigation stable across all three languages.

---

# 17. Module Model Localization

## File

```text
app/src/main/java/com/kaushal/worker/data/model/Models.kt
```

Do not store translated display text directly as English-only model values.

Recommended structure:

```text
TrainingModule(
    id = "fire",
    titleResId = R.string.module_fire_title,
    descriptionResId = R.string.module_fire_description,
    icon = "🔥",
    hasAr = true
)
```

Then in `ModuleCard` / module screens:

```kotlin
stringResource(module.titleResId)
stringResource(module.descriptionResId)
```

This is necessary so the Learning Modules page also translates.

---

# 18. Recommended Resource Naming

Use consistent names.

Example:

```text
app_name
nav_home
nav_learn
nav_progress
nav_profile

dashboard_good_morning
dashboard_learner
dashboard_learning_journey
dashboard_no_modules
dashboard_explore_training
dashboard_quick_actions
dashboard_learning_modules
dashboard_my_progress
dashboard_certificates
dashboard_field_book
dashboard_ar_training
dashboard_safety_passport

notifications_title
notifications_empty
notifications_ok

profile_title
profile_language
profile_notification_settings
profile_help_support
profile_logout

module_fire_title
module_fire_description
module_gas_title
module_gas_description
module_machinery_title
module_machinery_description
module_ppe_title
module_ppe_description
module_laws_title
module_laws_description
```

Continue the same naming pattern for the other screens.

---

# 19. Do Not Leave Hard-coded English Behind

After localization is implemented, search the Kotlin source for visible text patterns.

Examples to search:

```text
Text("...
text = "...
KaushalButton("...
OutlinedKaushalButton("...
TextButton(... Text("...
```

All user-visible English must be replaced by:

```kotlin
stringResource(R.string.some_key)
```

Exceptions can be:

```text
icons
emoji
IDs
debug/internal keys
```

Do not convert backend IDs or navigation route keys into localized strings.

---

# 20. Required Files — Final Change Map

## Existing files to modify

```text
app/src/main/java/com/kaushal/worker/navigation/AppNavigation.kt
app/src/main/java/com/kaushal/worker/navigation/Routes.kt        # only if a small route/settings adjustment is needed
app/src/main/java/com/kaushal/worker/data/model/Models.kt
app/src/main/java/com/kaushal/worker/TemporaryAppViewModel.kt
app/src/main/java/com/kaushal/worker/screens/home/DashboardScreen.kt
app/src/main/java/com/kaushal/worker/screens/modules/ModuleScreens.kt
app/src/main/java/com/kaushal/worker/screens/profile/ProfileScreen.kt
app/src/main/java/com/kaushal/worker/screens/onboarding/OnboardingScreens.kt
app/src/main/java/com/kaushal/worker/screens/trainingprofile/TrainingProfileScreen.kt
app/src/main/java/com/kaushal/worker/screens/auth/AuthScreens.kt
app/src/main/java/com/kaushal/worker/screens/progress/ProgressScreen.kt
app/src/main/java/com/kaushal/worker/screens/certificates/CertificatesScreen.kt
app/src/main/java/com/kaushal/worker/screens/fieldbook/FieldBookScreen.kt
app/src/main/java/com/kaushal/worker/screens/ar/ArTrainingScreen.kt
app/src/main/java/com/kaushal/worker/screens/assessment/AssessmentScreen.kt
app/src/main/java/com/kaushal/worker/ui/components/Common.kt
```

## New files

```text
app/src/main/java/com/kaushal/worker/localization/AppLanguage.kt

app/src/main/res/values/strings.xml
app/src/main/res/values-hi/strings.xml
app/src/main/res/values-b+sat+Olck/strings.xml
```

---

# 21. Implementation Order

Do the work in this order so the app stays compilable.

## Phase 1 — Remove the unwanted module

1. Remove `Emergency Response` from `staticModules`.
2. Build the app.
3. Confirm the Learning Modules screen still opens.

## Phase 2 — Restore bottom navigation

1. Update `LearningModulesScreen`.
2. Update `ProfileScreen`.
3. Update `AppNavigation`.
4. Confirm Learn/Profile can navigate to Home/Learn/Progress/Profile.

## Phase 3 — Dashboard actions

1. Add profile icon.
2. Connect profile icon to `onNavigate("Profile")`.
3. Add notification dialog state.
4. Show notification popup when notification icon is tapped.
5. Keep empty notification state real/non-mock.

## Phase 4 — Localization foundation

1. Add `AppLanguage`.
2. Add localized resources.
3. Connect locale to app root.
4. Verify English.
5. Verify Hindi.
6. Verify Santali.

## Phase 5 — Convert all screens

Move every visible English string to resources.

## Phase 6 — Profile language setting

1. Make Profile → Language functional.
2. Reuse `LanguageScreen`.
3. Save the selected language in the central session.
4. Apply locale immediately.
5. Return to the authenticated screen without logout.

## Phase 7 — Final regression test

Test:

```text
Welcome
→ Get Started
→ English
→ Continue
→ Register/Login
→ Dashboard
→ Learn
→ Profile
→ Progress
```

Then test language switching from Profile:

```text
Profile
→ Language
→ Hindi
→ Continue/Back
→ Profile becomes Hindi
→ Learn becomes Hindi
→ Dashboard becomes Hindi
→ Bottom navigation becomes Hindi
```

Repeat for:

```text
Santali / Ol Chiki
```

---

# 22. Acceptance Criteria

## Navigation

- [ ] Bottom navigation remains visible on Dashboard.
- [ ] Bottom navigation remains visible on Learn.
- [ ] Bottom navigation remains visible on Profile.
- [ ] Bottom navigation remains visible on Progress.
- [ ] Active tab is highlighted correctly.
- [ ] Learn/Profile no longer lose the bottom icon bar.

## Dashboard

- [ ] Profile icon is visible beside notification icon.
- [ ] Profile icon opens Profile.
- [ ] Notification icon opens a popup.
- [ ] Popup can be closed.
- [ ] Notification popup contains no fabricated notification records.

## Learning Modules

- [ ] Emergency Response is removed.
- [ ] Exactly five requested learning modules remain.
- [ ] Existing AR flags remain unchanged.

## Language

- [ ] English is supported.
- [ ] Hindi is supported.
- [ ] Santali is supported.
- [ ] Selecting Hindi changes the whole app UI to Hindi.
- [ ] Selecting Santali changes the whole app UI to Santali/Ol Chiki.
- [ ] Dashboard text changes.
- [ ] Bottom navigation labels change.
- [ ] Learn page changes.
- [ ] Profile page changes.
- [ ] Progress page changes.
- [ ] Certificates page changes.
- [ ] Field Book page changes.
- [ ] AR Training page changes.
- [ ] Assessment page changes.
- [ ] Authentication/onboarding text changes where applicable.
- [ ] Module titles/descriptions change.
- [ ] Changing language does not log the user out.
- [ ] Language selection remains in central session state.

## Profile Language Setting

- [ ] Profile → Language is clickable.
- [ ] It opens the language selector.
- [ ] Current language is shown as selected.
- [ ] Selecting another language updates the app.
- [ ] User returns to the authenticated app, not Welcome/Login.

---

# 23. Important Implementation Constraint

Do not rewrite the entire app architecture.

Keep the current:

```text
Jetpack Compose
Navigation Compose
TemporaryAppViewModel
Temporary Session
existing screen structure
existing BottomNavigationBar
existing routes
```

Only add the missing state, localization infrastructure, callbacks, and UI required by this task.

Do not introduce Firebase/auth/backend changes in this task unless required only to preserve the existing flow.

Do not add mock training data.

---

# 24. Final Expected User Experience

```text
DASHBOARD
 ├── 🔔 Notification → popup
 ├── 👤 Profile → Profile
 └── Bottom Navigation
      ├── Home
      ├── Learn
      ├── Progress
      └── Profile

LEARN
 ├── five learning modules
 └── Bottom Navigation stays visible

PROFILE
 ├── profile information
 ├── Language
 │    └── English / Hindi / Santali
 │         └── entire app changes language
 └── Bottom Navigation stays visible
```

The key requirement is:

> **Language selection must control the complete application UI, not only the language-selection page.**
