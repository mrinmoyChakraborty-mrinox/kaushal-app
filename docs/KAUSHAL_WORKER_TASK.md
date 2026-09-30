# KAUSHAL Worker App — UI & Navigation Implementation Tasks

## 1. Objective

Implement the complete KAUSHAL mobile application UI and navigation workflow shown in the provided reference screenshots.

This round is **UI/navigation testing only**.

The application must use a **temporary local authentication/session state**. Firebase Authentication, FastAPI authentication, Firestore persistence, Firebase UID/token generation, and `google-services.json` are intentionally out of scope for this round.

### Core product model

KAUSHAL supports two user types:

1. **Learner**
   - Learn safety modules
   - Read the Field Book
   - Practice simulations
   - Take assessments
   - Track progress
   - Potentially earn training completion certificates

2. **Worker**
   - Everything available to a learner
   - Assigned training
   - Worker-specific training
   - Compliance records
   - Safety Passport
   - Verified certificates
   - Employer/admin tracking

### Learner → Worker progression

```text
Learner
   ↓
Gets Worker ID after joining a mine/organization
   ↓
Adds Worker ID to profile
   ↓
Admin / organization verifies Worker ID
   ↓
Account becomes Worker / verified worker profile
```

For this UI-testing round, the verification/backend process is represented only by local navigation/state. Do not pretend that a real organization has verified the user.

---

# 2. Mandatory Authentication Constraint

## Temporary Authentication State

Use a local temporary state instead of Firebase.

Example:

```kotlin
isTemporarilyAuthenticated = true
```

or an equivalent local/session state.

### MUST NOT be implemented in this round

- Firebase Phone Authentication
- `google-services.json`
- Firebase UID creation
- Firebase ID tokens
- FastAPI authentication
- Firestore user/profile persistence
- Fake Firebase UID
- Fake Firebase token
- Claims that the user is authenticated with Firebase

### Allowed

- In-memory state
- `rememberSaveable`
- ViewModel state
- Local/session state
- Local navigation state
- User-entered temporary profile values

The temporary state exists only to test the complete UI/navigation flow.

---

# 3. Recommended Tech Stack

Use the existing Android project and preserve its current package/application structure where possible.

### Frontend

- Kotlin
- Jetpack Compose
- Material 3
- Android Navigation Compose
- Android ViewModel
- StateFlow / Compose state where appropriate

### Local UI state

- ViewModel
- `StateFlow`
- `SavedStateHandle` where useful
- No Firebase dependency for authentication

### Architecture

Use a simple maintainable structure:

```text
UI / Screens
      ↓
Navigation
      ↓
ViewModels / UI State
      ↓
Temporary Local Session/Profile State
```

Do not introduce unnecessary frameworks.

### Future backend integration

Keep interfaces/state models clean enough that Firebase/FastAPI/Firestore can be connected later without rewriting every screen.

---

# 4. Required Project Structure

Use the existing project's package root. The following structure is the target organization:

```text
app/
└── src/
    └── main/
        ├── java/com/kaushal/worker/
        │   ├── MainActivity.kt
        │   │
        │   ├── navigation/
        │   │   ├── AppNavigation.kt
        │   │   ├── Routes.kt
        │   │   └── NavigationState.kt
        │   │
        │   ├── auth/
        │   │   ├── AuthViewModel.kt
        │   │   ├── TemporaryAuthState.kt
        │   │   └── AuthUiState.kt
        │   │
        │   ├── profile/
        │   │   ├── TrainingProfileViewModel.kt
        │   │   ├── TrainingProfileState.kt
        │   │   └── ProfileModels.kt
        │   │
        │   ├── data/
        │   │   ├── model/
        │   │   │   ├── UserType.kt
        │   │   │   ├── TrainingProfile.kt
        │   │   │   ├── TrainingModule.kt
        │   │   │   └── Certificate.kt
        │   │   │
        │   │   └── local/
        │   │       └── TemporaryAppState.kt
        │   │
        │   ├── ui/
        │   │   ├── components/
        │   │   │   ├── KaushalButton.kt
        │   │   │   ├── KaushalTextField.kt
        │   │   │   ├── SelectionRow.kt
        │   │   │   ├── BottomNavigationBar.kt
        │   │   │   ├── TopBar.kt
        │   │   │   ├── EmptyState.kt
        │   │   │   ├── ModuleCard.kt
        │   │   │   └── ProfileSection.kt
        │   │   │
        │   │   └── theme/
        │   │       ├── Color.kt
        │   │       ├── Theme.kt
        │   │       └── Type.kt
        │   │
        │   ├── screens/
        │   │   ├── onboarding/
        │   │   │   ├── WelcomeScreen.kt
        │   │   │   ├── LanguageScreen.kt
        │   │   │   └── TrainingBenefitsScreen.kt
        │   │   │
        │   │   ├── authentication/
        │   │   │   ├── LoginScreen.kt
        │   │   │   ├── LoginOtpScreen.kt
        │   │   │   ├── RegisterScreen.kt
        │   │   │   └── RegisterOtpScreen.kt
        │   │   │
        │   │   ├── trainingprofile/
        │   │   │   └── TrainingProfileScreen.kt
        │   │   │
        │   │   ├── home/
        │   │   │   └── DashboardScreen.kt
        │   │   │
        │   │   ├── modules/
        │   │   │   ├── LearningModulesScreen.kt
        │   │   │   └── ModuleDetailScreen.kt
        │   │   │
        │   │   ├── fieldbook/
        │   │   │   └── FieldBookScreen.kt
        │   │   │
        │   │   ├── ar/
        │   │   │   └── ArTrainingScreen.kt
        │   │   │
        │   │   ├── assessment/
        │   │   │   └── AssessmentScreen.kt
        │   │   │
        │   │   ├── certificates/
        │   │   │   └── CertificatesScreen.kt
        │   │   │
        │   │   ├── progress/
        │   │   │   └── ProgressScreen.kt
        │   │   │
        │   │   └── profile/
        │   │       └── ProfileScreen.kt
        │   │
        │   └── assets/
        │       └── background/
        │           └── README.md
        │
        └── res/
            ├── drawable/
            ├── mipmap/
            └── values/
```

If the current project already has equivalent files, **modify/reuse them instead of creating duplicate navigation or theme systems**.

---

# 5. Asset Rule

The industrial/mining background image shown in the screenshots will be supplied later.

Create the required asset location now:

```text
app/src/main/java/com/kaushal/worker/assets/background/
```

or, preferably for Android drawable resources:

```text
app/src/main/res/drawable/
```

Keep a placeholder reference/documentation so the developer knows where to put the final background image.

### Important

Do not generate a fake replacement background.

Do not block the application if the image is absent.

Use a clean fallback background until the real image is supplied.

---

# 6. Global Design Requirements

Match the supplied screenshots as closely as practical.

### Visual language

- White/off-white background
- KAUSHAL dark navy branding
- Bright orange primary CTA
- Rounded cards
- Rounded input fields
- Soft borders/shadows
- Friendly worker illustration areas
- Industrial/mining visual language
- Large readable typography
- High contrast
- Mobile-first layout
- Bottom navigation for the main authenticated area

### Primary orange

Use a consistent orange throughout buttons, active states and accents.

### Primary navy

Use a consistent dark navy for headings and important text.

### Accessibility

- Touch targets should be comfortable for workers using mobile devices.
- Do not depend on tiny icons alone.
- Important actions must have readable text.
- Do not place essential information only in color.

---

# 7. TASK GROUP A — App Entry & Navigation Foundation

## A1 — Clean application entry

File:

```text
MainActivity.kt
```

Requirements:

- Launch the Compose application.
- Apply the KAUSHAL theme.
- Start `AppNavigation`.
- Do not initialize Firebase authentication for this round.

## A2 — Central route definitions

File:

```text
navigation/Routes.kt
```

Define routes for all screens.

Suggested routes:

```text
welcome
language
training_benefits

login
login_otp

register
register_otp

training_profile

dashboard
learning_modules
module_detail
field_book
ar_training
assessment
certificates
progress
profile
```

## A3 — Central navigation

File:

```text
navigation/AppNavigation.kt
```

Implement navigation based on temporary local state.

The navigation must support:

```text
New User
Welcome
  ↓
Language
  ↓
Training Benefits
  ↓
Register
  ↓
Register OTP
  ↓
Training Profile
  ↓
Dashboard
```

Existing user:

```text
Welcome
  ↓
Login
  ↓
Login OTP
  ↓
Dashboard
```

After a temporary authenticated state exists, the app must not unexpectedly return to login during ordinary navigation.

---

