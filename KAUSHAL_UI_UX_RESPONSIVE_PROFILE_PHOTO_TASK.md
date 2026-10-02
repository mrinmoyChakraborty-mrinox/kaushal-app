# KAUSHAL — UI/UX Enhancement + Responsive Design + Profile Photo + Notification Prompt
# Combined implementation task
# This file keeps ALL previous fixes and adds the new requested changes.

---

## 0. Scope

Implement these changes in the current KAUSHAL Worker Android app without rewriting the existing navigation/auth architecture.

### Previous fixes that MUST remain

1. Learn screen keeps the bottom navigation visible.
2. Profile screen keeps the bottom navigation visible.
3. Dashboard has a profile icon.
4. Dashboard notification icon opens a popup.
5. Remove the standalone **Emergency Response** module from Learning Modules.
6. English / Hindi / Santali language selection works for the **whole app**, not only the language screen.
7. Profile → Language opens the existing language selector.
8. Changing language does not log the user out.
9. Internal navigation keys remain stable and are never translated.

### New changes in this task

1. Make the complete app responsive for different Android phone/tablet sizes and orientations.
2. Redesign the Dashboard so it is visually richer and more polished.
3. Darken the Dashboard upper/hero background image with an overlay.
4. Keep the “Good morning” section readable against the darker background.
5. Move the Dashboard profile icon from the right side to the left side.
6. Keep the notification action on the right side.
7. Replace the existing notification popup with the popup style shown in the provided reference image.
8. Add profile-photo upload in the Profile section.
9. Add a **Create KAUSHAL Account** photo section where the user can add a profile photo.
10. After a user selects/uploads a photo during account creation, show the same uploaded photo in the Profile section.
11. Photo selection should support replace/remove where practical.
12. Do not add fake user data or fake training statistics.

---

# 1. Responsive Design — Entire App

## Goal

The UI must work correctly on:

```text
Small Android phones
Normal Android phones
Large Android phones
Foldables / wider screens
Android tablets
Portrait orientation
Landscape orientation
Different font/display scaling
```

Do not design only for one fixed phone screenshot.

---

## 1.1 Responsive layout rules

Avoid hard-coded full-screen dimensions such as:

```kotlin
height(800.dp)
width(360.dp)
fillMaxWidth(360.dp)
```

Do not position major UI using fixed pixel-like offsets.

Prefer:

```kotlin
fillMaxWidth()
fillMaxHeight()
weight(1f)
wrapContentHeight()
wrapContentWidth()
padding(...)
Arrangement.SpaceBetween
```

Use constraints when a specific max content width is useful.

---

## 1.2 Recommended Compose approach

Use `WindowSizeClass` or `BoxWithConstraints` for layout adaptation.

The app should have at least these responsive behaviors:

### Compact width

Typical phone:

```text
1-column layout
stack cards vertically
small horizontal padding
dashboard sections scroll vertically
```

### Medium / expanded width

Tablet or large landscape:

```text
2-column sections where appropriate
larger hero content
more horizontal spacing
cards can sit side-by-side
```

Do not simply enlarge every component.

---

## 1.3 Dashboard responsive layout

Compact:

```text
Hero / greeting
       ↓
Quick stats
       ↓
Continue training
       ↓
Learning modules
       ↓
Recent/Progress visualization
       ↓
Bottom navigation
```

Expanded:

```text
┌────────────────────────────────────────────┐
│ Hero / Greeting                            │
├───────────────────┬────────────────────────┤
│ Progress / Safety │ Continue Training      │
│ visualization     │                        │
├───────────────────┴────────────────────────┤
│ Learning modules / actions                 │
└────────────────────────────────────────────┘
```

Use the same data/components; only layout changes.

---

# 2. Dashboard — Make the UI More Visual

## File

```text
app/src/main/java/com/kaushal/worker/screens/home/DashboardScreen.kt
```

The existing Dashboard currently looks too plain/normal.

The redesign should feel like a modern safety-training dashboard rather than a simple list of buttons.

---

## 2.1 Keep the existing information architecture

Do not remove useful existing dashboard actions.

Keep:

