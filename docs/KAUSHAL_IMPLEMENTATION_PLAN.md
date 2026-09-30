# KAUSHAL — Implementation Plan

## Phase 1 — Foundation
1. Open the project in Android Studio.
2. Sync Gradle.
3. Confirm `MainActivity` launches Compose.
4. Confirm the KAUSHAL orange/navy theme.
5. Confirm there is one navigation host.

## Phase 2 — Temporary state
Use `TemporaryAppViewModel` only.
- `isTemporarilyAuthenticated`
- `isNewUser`
- temporary user profile
- language
- learner/worker
- sector/sub-sector
- department/role
- optional Worker ID
- Worker ID status = pending when an ID is entered

Do not add Firebase authentication in this milestone.

## Phase 3 — Authentication flow

### New user
Welcome → Language → Benefits → Register → Register OTP → Training Profile → Dashboard

### Existing user
Welcome → Login → Login OTP → Dashboard

Existing users must bypass Training Profile.

## Phase 4 — Profile
Validate:
- language
- user type
- sector
- sub-sector
- department
- role

Worker ID is optional.

## Phase 5 — Main app
Implement:
- Dashboard
- Learning Modules
- Module Detail
- Field Book
- AR placeholder
- Assessment placeholder
- Certificates empty state
- Progress
- Profile
- Logout

## Phase 6 — Visual refinement
Compare each screen with the supplied reference screenshots:
- white/off-white background
- dark navy typography
- bright orange CTA
- rounded cards and inputs
- large touch targets
- bottom navigation
- industrial visual language

The final industrial/worker artwork should be inserted later when supplied.

## Phase 7 — Testing matrix

### New learner
Welcome → Get Started → Language → Benefits → Register → OTP → Training Profile → Learner → profile fields → Dashboard

### New worker
Same flow, select Worker and optionally enter Worker ID. The app must show pending status, not verified status.

### Existing user
Welcome → Already Have Account → Login → OTP → Dashboard

### Main navigation
Dashboard → Learn → Module Detail → AR / Assessment → Certificates
Dashboard → Progress
Dashboard → Profile
Dashboard → Field Book

### Logout
Profile → Logout → Welcome

## Acceptance checks
- No compile errors
- No duplicate routes
- No fake Firebase UID/token
- No fake user names
- No fake progress
- No fake certificates
- No fake verification
- No black screen if artwork is absent
- Back navigation works
- Buttons work
- Forms validate
- Temporary profile values appear in Dashboard/Profile