# 8. TASK GROUP B — Welcome / Onboarding

## B1 — Welcome screen

File:

```text
screens/onboarding/WelcomeScreen.kt
```

Match the first supplied screenshot.

Elements:

- Worker illustration
- Industrial background
- "Welcome to"
- KAUSHAL logo/title
- "Learn • Practice • Stay Safe"
- `GET STARTED`
- `I ALREADY HAVE AN ACCOUNT`

Actions:

```text
GET STARTED
→ Language

I ALREADY HAVE AN ACCOUNT
→ Login
```

Do not add unrelated buttons.

---

# 9. TASK GROUP C — Language Selection

## C1 — Language screen

File:

```text
screens/onboarding/LanguageScreen.kt
```

Match the supplied screenshot.

Show:

- Worker illustration
- "What language do you speak?"
- English
- Hindi
- Bengali
- Continue button

The selected language must be stored in temporary local state.

### Important

If Hindi is selected, the UI must not simultaneously display English as the active language.

The selected language must affect the relevant UI text/state consistently.

For this round, support at least:

```text
English
Hindi
Bengali
```

If complete translation content is not yet available, keep untranslated text clearly controlled through a localization layer rather than showing two active language versions at once.

---

# 10. TASK GROUP D — Training Benefits

## D1 — Training benefits screen

File:

```text
screens/onboarding/TrainingBenefitsScreen.kt
```

Match the supplied reference.

Display the five capability areas:

1. Spot hazards in your workplace
2. Respond correctly to emergencies
3. Choose the right PPE and equipment
4. Practice safely with AR simulations
5. Pass your safety assessment & get certified

Continue:

```text
CONTINUE → Register
```

---

# 11. TASK GROUP E — Existing User Login

## E1 — Login screen

File:

```text
screens/authentication/LoginScreen.kt
```

Match the screenshot.

Elements:

- Login/Register tabs
- Welcome back
- Mobile number
- Send OTP
- Use registered number
- Register link

For this round, OTP is **UI simulation only**.

Do not send a real SMS.

## E2 — Login OTP

File:

```text
screens/authentication/LoginOtpScreen.kt
```

Elements:

- Six OTP boxes
- Mobile number summary
- Resend OTP
- Verify & Login

For testing:

- Accept a simple local test OTP behavior.
- Do not create Firebase credentials.
- On successful local verification:

```text
temporaryAuthenticated = true
existingUser = true
→ Dashboard
```

No fake Firebase UID/token may be created.

---

# 12. TASK GROUP F — New User Registration

## F1 — Register screen

File:

```text
screens/authentication/RegisterScreen.kt
```

Elements:

- Mobile number
- Full name
- Create Account
- Already have an account? Login

The entered name and mobile number are temporary local values only.

## F2 — Register OTP

File:

```text
screens/authentication/RegisterOtpScreen.kt
```

Elements:

- Six OTP boxes
- Resend OTP
- Verify & Continue

After local test verification:

```text
temporaryAuthenticated = true
isNewUser = true
→ Training Profile
```

The user must not go directly to Dashboard after registration.

---

# 13. TASK GROUP G — Training Profile

## G1 — Training Profile screen

File:

```text
screens/trainingprofile/TrainingProfileScreen.kt
```

This screen appears **only for a newly registered user**.

Existing users do not see this page during login.

### Step 1 — Language

Allow selection of:

- English
- Hindi
- Bengali

### Step 2 — User type

Required options:

```text
Learner
Worker
```

The selected value determines the profile behavior.

### Step 3 — Industrial sector

Provide the industrial-sector selection shown/expected in the KAUSHAL concept.

At minimum:

```text
Mining
Steel
Mica
```

Keep the selection component reusable so more sectors can be added later.

### Step 4 — Sub-sector

Selectable based on the chosen sector.

For this UI round, it is acceptable to provide the available UI options without backend persistence.

### Step 5 — Department / Operation

Selection field.

### Step 6 — Role

Selection field.

### Step 7 — Worker ID

Field:

```text
Worker ID (Optional)
```

Important behavior:

- Learner may leave it empty.
- Worker may enter a Worker ID.
- Entering a Worker ID in this round does **not** mean that it has been verified.
- Do not display "Verified Worker" unless a real backend verification exists.
- Do not fabricate organization verification.

### Continue

Save the entered profile to temporary local state and navigate to:

```text
Dashboard
```

---