```text
Greeting
Learning journey
Learning modules
My Progress
Certificates
Field Book
AR Training
Safety Passport
Quick actions
```

But present them through stronger visual hierarchy.

---

# 3. New Dashboard Visual Structure

Recommended order:

```text
┌──────────────────────────────────────────────┐
│ 👤  Good morning,                          🔔│
│     KAUSHAL Learner                          │
│                                              │
│     Start Your Safety Learning Journey       │
│                                              │
│     [ Continue Training ]                    │
└──────────────────────────────────────────────┘

        Progress / Safety overview

┌──────────────┐ ┌──────────────┐
│ Progress     │ │ Certificates │
│   ring       │ │   status     │
└──────────────┘ └──────────────┘

        Continue learning

┌──────────────────────────────────────────────┐
│ Fire & Explosion Response                    │
│ progress / action                            │
│                    [ Continue ]              │
└──────────────────────────────────────────────┘

        Quick Actions

┌────────────┐ ┌────────────┐
│ Field Book │ │ AR Training│
└────────────┘ └────────────┘

Bottom Navigation
```

---

# 4. Important Rule — No Fake Visualization Data

The Dashboard may become more visual, but do not invent statistics.

Do NOT add hard-coded fake values like:

```text
75% completed
12 modules completed
7 day streak
92 safety score
4 certificates
```

unless those values actually come from the existing app/session/backend.

Instead:

### When data exists

Show the real value.

### When data does not exist

Use an honest empty state such as:

```text
0%
No completed modules yet
Start learning to track your progress
```

or equivalent localized text.

---

# 5. Recommended Dashboard Visual Components

Implement only data-backed components.

## 5.1 Progress Ring

Show the current training completion.

Data source:

```text
existing progress/session/backend state
```

If there is no completed training yet:

```text
0%
```

with a clear empty-state message.

Use:

```text
CircularProgressIndicator
```

or a custom Compose drawing.

Make sure the ring scales correctly on different screen sizes.

---

## 5.2 Continue Training Card

Show the next available real module when such data exists.

Example structure:

```text
[Module icon]

Fire & Explosion Response

[real progress if available]

CONTINUE
```

Do not fabricate module progress.

If no real progress exists:

```text
Start your first training module
```

---

## 5.3 Quick Action Cards

Use visually distinct cards for existing features:

```text
Field Book
AR Training
Certificates
Progress
```

These should use:

```text
Icon
Title
short localized description where useful
```

Keep each card tappable.

---

# 6. Dashboard Upper Section — Darker Background

## Current issue

The upper background/hero image is too light.

The “Good morning” text does not have enough contrast.

---

## Required change

Keep the existing background image concept, but place a dark overlay above the image.

Recommended Compose structure:

```kotlin
Box {
    Image(...)

    Box(
        Modifier
            .matchParentSize()
            .background(
                Color.Black.copy(alpha = 0.45f)
            )
    )

    // Hero content
}
```

The exact alpha can be tuned visually.

Start around:

```text
0.40 – 0.55
```

and adjust until text is clearly readable.

Do not make the whole app dark.

Only the upper/hero area should receive the stronger dark overlay.

---

# 7. Dashboard Greeting

Keep:

```text
Good morning
```

and the user's real displayed name.

Example:

```text
Good morning,
Koyel
```

Use the real session/profile name.

Do not replace the name with mock text.

The greeting must remain readable on top of the darkened image.

---

# 8. Move Profile Icon to the LEFT

## Current requirement

The dashboard profile icon is currently on the right side.

Move it to the left side of the upper header.

### Required header structure

```text
┌──────────────────────────────────────────────┐
│ 👤   Good morning,                     🔔    │
│      KAUSHAL Learner                         │
└──────────────────────────────────────────────┘
```

### Behavior

Profile icon:

```text
tap → Profile screen
```

Notification icon:

```text
tap → notification prompt
```

### Important

Do not move both icons to the left.

Keep:

```text
Profile → left
Notification → right
```

---

# 9. Profile Icon Should Become a Real Avatar When Photo Exists

The profile icon can work as follows:

### No photo

Show:

```text
Icons.Default.Person
```

### Photo exists

Show:

```text
CircleImage / Avatar
```

