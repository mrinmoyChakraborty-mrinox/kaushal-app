# KAUSHAL Worker App — Product Requirements Document

## 1. Product Overview

**KAUSHAL — Learn • Practice • Stay Safe** is a mobile safety-training platform designed for workers and future workers in industrial environments such as mining, steel and mica.

The product combines:

- Safety education
- Interactive learning
- Field Book knowledge
- AR-based simulations
- Assessments
- Training progress
- Certificates
- Worker identity/profile information
- Future worker verification
- Future employer/admin tracking

The current implementation phase is specifically a **UI and navigation validation phase**.

---

# 2. Product Vision

KAUSHAL should allow a person to begin as a **Learner**, develop safety knowledge through structured training, and later transition into a **Worker** profile after obtaining and submitting a real Worker ID that can be verified by an organization/admin.

The product therefore supports two connected user journeys:

```text
LEARNER JOURNEY

Discover KAUSHAL
      ↓
Create account
      ↓
Set up training profile
      ↓
Learn safety
      ↓
Practice
      ↓
Assess
      ↓
Track progress
      ↓
Earn certificates
      ↓
Join an industrial workplace
      ↓
Receive Worker ID
      ↓
Submit Worker ID
      ↓
Organization/Admin verification
      ↓
Verified Worker
```

and:

```text
WORKER JOURNEY

Create/Login
      ↓
Worker profile
      ↓
Assigned training
      ↓
Worker-specific training
      ↓
Compliance
      ↓
Safety Passport
      ↓
Verified certificates
      ↓
Employer/Admin tracking
```

---

# 3. Current Development Scope

## Included now

- Mobile UI
- Complete onboarding
- Login UI
- Registration UI
- OTP UI simulation
- Training profile
- Learner/Worker selection
- Dashboard
- Learning modules
- Module detail
- Field Book entry
- AR training placeholder
- Assessment entry
- Progress
- Certificates empty state
- Profile
- Logout
- Navigation
- Temporary local session

## Not included now

- Firebase Authentication
- Firebase UID
- Firebase ID tokens
- `google-services.json`
- FastAPI authentication
- Firestore persistence
- Real SMS OTP
- FCM notification registration
- Real organization verification
- Real Worker ID verification
- Real employer/admin tracking
- Real AR engine
- Production certificate issuance

---

# 4. Critical Temporary Authentication Requirement

For this implementation round:

```text
Firebase = NOT USED FOR AUTHENTICATION
```

Use local temporary state.

Example:

```kotlin
data class TemporaryAuthState(
    val isTemporarilyAuthenticated: Boolean = false,
    val isNewUser: Boolean = false,
    val userType: UserType? = null
)
```

The exact implementation may differ.

The important rule is:

```text
Local temporary state → navigation testing
```

not:

```text
Fake Firebase authentication → navigation testing
```

Never create:

```text
fake Firebase UID
fake Firebase token
fake Firebase user
```

---

# 5. User Types

## 5.1 Learner

A learner is someone who is learning safety skills before or independently of employment.

Capabilities:

- Learn safety modules
- Read Field Book
- Practice simulations
- Take assessments
- Track progress
- Potentially earn training completion certificates

A learner does not need a Worker ID.

---

## 5.2 Worker

A worker is a person who is working in an industrial organization and can eventually be associated with a verified Worker ID.

Capabilities:

- Everything available to learner
- Assigned training
- Worker-specific training
- Compliance records
- Safety Passport
- Verified certificates
- Employer/admin tracking

Worker-specific information should only become verified when backed by a real organization/admin process.

---

# 6. Learner → Worker Lifecycle

The intended product lifecycle is:

```text
Learner
   ↓
Gets Worker ID
   ↓
Adds Worker ID to profile
   ↓
Admin / organization verifies it
   ↓
Account becomes Worker / verified worker profile
```

## Current phase

Only the first parts are represented locally.

If the user enters a Worker ID:

```text
Worker ID entered
Status = Verification pending
```

The application must not say:

```text
Verified Worker
```

until a future backend verification system exists.

---

# 7. First Launch Experience

The first screen is the KAUSHAL Welcome screen.

It should visually follow the supplied reference.

Content:

```text
Welcome to
KAUSHAL

Learn • Practice • Stay Safe
```

Primary action:

```text
GET STARTED
```

Secondary action:

