# TableFlow — table reservations and ordering for a small restaurant

An Android app (Kotlin + Jetpack Compose) for restaurant table reservations
and ordering: browsing the menu, a cart with automatic price calculation
(discounts, VAT), and table booking with local seat availability checking.

A portfolio project — set in the restaurant/hospitality industry, one of the
domains where software houses build projects for external clients.

## Tech stack and the role of each piece

| Layer | Technology | Role |
|---|---|---|
| Language | Kotlin | The entire project, both the logic (`:shared` module) and the UI (`:app` module) |
| UI | Jetpack Compose + Material 3 | Declarative interface — menu screen, cart, reservations |
| Navigation | Navigation Compose | Switching between screens (bottom navigation bar) |
| State / architecture | ViewModel + StateFlow | Separating UI logic from views — the standard MVVM pattern on Android |
| Local database | Room | Persisting reservations on the device |
| Concurrency | Kotlin Coroutines | Database operations without blocking the UI thread |
| Business logic | pure Kotlin module (`:shared`) | Price calculation and reservation availability checks — with zero Android dependency |
| Tests | JUnit4 | Unit tests for the business logic in the `:shared` module |

## Why the `:shared` module matters

All business logic — calculating order price with discounts and VAT, checking
whether a given reservation slot has room — lives in a separate Kotlin module
with no dependency on Android. This is a deliberate architectural choice:
this module could be moved into `commonMain` in a Kotlin Multiplatform setup
without changes (shared code across Android, iOS, and web).

## Repository structure

tableflow/
├── shared/ # pure Kotlin module (no Android)
│ └── src/
│ ├── main/kotlin/.../model/ # domain models
│ ├── main/kotlin/.../logic/ # PricingCalculator, ReservationAvailability
│ └── test/kotlin/.../logic/ # JUnit4 tests
├── app/ # Android module
│ └── src/main/
│ ├── kotlin/com/tableflow/
│ │ ├── data/ # Room: entities, DAO, database, mappers
│ │ ├── viewmodel/ # OrderViewModel, ReservationViewModel
│ │ ├── ui/screens/ # MenuScreen, ReservationScreen (Compose)
│ │ ├── ui/theme/ # Material 3 theme
│ │ ├── navigation/ # Navigation Compose
│ │ └── MainActivity.kt
│ ├── res/values/
│ └── AndroidManifest.xml
├── build.gradle.kts / settings.gradle.kts


## Getting started

1. Open the `tableflow/` folder in **Android Studio** (Koala or newer).
2. Wait for the Gradle sync (it will fetch the Compose/Room/Navigation dependencies).
3. Run on an emulator or a physical device: **Run → app**.

### Running the business logic tests

Easiest way: in Android Studio, right-click the `shared/src/test` folder →
**Run Tests**. (The repo doesn't include a generated Gradle Wrapper yet —
Android Studio will add one automatically the first time you open the project.)

## Possible next steps

- A real Kotlin Multiplatform module (adding `iosMain` alongside Android)
- Syncing reservations with a backend
- Push notifications for reservation status
- UI tests (Compose Testing) for the screens