# 14. TASK GROUP H — Dashboard

## H1 — Dashboard screen

File:

```text
screens/home/DashboardScreen.kt
```

Match the supplied dashboard screenshot.

Structure:

- Greeting
- User name if entered by the user
- User type
- Sector/role information if entered
- Overall progress area
- Training modules
- Quick actions
- Bottom navigation

### Strict no-mock-data rule

Do NOT fabricate:

```text
Ravi Kumar
Minmoy Chakraborty
60%
3/5 modules completed
4/6 lessons
2/8 lessons
etc.
```

These names and progress values in the screenshots are visual references only.

Use actual temporary user-entered data.

If no real training progress exists:

```text
0%
0 completed
```

or an appropriate empty state.

If no modules are assigned/available from real data, show an empty state instead of inventing assignments.

### Static module catalog

Static training module definitions are allowed because they are product content, not fake user progress.

Possible module names:

- Fire & Explosion Response
- Gas Leak & Confined Space
- Machinery Safety
- PPE & Workplace Safety
- Mine Laws & Workers' Rights
- Emergency Response

Do not assign fake completion numbers.

---

# 15. TASK GROUP I — Notification Permission UI

## I1 — First-dashboard notification prompt

Display the "Stay Updated" prompt only as a local UI permission/onboarding state.

Options:

```text
Turn On Notifications
Not Now
```

For this round:

- Do not require FCM.
- Do not claim notifications are actually registered with Firebase.
- Store only a local UI preference if needed.

---

# 16. TASK GROUP J — Learning Modules

## J1 — Empty modules state

File:

```text
screens/modules/LearningModulesScreen.kt
```

Match the supplied empty-state screenshot when no modules are available.

Display:

```text
Learning Modules

Your safety training modules
will appear here when available.

Training content is being prepared.
Please check back later.
```

Use an appropriate empty-state illustration/icon.

## J2 — Available modules state

The same screen must support the available-module state.

Each module row can contain:

- Icon
- Module name
- Short description
- Optional AR indicator
- Navigation arrow

Do not show fake completion values.

Actual progress should come from real/local user state.

---

# 17. TASK GROUP K — Module Detail

## K1 — Module detail screen

File:

```text
screens/modules/ModuleDetailScreen.kt
```

Match the supplied reference.

Actions:

```text
Start Learning
AR Training
Take Assessment
View Certificate
```

Rules:

- Locked actions must visually indicate locked state.
- Certificate remains locked until actual assessment completion.
- AR can show "Coming Soon" if AR implementation is not part of this round.
- No fake completion.

---

# 18. TASK GROUP L — AR Training

## L1 — AR Training placeholder

File:

```text
screens/ar/ArTrainingScreen.kt
```

Match the supplied placeholder screen.

Display:

```text
AR Training

AR training for this module
will be available soon.

We are preparing real workplace
simulations for hands-on learning
with your phone.
```

Button/state:

```text
Coming Soon
```

Do not implement fake AR behavior.

The screen must still be reachable through navigation.

---

# 19. TASK GROUP M — Field Book

## M1 — Field Book screen

File:

```text
screens/fieldbook/FieldBookScreen.kt
```

The Field Book is a major learner/worker feature.

The concept is:

```text
Layer 1 — Book
Illustration + story + safety knowledge

Layer 2 — Interactive
Tap / drag / scan / identify / choose / arrange / simulate
```

For this UI round:

- Build the navigation entry point.
- Show a clean Field Book interface.
- Use empty/content-ready states where actual training assets are unavailable.
- Do not invent fake completion statistics.
- Keep the architecture ready for future interactive safety exercises.

---

# 20. TASK GROUP N — Assessment

## N1 — Assessment screen

File:

```text
screens/assessment/AssessmentScreen.kt
```

Required:

- Assessment title
- Question area
- Answer options
- Next/Submit controls
- Result state

For the initial UI-testing round, the assessment may use an explicit **content placeholder/empty state** if real question data is not supplied.

Do not create fake certification records.

If a local test assessment is later added, clearly treat it as test/local content rather than backend verification.

---

# 21. TASK GROUP O — Certificates

## O1 — Certificates screen

File:

```text
screens/certificates/CertificatesScreen.kt
```

Match the supplied empty-state screenshot.

When no certificates exist:

```text
My Certificates

No Certificates Yet

Complete module assessments
to earn your certificates.
```