```text
I ALREADY HAVE AN ACCOUNT
```

---

# 8. Language Selection

The user chooses their preferred language.

Required initial choices:

```text
English
Hindi
Bengali
```

The selected language must become part of temporary profile state.

The UI must not show two languages as simultaneously selected.

---

# 9. Training Benefits

Before account creation, explain what KAUSHAL enables.

Required capabilities:

### Hazard identification

```text
Spot hazards in your workplace
Identify potential risks before they become dangerous.
```

### Emergency response

```text
Respond correctly to emergencies
Know what to do during fire, gas leaks,
equipment failures, and more.
```

### PPE

```text
Choose the right PPE and equipment
Select and use the correct protective equipment
for your work environment.
```

### AR practice

```text
Practice safely with AR simulations
Experience real workplace situations using your phone.
```

### Assessment/certification

```text
Pass your safety assessment & get certified
Complete assessments, earn a verified digital
certificate, and build your Safety Passport.
```

---

# 10. Existing User Flow

```text
Welcome
   ↓
I Already Have an Account
   ↓
Login
   ↓
Enter Mobile Number
   ↓
Send OTP
   ↓
OTP Screen
   ↓
Local OTP Verification
   ↓
Temporary Authentication
   ↓
Dashboard
```

Existing users do not see:

```text
Set Up Your Training Profile
```

again during this login flow.

---

# 11. New User Flow

```text
Welcome
   ↓
Get Started
   ↓
Language
   ↓
Training Benefits
   ↓
Register
   ↓
Mobile Number + Full Name
   ↓
Register OTP
   ↓
Local OTP Verification
   ↓
Temporary Account State
   ↓
Set Up Your Training Profile
   ↓
Dashboard
```

The Training Profile screen is mandatory for a newly registered user before entering the dashboard.

---

# 12. Registration Requirements

The registration screen must contain:

```text
Create Your KAUSHAL Account

Enter your details to get started.

Mobile Number
Full Name

CREATE ACCOUNT

Already have an account?
Login
```

The values are stored only in temporary local state during this development phase.

---

# 13. Training Profile

The profile setup is a multi-step form.

The reference design uses four conceptual steps.

## Step 1 — Language

User selects:

```text
English
Hindi
Bengali
```

## Step 2 — Learner / Worker

User selects:

```text
Learner
Worker
```

This is a central product decision.

---

## Step 3 — Industrial Sector

At minimum:

```text
Mining
Steel
Mica
```

The architecture should allow additional sectors later.

---

## Step 4 — Sub-Sector

Depends on industrial sector.

The UI should support dependent selection.

---

## Step 5 — Department / Operation

User selects their department or operation.

---

## Step 6 — Role

User selects their role.

---

## Step 7 — Worker ID

Optional:

```text
Worker ID (Optional)
```

For learners:

```text
May remain empty.
```

For workers:

```text
Can be entered if available.
```

But entering an ID does not mean verification.

---

# 14. Dashboard

The dashboard is the main authenticated home screen.

Reference structure:

```text
Greeting
User information

Overall Progress

Your Training Modules

Quick Actions

Bottom Navigation
```

Potential quick actions:

```text
Learning Modules
My Progress
Certificates
Field Book
AR Training
Safety Passport
```

The exact worker-specific actions may be conditionally displayed.

---

# 15. No Mock Data Requirement

This is a hard product requirement.

The screenshots contain example people and progress values.

Those values are reference-only.

Never ship:

```text
Ravi Kumar
Minmoy Chakraborty
60%
3 / 5 modules
4 / 6 lessons
2 / 8 lessons
```

as fake current user data.

Instead:

### User identity

Display values entered by the actual user during this temporary session.

### Progress

If no actual progress exists:

```text
0%
```

### Certificates

If none exist:

```text
No Certificates Yet
```

### Assigned training

If none exists:

```text
No training assigned yet.
```

### Worker verification

If no real verification exists:

```text
Verification pending
```

or another clearly non-verified state.

---

# 16. Static Training Content vs Mock User Data

Static training module names are acceptable because they are application content.

For example:

```text
Fire & Explosion Response
Gas Leak & Confined Space
Machinery Safety
PPE & Workplace Safety
Mine Laws & Workers' Rights
Emergency Response
```

These are not fake user records.

However, the app must not invent:

```text
User completed 4 of 6 lessons
User scored 85%
User has certificate XYZ
Employer assigned module X
```

unless that information actually exists in the current local/backend state.

---

# 17. Learning Modules

The Learning Modules page must support two states.

## Empty state

```text
Learning Modules

Your safety training modules
will appear here when available.

Training content is being prepared.
Please check back later.
```

## Available state

Show actual available module definitions.

Each module:

- Name
- Icon
- Description
- AR availability indicator when applicable
- Navigation arrow

Progress is only shown if real progress exists.

---

# 18. Module Detail

Module detail provides:

```text
Module title
Description
Start Learning
AR Training
Take Assessment
View Certificate
```

### State rules

`Start Learning`:

- available when module content exists

`AR Training`:

- available only if AR content exists
- otherwise show a coming-soon state

`Take Assessment`:

- available when assessment exists

`View Certificate`:

- locked until an actual qualifying completion exists

---

# 19. Field Book

The Field Book is designed as an interactive safety manual.

Every topic can eventually have two layers:

```text
LAYER 1 — BOOK

Illustration
Story
Safety knowledge
```

and:

```text
LAYER 2 — INTERACTIVE

Tap
Drag
Scan
Identify
Choose
Arrange
Simulate
```

The current UI should establish this product concept without requiring the complete interactive engine.

---

# 20. AR Training

AR Training is a future interactive simulation capability.

The current UI should show:

```text
AR Training

AR training for this module
will be available soon.

We are preparing real workplace
simulations for hands-on learning
with your phone.
```

Do not create fake AR results.

---

# 21. Assessments

Assessments will eventually evaluate safety knowledge.

Future flow:

```text
Start Assessment
   ↓
Questions
   ↓
Answers
   ↓
Submit
   ↓
Score
   ↓
Completion state
   ↓
Certificate eligibility
```

Current phase:

- UI/navigation only
- no backend score persistence
- no fake certificate

---

# 22. Progress

Progress should eventually aggregate:

```text
Modules completed
Lessons completed
Assessment performance
Simulation activity
Certificates earned
```

Current phase:

Only calculate/display values from actual temporary/local state.

With no completed content:

```text
0%
No modules completed yet
```

---

# 23. Certificates

The Certificates page should initially show:

```text
My Certificates

No Certificates Yet

Complete module assessments
to earn your certificates.
```

Certificates must never be fabricated for demonstration.

Future certificates will come from verified backend records.

---

# 24. Safety Passport

Safety Passport is a future worker-facing record.

It can eventually contain:

- Completed training
- Certificates
- Safety qualifications
- Compliance status
- Worker verification
- Employer/organization associations

In the current UI phase, it may be represented as a placeholder/empty state.

---

# 25. Worker Features

Once a user is a verified worker, the product can expose:

```text
Assigned Training
Worker-specific Training
Compliance Records
Safety Passport
Verified Certificates
Employer/Admin Tracking
```

The UI architecture must make it possible to add these without rebuilding the navigation architecture.

---

# 26. Profile

Profile must show the user's actual temporary profile data.

Fields:

```text
Name
Mobile Number
User Type
Worker ID
Industrial Sector
Sub-Sector
Department / Operation
Role
Language
```

Actions:

```text
Language
Notification Settings
Help & Support
Logout
```

---

# 27. Worker ID Status

Use a state model such as:

```text
NOT_APPLICABLE
NOT_SUBMITTED
PENDING
VERIFIED
```

Current phase should normally produce:

```text
Learner with no Worker ID:
NOT_APPLICABLE or NOT_SUBMITTED

Worker with submitted Worker ID:
PENDING
```

`VERIFIED` should be reserved for a future real backend/admin verification.

---

# 28. Notifications

The dashboard can request notification permission through a product UI prompt:

```text
Stay Updated

Turn on notifications to get important
updates about your assigned training,
new modules, assessments, and certificates.

TURN ON NOTIFICATIONS
Not Now
```

Current phase:

- local UI only
- no FCM requirement
- no fake notification registration

---

# 29. Navigation Model

Main authenticated navigation:

```text
Home
Learn
Progress
Profile
```

Secondary destinations:

```text
Field Book
AR Training
Module Detail
Assessment
Certificates
Safety Passport
Worker features
```

All screens should be reachable through valid navigation paths.

---

# 30. Screen Inventory