using the uploaded user photo.

Therefore the Dashboard itself should eventually show the same uploaded avatar beside the greeting.

This must use the same central profile-photo state as the Profile screen.

---

# 10. Notification Popup — Follow Provided Image

Use the user-provided reference image as the visual design reference.

Reference structure:

```text
┌──────────────────────────────┐
│            🔔                │
│                              │
│       Stay Updated           │
│                              │
│ Turn on notifications to get │
│ important updates about your │
│ assigned training, new       │
│ modules, assessments, and    │
│ certificates.                │
│                              │
│ ┌──────────────────────────┐ │
│ │  Turn On Notifications   │ │
│ └──────────────────────────┘ │
│                              │
│          Not Now             │
└──────────────────────────────┘
```

The supplied image is the visual reference for:

```text
spacing
card shape
button hierarchy
text hierarchy
icon position
overall compact layout
```

---

# 11. Notification Popup Content

Use these concepts as the source strings:

### Title

```text
Stay Updated
```

### Description

```text
Turn on notifications to get important updates about your assigned training, new modules, assessments, and certificates.
```

### Primary button

```text
Turn On Notifications
```

### Secondary action

```text
Not Now
```

All of these MUST go into the localization resources.

Do not hard-code them directly in `AlertDialog`.

---

# 12. Notification Popup Style

The popup should be a custom-looking Material 3 dialog rather than the previous simple generic dialog.

Recommended characteristics:

```text
rounded corners
compact width
comfortable internal padding
notification bell icon at top
centered title
centered description
full-width primary button
text-style secondary action
```

The primary action should visually match the KAUSHAL design system.

The button may use the existing KAUSHAL orange/accent.

Do not make the dialog huge on tablets.

Use a constrained width such as:

```text
280.dp–360.dp
```

depending on screen width.

---

# 13. Notification Permission Behavior

The popup is the app's own explanation/request UI.

When the user taps:

```text
Turn On Notifications
```

request the real Android notification permission where required.

For Android 13+:

```text
android.permission.POST_NOTIFICATIONS
```

For Android versions where notification runtime permission is not required:

```text
do not trigger a nonexistent runtime permission dialog
```

After the action:

```text
close the custom popup
```

### Important

Do not permanently fake notification permission as granted.

Use the actual Android permission state.

---

# 14. Not Now Behavior

When the user taps:

```text
Not Now
```

simply:

```text
close popup
```

Do not log the user out.

Do not navigate away from Dashboard.

Do not show fake notifications.

---

# 15. Profile Photo Upload — Profile Screen

## File

```text
app/src/main/java/com/kaushal/worker/screens/profile/ProfileScreen.kt
```

The Profile section must contain a visible profile avatar area.

### No image state

Display:

```text
large circular profile placeholder
Person icon
Add Photo / Upload Photo action
```

Example:

```text
       ┌─────────┐
       │   👤    │
       │   +     │
       └─────────┘

       Add Photo
```

---

# 16. Profile Photo Picker

Use the Android activity result photo picker / content picker.

Recommended direction:

```text
ActivityResultContracts.PickVisualMedia
```

or a compatible picker already used by the project.

The user should be able to select an image from the device.

Supported user flow:

```text
Profile
   ↓
Tap avatar / Add Photo
   ↓
System photo picker
   ↓
Select image
   ↓
Preview immediately
   ↓
Store photo reference
```

---

# 17. Photo Actions

Once a profile image exists, provide:

```text
Change Photo
```

and optionally:

```text
Remove Photo
```

If Remove Photo is implemented:

```text
uploaded photo → remove → default person icon
```

Do not delete a backend file unless the backend upload has actually been implemented.

---

# 18. Create KAUSHAL Account — Add Photo Section

## File

```text
app/src/main/java/com/kaushal/worker/screens/auth/AuthScreens.kt
```

The account creation screen must include a profile-photo section.

---

# 19. Create KAUSHAL Account UI

Recommended layout:

```text
Create Your KAUSHAL Account

Enter your details to get started.


             ┌─────────┐
             │   👤 +  │
             └─────────┘

              Add Photo
              Optional


[ 📱  Mobile Number ]

[ 👤  Full Name ]

[ CREATE ACCOUNT ]
```

