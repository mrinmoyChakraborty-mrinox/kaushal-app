# KAUSHAL

### Mobile AR Vocational Training & Safety Certification for Industrial Workers

KAUSHAL is a mobile-first, scenario-based vocational training and safety certification platform designed for workers in **Jharkhand's mining, steel manufacturing, and mica-processing sectors**.

Instead of relying only on static manuals or classroom demonstrations, KAUSHAL uses **interactive learning, mobile AR practical simulations, assessments, and digital certification** to help learners practice safety decisions in realistic workplace scenarios using an ordinary Android smartphone.

> **Hackathon MVP:** The current implementation focuses on a reusable Unity AR training engine with three planned practical modules, with **Fire & Explosion Response** and **Gas Leak & Confined Space Protocol** as the submission-critical demonstrations.

## Why KAUSHAL?

```text
Learn
  ↓
Understand the situation
  ↓
Make a decision
  ↓
Practice in AR
  ↓
Get feedback
  ↓
Take an assessment
  ↓
Earn a verifiable result
```

KAUSHAL is a **training and comprehension platform**. It does not replace formal workplace authorization or site-specific safety procedures.

## Core Features

* Mobile AR training on Android smartphones
* Scenario-based workplace learning
* Interactive safety decisions with immediate feedback
* Practical AR simulations
* Formal assessments with score and pass/retry outcomes
* Hindi and Santali localization
* Offline-capable AR sessions
* QR-based certificate generation and verification
* Web administration/compliance dashboard
* Data-driven scenario architecture for future expansion

## Product Scope

| Module   | Area                                       | Current AR Scope  |
| -------- | ------------------------------------------ | ----------------- |
| Module 1 | Fire & Explosion Response                  | ✅ Priority        |
| Module 2 | Gas Leak & Confined Space Protocol         | ✅ Priority        |
| Module 3 | Machinery Safety                           | 🔄 Third priority |
| Module 4 | PPE & Workplace Safety                     | Roadmap           |
| Module 5 | Mine Laws, Safety Duties & Workers' Rights | Roadmap           |

### Current submission target

```text
🔥 Fire & Explosion Response       → Complete AR module
☣️ Gas Leak & Confined Space      → Complete AR module
⚙️ Machinery Safety               → Same engine, add if time allows
```

## AR Training Experience

### Fire & Explosion Response

The Fire module follows a connected emergency story rather than a collection of unrelated questions.

```text
Start
  ↓
Place AR environment
  ↓
Fire emergency begins
  ↓
Identify / select appropriate extinguisher
  ↓
Identify designated exit
  ↓
Follow escape route
  ↓
Reach assembly point
  ↓
Complete assessment
  ↓
Score
  ↓
PASS / RETRY
```

### Gas Leak & Confined Space Protocol

```text
Start
  ↓
Place AR environment
  ↓
Gas hazard detected
  ↓
Scan / inspect atmosphere
  ↓
Identify hazard zone
  ↓
Select relevant PPE
  ↓
Buddy / attendant interaction
  ↓
Choose safe response
  ↓
Complete assessment
  ↓
Score
  ↓
PASS / RETRY
```

### Machinery Safety

```text
AR machine
  ↓
Identify dangerous component
  ↓
Identify required PPE
  ↓
Identify emergency stop
  ↓
Identify unsafe behaviour
  ↓
Choose correct action
  ↓
Assessment
```

## Architecture

```text
┌──────────────────────────────────────────────┐
│              Flutter / Android               │
│                                              │
│ Login • Module Library • Learning UI         │
│ Quiz • Certificates • QR • Offline Data      │
│ Localization • Navigation                   │
└──────────────────────┬───────────────────────┘
                       │
                 Unity AR Bridge
                       │
┌──────────────────────▼───────────────────────┐
│                  Unity AR                    │
│                                              │
│ AR Session • Plane Detection • Placement    │
│ Scenario Engine • Interactions • Feedback   │
│ Checkpoints • Assessment • Scoring           │
│ Result Generation • Asset Catalog            │
└──────────────────────┬───────────────────────┘
                       │
                  Result / Progress
                       │
┌──────────────────────▼───────────────────────┐
│             Backend / Dashboard              │
│                                              │
│ FastAPI • Data • Compliance Dashboard        │
│ Worker Management • Progress • Verification │
└──────────────────────────────────────────────┘
```

## Unity Design Philosophy

### One reusable engine

```text
ARTraining.unity
       │
       ├── Fire Scenario
       ├── Gas Scenario
       └── Machinery Scenario
```

Gameplay logic should be shared. Sector and scenario differences should be represented through data rather than duplicated Unity projects or hard-coded scripts.

### Scenario states

```text
INTRO
PLACE_ENVIRONMENT
SHOW_OBJECTIVE
START_SCENARIO
SPAWN_OBJECT
WAIT_FOR_INTERACTION
CHECK_ACTION
SHOW_FEEDBACK
NEXT_STEP
ASSESSMENT
RESULT
COMPLETE
```

### Interaction types

```text
TAP
SELECT
DRAG
PLACE
SCAN
IDENTIFY
ENTER_ZONE
REACH_CHECKPOINT
FOLLOW_ROUTE
DECISION
```

## Sector Architecture

```text
MINING
├── Common Mining Chapters
├── Opencast / Surface Mining
└── Underground Mining

STEEL MANUFACTURING
├── Common Steel Chapters
├── Hot Metal Production
└── Rolling Mills & Processing

MICA PROCESSING
├── Common Mica Chapters
├── Scrap Mining / Artisanal Collection
└── Mica Processing & Splitting
```

## Asset System

KAUSHAL is intentionally **placeholder-first**.

Gameplay must work before the final 3D assets arrive.

Stable asset IDs include:

```text
ENV_MINING_GALLERY
CHAR_WORKER
CHAR_BUDDY

PROP_EXIT_SIGN
PROP_ESCAPE_ARROW
PROP_ASSEMBLY_POINT
PROP_BARRICADE

PROP_FIRE_EXTINGUISHER
PROP_ELECTRICAL_PANEL

PROP_GAS_DETECTOR
PROP_GAS_WARNING_SIGN
PROP_CONFINED_SPACE_ENTRY

PROP_HELMET
PROP_SAFETY_VEST
PROP_RESPIRATOR
PROP_GLOVES
PROP_BOOTS

PROP_EXCAVATOR
PROP_CONVEYOR
PROP_EMERGENCY_STOP

FX_FIRE
FX_SMOKE
FX_GAS
FX_SPARK
FX_ALARM
```

Interactive prefabs follow:

```text
AssetPrefab
├── Visual
├── InteractionAnchor
├── Collider
└── AssetMetadata
```

This allows real models to replace placeholders without rewriting the training logic.

## Safety Content Principles

KAUSHAL deals with real-world industrial safety.

The implementation must not invent site-specific procedures or unsupported operational details.

Where the source content refers to:

* site-approved procedures
* authorized personnel
* competent persons
* trained persons
* model-specific procedures
* required permit/isolation/communication controls

those constraints must be preserved.

The AR system is intended to teach **hazard recognition, decision making, safe sequencing, and procedural discipline**.

## Offline-First

A practical AR session should run without a live network connection:

```text
Local scenario data
       ↓
Local AR session
       ↓
Local scoring
       ↓
Result generated
       ↓
Sync later
```

## Mobile Performance

Target:

* Android 10+
* ARCore-compatible smartphones
* Mid-range Android hardware
* No external VR headset

Priorities:

* Low-poly geometry
* Reasonable texture sizes
* Simple shaders
* Limited realtime lighting
* Lightweight VFX
* Limited simultaneous objects
* Reusable prefabs

## Repository Structure

```text
Assets/
└── KAUSHAL/
    ├── Core/
    │   ├── Runtime/
    │   ├── Editor/
    │   └── Interfaces/
    ├── AR/
    │   ├── Runtime/
    │   └── Prefabs/
    ├── Modules/
    │   ├── Fire/
    │   ├── GasConfined/
    │   └── Machinery/
    ├── Data/
    │   ├── Modules/
    │   ├── Scenarios/
    │   └── AssetCatalog/
    ├── AssetsSource/
    ├── Prefabs/
    ├── Scenes/
    │   └── ARTraining.unity
    ├── UI/
    ├── Audio/
    └── Documentation/
```

## Result Format

```json
{
  "module": "fire_explosion",
  "sector": "mining",
  "variant": "underground",
  "scenario": "emergency_escape",
  "score": 92,
  "passed": true,
  "attempts": 1,
  "durationSeconds": 94
}
```

## Flutter / Android Integration

Conceptual Unity bridge:

```text
StartModule(...)
StartScenario(...)
SendProgress(...)
CompleteScenario(...)
SendResult(...)
ExitAR()
```

Unity owns the AR practical experience. Flutter/Android owns the broader application shell.

## Development Priorities

```text
1. Inspect existing Unity project
2. Preserve existing work
3. Set up AR architecture
4. Build generic AR scene
5. Plane detection + placement
6. Placeholder environment
7. Asset Catalog
8. Interaction system
9. Scenario state machine
10. Feedback / mistake replay
11. Scoring + assessment
12. Fire scenario
13. Gas scenario
14. Machinery if time allows
15. Asset import automation
16. Android build + device test
17. Flutter bridge integration
```

The MVP priority is:

```text
FUNCTIONAL AR
    >
POLISHED ASSETS
```

## Acceptance Criteria

### Fire

A tester should be able to:

* start the scenario
* place the AR environment
* encounter the hazard
* perform a meaningful interaction
* make a safety decision
* receive feedback
* complete a route/checkpoint interaction
* reach the assembly point
* complete assessment
* receive a score
* receive a pass/retry result
* complete the scenario offline

### Gas

A tester should be able to:

* start the scenario
* place the AR environment
* encounter a gas/confined-space hazard
* perform an atmospheric-check interaction
* identify the hazard zone
* perform a PPE/role interaction
* make a safe-response decision
* receive feedback
* complete assessment
* receive a score
* receive a pass/retry result
* complete the scenario offline

## Technology Direction

```text
Unity
AR Foundation
ARCore
C#
```

Wider product:

```text
Flutter / Android
Unity AR
FastAPI
Web Administration Dashboard
Offline Storage
QR Verification
```

Exact Unity package versions and project settings should be taken from the repository's current project configuration.

## Project Status

KAUSHAL is currently being developed as a **hackathon-focused prototype/MVP**.

The immediate goal is to demonstrate:

```text
Scenario
  ↓
AR practical interaction
  ↓
Safety decision
  ↓
Feedback
  ↓
Assessment
  ↓
Score
```

on an ordinary Android device.

## Supporting Documentation

```text
README.md
KAUSHAL_CONTEXT.md
ANTIGRAVITY_PROMPT.md
ASSET_CREDITS.md
```

## Contributing

Contributions should:

1. Reuse the shared AR engine.
2. Prefer data-driven scenarios over hard-coded module logic.
3. Keep placeholder assets interchangeable.
4. Preserve offline AR functionality.
5. Respect mobile performance constraints.
6. Avoid unsupported safety instructions.
7. Keep Unity and Flutter responsibilities separated.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Third-party models, textures, sounds, fonts, and other resources remain subject to their individual licenses and attribution requirements.

---

### KAUSHAL

**Learn safely. Practice practically. Certify digitally.**