| # | Screen | New User | Existing User | Learner | Worker |
|---|---|---:|---:|---:|---:|
| 1 | Welcome | Yes | Yes | Yes | Yes |
| 2 | Language | Yes | No | Yes | Yes |
| 3 | Training Benefits | Yes | No | Yes | Yes |
| 4 | Login | No | Yes | Yes | Yes |
| 5 | Login OTP | No | Yes | Yes | Yes |
| 6 | Register | Yes | No | Yes | Yes |
| 7 | Register OTP | Yes | No | Yes | Yes |
| 8 | Training Profile | Yes | No | Yes | Yes |
| 9 | Dashboard | Yes | Yes | Yes | Yes |
| 10 | Learning Modules | Yes | Yes | Yes | Yes |
| 11 | Module Detail | Conditional | Conditional | Yes | Yes |
| 12 | Field Book | Yes | Yes | Yes | Yes |
| 13 | AR Training | Conditional | Conditional | Yes | Yes |
| 14 | Assessment | Conditional | Conditional | Yes | Yes |
| 15 | Progress | Yes | Yes | Yes | Yes |
| 16 | Certificates | Yes | Yes | Yes | Yes |
| 17 | Profile | Yes | Yes | Yes | Yes |
| 18 | Safety Passport | Future/Conditional | Future/Conditional | Future | Worker |
| 19 | Worker Training | Future | Future | No | Worker |
| 20 | Compliance | Future | Future | No | Worker |

---

# 31. Data Model for Current Phase

Use local state such as:

```kotlin
data class TemporaryUserProfile(
    val name: String = "",
    val mobileNumber: String = "",
    val language: String? = null,
    val userType: UserType? = null,
    val industrialSector: String? = null,
    val subSector: String? = null,
    val department: String? = null,
    val role: String? = null,
    val workerId: String? = null,
    val workerVerificationStatus: WorkerVerificationStatus =
        WorkerVerificationStatus.NOT_SUBMITTED
)
```

This is temporary application state.

It is not a Firebase user record.

---

# 32. Future Backend Data Model

Future architecture can map the temporary model to:

```text
Firebase Authentication
        ↓
Firebase UID
        ↓
User/Profile document
        ↓
Training Profile
        ↓
Training Assignments
        ↓
Progress
        ↓
Assessment Results
        ↓
Certificates
        ↓
Worker Verification
        ↓
Safety Passport
```

Do not implement this backend now.

---

# 33. Visual Requirements

The UI should closely follow the supplied reference screenshots.

### Brand

```text
KAUSHAL
Learn • Practice • Stay Safe
```

### Colors

Primary visual colors:

```text
KAUSHAL Navy
KAUSHAL Orange
White / Off-white
Light neutral borders
```

### Components

Use:

- Rounded buttons
- Rounded cards
- Rounded text fields
- Clear selected states
- Consistent spacing
- Worker illustrations
- Industrial/mining imagery
- Bottom navigation
- Large touch targets

---

# 34. Background Image

The supplied industrial background will be added later.

Do not hardcode a dependency that crashes when the image is absent.

Use:

```text
drawable / background asset
```

with a safe fallback.

The final supplied background should be usable on:

- Welcome
- Authentication screens
- OTP screens
- Profile setup where applicable
- Other screens where the reference uses the industrial background

---

# 35. Responsive Requirements

The application must work on Android phones including smaller screens.

Requirements:

- Vertical scrolling for long screens
- No content clipped at bottom
- Buttons remain visible/accessible
- Keyboard-aware form layouts
- No horizontal overflow
- No black screen caused by oversized/missing assets
- Correct system-bar handling
- Consistent content padding

---

# 36. Error Handling

Show user-friendly local validation errors.

Examples:

```text
Please enter your mobile number.
Please enter your full name.
Please select your language.
Please select whether you are a learner or worker.
Please select your industrial sector.
Please complete the required fields.
Please enter all 6 OTP digits.
```

No backend error messages should be fabricated.

---

# 37. Logout Requirements

When Logout is selected:

```text
Clear temporary authentication state
Clear temporary session navigation state
Return to Welcome
```

No Firebase logout is required.

---

# 38. Design Principle — Real Data Only

The application should distinguish clearly between:

### Real/current local user data