The photo section should be visually prominent but must not make registration unnecessarily difficult.

---

# 20. Account Photo Rules

Profile photo should be:

```text
optional
```

unless the existing product requirements explicitly make it mandatory later.

### If user does not upload

Account creation still works.

### If user uploads

Account creation continues with the selected image reference.

Do not block registration just because no photo was selected.

---

# 21. Important — Same Photo Must Appear in Profile

The profile image must not be maintained independently by the Register screen and Profile screen.

Use one source of truth.

Required flow:

```text
Create Account
      ↓
User selects photo
      ↓
Temporary/session profile image updated
      ↓
Account created / authenticated
      ↓
Dashboard
      ↓
Profile
      ↓
same selected photo shown
```

---

# 22. Central Profile Photo State

## File

```text
app/src/main/java/com/kaushal/worker/TemporaryAppViewModel.kt
```

Extend the existing user/profile model with a photo reference.

Conceptually:

```text
Profile
 ├── name
 ├── phone
 ├── workerId
 ├── language
 └── profilePhotoUri
```

Example:

```kotlin
val profilePhotoUri: String? = null
```

Use a URI/path/reference string rather than storing a large bitmap directly in Compose state.

---

# 23. Do NOT Store Bitmap in ViewModel

Avoid:

```kotlin
Bitmap?
```

inside the main session state.

Store:

```text
URI / persisted URI string / backend storage URL
```

instead.

The UI can decode/load the image when rendering.

---

# 24. Image State Synchronization

The following screens should read the same profile image:

```text
Create Account
Dashboard
Profile
```

Potential future locations:

```text
Worker ID / Safety Passport
Admin profile preview
Certificate
```

No screen should create a separate independent avatar state unless there is a specific reason.

---

# 25. Dashboard Avatar Synchronization

Dashboard should observe:

```text
session.profile.profilePhotoUri
```

### No image

```text
Person icon
```

### Image exists

```text
Circular uploaded image
```

This ensures the profile avatar updates automatically after the user uploads a picture.

---

# 26. Image Loading

Use an image-loading method appropriate for the project.

Recommended for Compose:

```text
Coil
```

if already available / acceptable for the current project.

For a local URI:

```text
AsyncImage(
    model = profilePhotoUri,
    ...
)
```

with:

```text
placeholder = person icon
error = person icon
```

Do not crash if the URI becomes invalid.

---

# 27. Account Creation Photo Persistence

There are two valid implementation stages.

## Current temporary/local stage

Persist:

```text
profilePhotoUri
```

in the existing local session/state.

This is sufficient for UI/navigation testing.

## Production/Firebase stage

Upload the photo to:

```text
Firebase Storage
```

and save the resulting URL in the user's profile document.

Recommended conceptual structure:

```text
users/{uid}/profilePhoto
```

or:

```text
users/{uid}
    profilePhotoUrl
```

Do not add Firebase Storage code if the current project intentionally remains in its temporary-auth testing phase. The UI/state architecture should still make the later Storage integration straightforward.

---

# 28. Profile Photo Placeholder Design

The placeholder should match KAUSHAL's visual language.

Recommended:

```text
circular shape
soft border
subtle surface
person icon
small + badge
```

On tap:

```text
open photo picker
```

The avatar should remain visually clear in:

```text
light mode
darkened dashboard hero
small phone screen
tablet screen
```

---

# 29. Profile Screen Header

Recommended:

```text
← / header
        Profile

        ┌─────────────┐
        │             │
        │ uploaded    │
        │ photo       │
        │             │
        └─────────────┘

        Change Photo

        User Name
        Learner
```

Do not hide the profile photo below the fold.

The photo should appear near the top of Profile.

---

# 30. Responsive Profile Photo Size

Do not use one huge hard-coded size for all devices.

Recommended approach:

```text
compact phone → approximately 88–104 dp
medium/large screen → approximately 104–128 dp
```

Use a bounded size rather than growing indefinitely.

---

# 31. Responsive Typography

Do not use unusually large fixed fonts that overflow on compact devices.

Check:

```text
Good morning
dashboard title
module titles
dialog description
profile labels
account creation text
```