Do not fabricate certificates.

Later, real certificates will come from backend data.

---

# 22. TASK GROUP P — Progress

## P1 — Progress screen

File:

```text
screens/progress/ProgressScreen.kt
```

Show progress based only on actual local state.

No fake:

```text
60%
3/5
4/6
```

If there is no completed content:

```text
0%
No modules completed yet
```

The page must remain functional even with zero data.

---

# 23. TASK GROUP Q — Profile

## Q1 — Profile screen

File:

```text
screens/profile/ProfileScreen.kt
```

Match the supplied profile screenshot.

Show user-entered information when available:

- Name
- Mobile number
- User type
- Worker ID
- Industrial sector
- Department/operation
- Role
- Language

Menu items:

- Language
- Notification Settings
- Help & Support
- Logout

### Worker status

For a learner who has entered a Worker ID:

```text
Worker ID: <entered ID>
Status: Verification pending
```

Do not claim verification.

For a truly verified worker in a future backend version:

```text
Worker
Verified Worker
```

would come from backend verification.

---

# 24. TASK GROUP R — Learner/Worker Functional Differences

## Learner

Learner can access:

```text
Dashboard
Learning Modules
Field Book
AR Training
Assessments
Progress
Certificates
Profile
```

## Worker

Worker can access all learner features plus worker-oriented sections such as:

```text
Assigned Training
Worker-specific Training
Compliance
Safety Passport
Verified Certificates
Employer/Admin tracking
```

For this UI round, these worker-only sections may be represented by UI navigation/placeholder states where backend functionality does not yet exist.

Do not fabricate employer data or compliance records.

---

# 25. TASK GROUP S — Worker ID Transition

Create the state model now so future backend verification can be added without changing the UI architecture.

Suggested state:

```kotlin
enum class UserType {
    LEARNER,
    WORKER
}

enum class WorkerVerificationStatus {
    NOT_APPLICABLE,
    NOT_SUBMITTED,
    PENDING,
    VERIFIED
}
```

Current UI behavior:

```text
Learner
  ↓
Worker ID entered
  ↓
PENDING
```

Future behavior:

```text
PENDING
  ↓
Backend/Admin verifies
  ↓
VERIFIED
  ↓
Worker profile
```

Do not simulate the final verified state as if it were real.

---

# 26. TASK GROUP T — Bottom Navigation

Authenticated screens should use the bottom navigation shown in the screenshots.

Recommended:

```text
Home
Learn
Progress
Profile
```

Navigation behavior must work from every authenticated main screen.

Do not create multiple independent bottom-navigation implementations.

Create one reusable:

```text
ui/components/BottomNavigationBar.kt
```

---

# 27. TASK GROUP U — Back Navigation

Implement predictable back navigation.

Examples:

```text
Module Detail → Learning Modules
AR Training → Module Detail
Assessment → Module Detail
Certificates → Profile/Progress as appropriate
Training Profile → previous onboarding/auth page where applicable
```

Back should never unexpectedly exit the app during normal workflow.

---

# 28. TASK GROUP V — Logout

Logout must:

```text
temporaryAuthenticated = false
```

Clear temporary authentication/session state.

Then navigate to:

```text
Welcome
```

No Firebase logout call is required.

No Firebase state should be created.

---

# 29. TASK GROUP W — Validation

Validate all forms.

### Login

- Mobile number required
- Reasonable mobile number format

### Register

- Mobile number required
- Full name required

### OTP

- Six digits
- Show error for invalid/incomplete local test input

### Training profile

- Language required
- User type required
- Sector required
- Sub-sector required where applicable
- Department required
- Role required
- Worker ID optional

The Continue button must not silently navigate when required information is missing.

---

# 30. TASK GROUP X — No Mock Data Policy

This is a critical acceptance requirement.

### Prohibited

Do not hardcode fake:

- User names
- Phone numbers as actual user profiles
- Progress percentages
- Completed lessons
- Certificates
- Worker verification
- Employer names
- Compliance records
- Assigned training
- Assessment scores

### Allowed

- Static product labels
- Static training module names
- Static illustrations
- Empty-state text
- User-entered temporary values
- Local test OTP mechanism
- Local navigation state
- Local boolean flags
- Product UI configuration

---

# 31. TASK GROUP Y — Testing Matrix

Test the complete flow.

## New learner