- Name entered by user
- Mobile number entered by user
- Selected language
- Selected user type
- Selected sector
- Selected sub-sector
- Selected department
- Selected role
- Worker ID entered by user

### Product content

- Module names
- Safety descriptions
- UI labels
- Illustrations

### Backend-dependent data

- Assigned training
- Completion percentages
- Assessment scores
- Certificates
- Employer
- Compliance
- Worker verification

Backend-dependent data must not be fabricated.

---

# 39. Definition of Done

The UI phase is accepted when:

1. App launches without Firebase authentication.
2. Welcome screen matches the reference.
3. New-user flow reaches Training Profile.
4. Existing-user flow reaches Dashboard without Training Profile.
5. Learner and Worker can be selected.
6. Training profile values are stored in temporary state.
7. Dashboard uses entered user data rather than fake names.
8. Dashboard does not show fake progress.
9. Learning Modules has correct empty/available states.
10. Module Detail is navigable.
11. AR Training placeholder is navigable.
12. Field Book is navigable.
13. Assessment is navigable.
14. Certificates correctly show empty state without fake certificates.
15. Progress does not fabricate completion.
16. Profile displays actual temporary values.
17. Worker ID can be entered but is not falsely verified.
18. Bottom navigation works.
19. Logout works.
20. No fake Firebase UID/token exists.
21. No `google-services.json` is required for this phase.
22. No Firebase Phone Authentication is required.
23. No Firestore persistence is required.
24. No FastAPI authentication is required.
25. Build completes successfully.
26. No screen becomes black/blank because of missing assets.
27. The final UI remains consistent with the supplied screenshots.

---

# 40. Future Release Boundary

After this UI milestone is approved, the next phase can add:

```text
Firebase Phone Authentication
        ↓
Real Firebase UID
        ↓
FastAPI authentication
        ↓
Firestore
        ↓
Persistent user profiles
        ↓
Real module assignments
        ↓
Real progress tracking
        ↓
Real assessments
        ↓
Certificate generation/verification
        ↓
Worker ID verification
        ↓
Admin/organization dashboard
        ↓
Safety Passport
        ↓
Real AR simulations
        ↓
FCM notifications
```

The UI implementation should be written so these services can be introduced behind clean repository/service interfaces later.

---

# 41. Final Product Flow

```text
                         ┌─────────────────────┐
                         │       WELCOME       │
                         └──────────┬──────────┘
                                    │
                    ┌───────────────┴────────────────┐
                    │                                │
              GET STARTED                  ALREADY HAVE ACCOUNT
                    │                                │
                    ▼                                ▼
               LANGUAGE                           LOGIN
                    │                                │
                    ▼                                ▼
           TRAINING BENEFITS                    LOGIN OTP
                    │                                │
                    ▼                                ▼
                REGISTER                    TEMP AUTH SUCCESS
                    │                                │
                    ▼                                │
              REGISTER OTP                          │
                    │                                │
                    ▼                                │
           TEMP AUTH SUCCESS                         │
                    │                                │
                    ▼                                │
          TRAINING PROFILE                           │
                    │                                │
             ┌──────┴──────┐                         │
             │             │                         │
          LEARNER       WORKER                       │
             │             │                         │
             └──────┬──────┘                         │
                    │                                │
                    └──────────────┬─────────────────┘
                                   ▼
                              DASHBOARD
                                   │
             ┌─────────────┬───────┼────────┬─────────────┐
             ▼             ▼       ▼        ▼             ▼
           LEARN         FIELD     AR      PROGRESS      PROFILE
             │           BOOK    TRAINING
             ▼
       MODULE DETAIL
             │
       ┌─────┼───────────────┐
       ▼     ▼               ▼
     LEARN   AR          ASSESSMENT
       │     │               │
       │     │               ▼
       │     │          COMPLETION
       │     │               │
       │     │               ▼
       │     │          CERTIFICATE
       │     │
       │     └── Coming Soon / Future AR
       │
       └── Progress updates only from real local/backend state


LEARNER → gets real Worker ID → adds Worker ID → admin verification
                                      │
                                      ▼
                              VERIFIED WORKER
                                      │
             ┌────────────┬───────────┼──────────────┐
             ▼            ▼           ▼              ▼
        Assigned      Compliance   Safety         Employer/
        Training      Records      Passport       Admin Tracking
```

This is the complete target product workflow for the current UI/navigation implementation.