for:

```text
single-line overflow
unexpected wrapping
clipped text
button text clipping
```

Allow natural multi-line wrapping where necessary.

---

# 32. Responsive Notification Dialog

The supplied reference image is a compact phone-style popup.

On larger devices:

```text
keep the dialog centered
keep a sensible maximum width
do not stretch it edge-to-edge
```

On small phones:

```text
respect screen padding
allow description to wrap
keep button fully visible
```

---

# 33. Dashboard Scrolling

Dashboard content must scroll naturally.

Use:

```kotlin
verticalScroll(rememberScrollState())
```

or a `LazyColumn`.

Prefer `LazyColumn` when the dashboard contains multiple dynamic sections.

Do not put a large fixed-height `Column` inside a screen and expect every device to fit.

---

# 34. Bottom Navigation Responsiveness

Bottom navigation must remain reachable on all supported devices.

Make sure:

```text
contentPadding
navigationBarsPadding()
Scaffold innerPadding
```

are handled correctly.

The main content must never be hidden behind the bottom navigation bar.

---

# 35. Safe Area / System Insets

Handle:

```text
status bar
display cutouts/notches
navigation bar
gesture navigation
```

Do not use hard-coded top/bottom offsets.

Use the Material/Compose inset APIs already compatible with the project.

---

# 36. Language Localization Must Remain Fully Functional

The UI redesign MUST NOT break the previous localization requirement.

All new strings introduced in this task must be localized.

Required resource groups:

```text
app/src/main/res/values/strings.xml
app/src/main/res/values-hi/strings.xml
app/src/main/res/values-b+sat+Olck/strings.xml
```

New strings include at least:

```text
Stay Updated
Turn on notifications to get important updates about your assigned training, new modules, assessments, and certificates.
Turn On Notifications
Not Now

Add Photo
Change Photo
Remove Photo
Create Your KAUSHAL Account
Optional
Upload Photo
```

and every new Dashboard label.

---

# 37. Language Switching Must Affect the New UI Too

When Hindi is selected:

```text
Dashboard hero
Dashboard cards
Notification popup
Profile photo labels
Account photo labels
Bottom navigation
```

must use Hindi resources.

When Santali is selected:

```text
Dashboard hero
Dashboard cards
Notification popup
Profile photo labels
Account photo labels
Bottom navigation
```

must use Santali/Ol Chiki resources.

No newly added English strings may remain hard-coded.

---

# 38. Suggested File Change Map

## Dashboard

```text
app/src/main/java/com/kaushal/worker/screens/home/DashboardScreen.kt
```

Change:

```text
responsive layout
hero background overlay
greeting/header
profile icon position
notification icon
notification dialog
data-driven visual cards
profile avatar binding
```

---

## Profile

```text
app/src/main/java/com/kaushal/worker/screens/profile/ProfileScreen.kt
```

Change:

```text
profile avatar
photo picker
change photo
remove photo if implemented
language navigation
bottom navigation
responsive layout
```

---

## Authentication

```text
app/src/main/java/com/kaushal/worker/screens/auth/AuthScreens.kt
```

Change:

```text
Create KAUSHAL Account photo section
photo picker
photo preview
photo reference passed to account/session state
responsive registration layout
localized strings
```

---

## Session / ViewModel

```text
app/src/main/java/com/kaushal/worker/TemporaryAppViewModel.kt
```

Change:

```text
profilePhotoUri
central profile photo update method
preserve image through navigation
```

---

## Shared Components

```text
app/src/main/java/com/kaushal/worker/ui/components/Common.kt
```

Review/change:

```text
BottomNavigationBar
shared Avatar component if useful
buttons
cards
spacing
navigation insets
```

Do not duplicate existing shared UI components.

---

## Navigation

```text
app/src/main/java/com/kaushal/worker/navigation/AppNavigation.kt
```

Verify:

```text
Dashboard → Profile
Profile → Language
Learn → bottom nav
Profile → bottom nav
```

and make sure the profile photo state is available to all relevant screens.

---

## Localization