```text
Welcome
→ Get Started
→ Language
→ Training Benefits
→ Register
→ Register OTP
→ Training Profile
→ Select Learner
→ Select sector/sub-sector/department/role
→ Continue
→ Dashboard
```

## New worker

```text
Welcome
→ Get Started
→ Language
→ Training Benefits
→ Register
→ Register OTP
→ Training Profile
→ Select Worker
→ Fill worker profile
→ Continue
→ Dashboard
```

## Existing user

```text
Welcome
→ Already Have an Account
→ Login
→ Login OTP
→ Dashboard
```

Existing user must NOT see:

```text
Training Profile
```

during the login flow.

## Learner feature flow

```text
Dashboard
→ Learning Modules
→ Module Detail
→ Start Learning
→ AR Training
→ Assessment
→ Certificates
→ Progress
→ Profile
```

Every navigation action must work.

---

# 32. TASK GROUP Z — Build & Quality

Before completion:

- Remove compile errors.
- Remove duplicate routes.
- Remove duplicate navigation hosts.
- Remove unused Firebase authentication calls.
- Remove fake UID/token logic.
- Ensure all imports resolve.
- Ensure Android back navigation works.
- Ensure screens do not render black/blank due to missing assets.
- Ensure scrolling works on small screens.
- Ensure keyboard does not permanently cover form fields.
- Ensure buttons are clickable.
- Ensure selected radio/selection state is visible.
- Ensure language selection is actually stored.
- Ensure learner/worker selection is actually stored.
- Ensure profile fields flow into the dashboard/profile.
- Ensure logout returns to Welcome.

---

# 33. Final Acceptance Criteria

The implementation is complete only when:

### Authentication

- [ ] New-user registration flow works locally.
- [ ] Existing-user login flow works locally.
- [ ] OTP is only a local UI test mechanism.
- [ ] No fake Firebase UID exists.
- [ ] No fake Firebase token exists.
- [ ] No Firebase authentication is required.

### Profile

- [ ] Language selection works.
- [ ] Learner/Worker selection works.
- [ ] Sector selection works.
- [ ] Sub-sector selection works.
- [ ] Department selection works.
- [ ] Role selection works.
- [ ] Worker ID is optional.
- [ ] Worker verification is not falsely claimed.

### Navigation

- [ ] Every screen from the screenshots is reachable.
- [ ] Back navigation works.
- [ ] Bottom navigation works.
- [ ] Logout works.
- [ ] Existing users bypass Training Profile.
- [ ] New users enter Training Profile after registration.

### Data integrity

- [ ] No fake dashboard user.
- [ ] No fake progress.
- [ ] No fake certificates.
- [ ] No fake employer/compliance data.
- [ ] Empty states are used where real data does not exist.

### Visual

- [ ] Welcome matches reference.
- [ ] Language matches reference.
- [ ] Benefits matches reference.
- [ ] Login matches reference.
- [ ] OTP matches reference.
- [ ] Register matches reference.
- [ ] Training Profile matches reference.
- [ ] Dashboard structure matches reference.
- [ ] Learning Modules matches reference.
- [ ] Module Detail matches reference.
- [ ] AR placeholder matches reference.
- [ ] Certificates empty state matches reference.
- [ ] Profile matches reference.
- [ ] Orange/navy visual identity is consistent.

---

# 34. Implementation Order

Implement in this exact order to reduce navigation errors:

```text
1. Theme / shared components
2. Routes
3. Temporary auth state
4. AppNavigation
5. Welcome
6. Language
7. Training Benefits
8. Login
9. Login OTP
10. Register
11. Register OTP
12. Training Profile
13. Dashboard
14. Bottom Navigation
15. Learning Modules
16. Module Detail
17. AR Training
18. Field Book
19. Assessment
20. Progress
21. Certificates
22. Profile
23. Logout
24. Validation
25. Full navigation testing
26. Visual cleanup
27. Final build verification
```

Do not start Firebase integration in this task.

---

# 35. Future Integration Boundary

After the UI/navigation round is approved, replace the temporary layer with:

```text
TemporaryAuthState
        ↓
Firebase Phone Authentication
        ↓
Firebase UID
        ↓
FastAPI
        ↓
Firestore
        ↓
Persistent Training Profile
        ↓
Real assignments/progress/certificates
        ↓
Admin verification
        ↓
Worker verification
```

The current implementation must be structured so this replacement can happen without redesigning the entire UI.