```text
app/src/main/java/com/kaushal/worker/localization/AppLanguage.kt
app/src/main/res/values/strings.xml
app/src/main/res/values-hi/strings.xml
app/src/main/res/values-b+sat+Olck/strings.xml
```

Keep previous localization implementation intact.

---

# 39. Suggested Component Extraction

If `DashboardScreen.kt` becomes too large, extract only reusable UI sections.

Recommended optional components:

```text
DashboardHero
DashboardProgressCard
ContinueTrainingCard
QuickActionCard
NotificationPrompt
ProfileAvatar
```

Do not split every tiny `Row`/`Column` into its own file.

Keep the code understandable.

---

# 40. Visual Design Direction

The Dashboard should feel:

```text
modern
safety-focused
visual
professional
friendly for workers/learners
easy to scan
not crowded
```

Avoid:

```text
plain text + many rectangular buttons
flat white screen
too many borders
too many colors
tiny text
overloaded cards
```

Use:

```text
clear hierarchy
rounded cards
soft elevation
consistent spacing
one strong accent
iconography
progress visualization
hero section
```

Maintain the existing KAUSHAL brand identity.

---

# 41. Dashboard Color/Contrast Rule

The upper background should be visually darker than the current version.

Required relationship:

```text
background image
      ↓
dark overlay
      ↓
white/high-contrast text
```

The overlay is for readability, not to obscure the image completely.

Test against the actual background image rather than choosing the final alpha only from code.

---

# 42. Do Not Replace the Background Image With a Random Image

The requirement is:

```text
keep the intended/current KAUSHAL background concept
make it darker
```

Do not introduce an unrelated stock image.

The developer can replace the asset later if the product team provides a new hero image.

---

# 43. Existing Emergency Response Fix Must Remain

Continue to remove the standalone:

```text
Emergency Response
```

from the learning module list.

Do not accidentally re-add it while redesigning the Dashboard's module cards.

This does NOT prohibit valid emergency-response content inside another safety module.

---

# 44. Testing Matrix

Test on at least:

```text
Small phone portrait
Normal phone portrait
Large phone
Landscape phone
Tablet / large width
```

Test:

```text
English
Hindi
Santali
```

---

# 45. Functional Test — Profile Photo

### Case 1 — No photo

```text
Create Account
→ skip photo
→ create account
→ Dashboard
→ profile placeholder visible
```

### Case 2 — Upload during registration

```text
Create Account
→ Add Photo
→ choose image
→ preview image
→ Create Account
→ Dashboard
→ avatar shows selected image
→ Profile
→ same image is shown
```

### Case 3 — Change from Profile

```text
Profile
→ Change Photo
→ choose another image
→ Profile updates
→ Dashboard avatar updates
```

### Case 4 — Invalid/missing image reference

```text
image cannot load
→ fallback person icon
→ app remains stable
```

---

# 46. Functional Test — Notification Popup

```text
Dashboard
→ tap bell
→ custom "Stay Updated" popup appears
```

Verify:

```text
icon visible
title visible
description visible
Turn On Notifications button visible
Not Now visible
rounded card
correct spacing
```

Then:

```text
Turn On Notifications
→ actual Android notification permission flow where required
```

And:

```text
Not Now
→ popup closes
→ remains on Dashboard
```

---

# 47. Functional Test — Navigation

```text
Dashboard
→ Learn
→ bottom nav remains
```

```text
Dashboard
→ Profile
→ bottom nav remains
```

```text
Profile
→ Language
→ select Hindi
→ return
→ Profile is Hindi
```

```text
Profile
→ Language
→ select Santali
→ return
→ Profile is Santali
```

---

# 48. Functional Test — Responsive UI

Check for:

```text
no clipping
no overlapping
no horizontal scrolling
no button text cut off
no avatar collision with status bar
no popup overflowing the screen
no bottom-nav overlap
no fixed-height black/empty areas
```

Use the Android emulator/device display scaling settings where possible.

---

# 49. Acceptance Criteria

## Responsive

- [ ] App works on small phones.
- [ ] App works on normal phones.
- [ ] App works on large screens/tablets.
- [ ] App behaves correctly in portrait.
- [ ] App behaves correctly in landscape.
- [ ] No important content is clipped.
- [ ] No major hard-coded screen dimensions remain.

## Dashboard

- [ ] Upper hero background is darker using an overlay.
- [ ] Good morning text is readable.
- [ ] Profile avatar/icon is on the LEFT.
- [ ] Notification icon is on the RIGHT.
- [ ] Dashboard has richer visual hierarchy.
- [ ] Progress/visual cards use real state only.
- [ ] No fake statistics are introduced.
- [ ] Dashboard scrolls properly.

## Notifications

- [ ] Bell opens the new custom popup.
- [ ] Popup follows the supplied reference layout.
- [ ] “Stay Updated” title exists.
- [ ] Description exists.
- [ ] “Turn On Notifications” primary button exists.
- [ ] “Not Now” exists.
- [ ] Real Android permission is requested where required.
- [ ] Popup is responsive.

## Profile Photo

- [ ] Profile screen has a large avatar area.
- [ ] User can select an image.
- [ ] User can see a preview.
- [ ] User can change the image.
- [ ] Profile falls back safely when no image exists.
- [ ] Dashboard and Profile use the same photo state.

## Create KAUSHAL Account

- [ ] Account creation screen has photo section.
- [ ] Photo upload is optional.
- [ ] Image can be selected before account creation.
- [ ] Selected image is preserved into authenticated session state.
- [ ] Profile shows the uploaded image after registration.
- [ ] Dashboard avatar can also reflect the uploaded image.

## Localization

- [ ] English works.
- [ ] Hindi works throughout the app.
- [ ] Santali/Ol Chiki works throughout the app.
- [ ] New UI strings are localized.
- [ ] Notification popup is localized.
- [ ] Photo-related labels are localized.
- [ ] Navigation labels remain localized.
- [ ] Language change does not log the user out.

## Previous fixes

- [ ] Emergency Response remains removed from Learning Modules.
- [ ] Learn bottom navigation remains fixed.
- [ ] Profile bottom navigation remains fixed.
- [ ] Profile navigation works.
- [ ] Profile → Language works.

---

# 50. Implementation Order

Do not implement everything randomly.

Recommended order:

### Phase 1
Responsive foundation:

```text
Scaffold
insets
scrolling
spacing
WindowSizeClass / constraints
```

### Phase 2
Dashboard visual redesign:

```text
hero
dark overlay
profile-left header
notification-right header
visual cards
progress
quick actions
```

### Phase 3
Notification popup:

```text
custom modal
notification permission
localized strings
```

### Phase 4
Profile photo foundation:

```text
profile model
photo URI state
picker
avatar component
```

### Phase 5
Create Account photo:

```text
picker
preview
store same profilePhotoUri
```

### Phase 6
Synchronization:

```text
Account
   ↓
Session
   ↓
Dashboard
   ↓
Profile
```

### Phase 7
Localization regression:

```text
English
Hindi
Santali
```

### Phase 8
Full responsive/device testing.

---

# 51. Final Expected Experience

```text
CREATE KAUSHAL ACCOUNT
        │
        ├── Add Photo (optional)
        │       ↓
        │   select image
        │       ↓
        │   preview
        │
        └── Create Account
                ↓
             Dashboard
                │
        ┌───────┴───────────┐
        │                   │
   👤 Profile           🔔 Notifications
      LEFT                  RIGHT
        │                   │
        ↓                   ↓
     Profile          Stay Updated
        │              [Turn On Notifications]
        │                    [Not Now]
        │
        ├── uploaded photo
        ├── Change Photo
        └── Language
              ↓
      English / Hindi / Santali
              ↓
        whole app changes
```

The final dashboard should look substantially more polished and visual than the current plain UI, while remaining lightweight and usable on mid-range Android devices.

---

# 52. Developer Constraint

Do not:

```text
rewrite the complete app
replace Navigation Compose
remove the existing session architecture
add fake statistics
add fake notifications
break the current authentication flow
create duplicate bottom-navigation components
create separate profile image states for each screen
hard-code English strings in newly added UI
```

Do:

```text
reuse current architecture
reuse existing components
keep route keys stable
centralize profile photo state
centralize localization
use real user/session data
use responsive Compose layouts
follow the supplied notification popup reference
```
