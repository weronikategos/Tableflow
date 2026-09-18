# TableFlow — rezerwacje stolików i zamówienia dla małej restauracji

Aplikacja na Androida (Kotlin + Jetpack Compose) do rezerwacji stolików
i składania zamówień w restauracji: przeglądanie menu, koszyk z automatycznym
naliczaniem cen (rabaty, VAT), rezerwacja stolika z lokalnym sprawdzaniem
dostępności miejsc.

Projekt portfolio — dobrany pod branżę gastronomiczną, bo to jeden z obszarów,
w których działają software house'y realizujące projekty dla klientów
zewnętrznych (nie tylko produkty własne).

## Stos technologiczny i rola każdego elementu

| Element | Technologia | Rola |
|---|---|---|
| Język | **Kotlin** | Cały projekt, zarówno logika (moduł `:shared`), jak i UI (moduł `:app`) |
| UI | **Jetpack Compose + Material 3** | Deklaratywny interfejs — ekran menu, koszyk, rezerwacje |
| Nawigacja | **Navigation Compose** | Przełączanie między ekranami (dolny pasek nawigacji) |
| Stan / architektura | **ViewModel + StateFlow** | Oddzielenie logiki UI od widoków — standardowy wzorzec MVVM w Androidzie |
| Baza danych lokalna | **Room** | Trwałe przechowywanie rezerwacji na urządzeniu |
| Współbieżność | **Kotlin Coroutines** | Operacje na bazie danych bez blokowania wątku UI |
| Logika biznesowa | **czysty moduł Kotlin (`:shared`)** | Naliczanie cen i sprawdzanie dostępności rezerwacji — bez żadnej zależności od Androida |
| Testy | **JUnit4** | Testy jednostkowe logiki biznesowej w module `:shared` |

## Dlaczego moduł `:shared` jest tu ważny (i dlaczego wspominam o tym na rozmowie)

Cała logika biznesowa — naliczanie ceny zamówienia z rabatem i VAT, sprawdzanie
czy jest miejsce na dany termin rezerwacji — jest napisana w osobnym module
Kotlin **bez żadnej zależności od Androida**. To świadoma decyzja
architektoniczna: taki moduł można w przyszłości przenieść bez zmian do
`commonMain` w projekcie Kotlin Multiplatform (współdzielony kod na Android,
iOS i web) — czyli dokładnie w kierunku, w jakim idzie ta oferta pracy
(KMP + Compose Multiplatform + Next.js na później).

## Struktura repozytorium

```
tableflow/
├── shared/                          # czysty moduł Kotlin (bez Androida)
│   └── src/
│       ├── main/kotlin/.../model/   # modele domenowe
│       ├── main/kotlin/.../logic/   # PricingCalculator, ReservationAvailability
│       └── test/kotlin/.../logic/   # testy JUnit4
├── app/                              # moduł Android
│   └── src/main/
│       ├── kotlin/com/tableflow/
│       │   ├── data/                # Room: encje, DAO, baza, mappery
│       │   ├── viewmodel/           # OrderViewModel, ReservationViewModel
│       │   ├── ui/screens/          # MenuScreen, ReservationScreen (Compose)
│       │   ├── ui/theme/            # Material 3 theme
│       │   ├── navigation/          # Navigation Compose
│       │   └── MainActivity.kt
│       ├── res/values/
│       └── AndroidManifest.xml
├── build.gradle.kts / settings.gradle.kts
```

## Uruchomienie

1. Otwórz folder `tableflow/` w **Android Studio** (Koala lub nowszy).
2. Poczekaj na synchronizację Gradle (pobierze zależności Compose/Room/Navigation).
3. Uruchom na emulatorze lub telefonie: **Run → app**.

### Same testy logiki biznesowej

Najprościej: w Android Studio kliknij prawym przyciskiem na folder
`shared/src/test` → **Run Tests**. (W repo nie ma jeszcze wygenerowanego
Gradle Wrappera — Android Studio doda go automatycznie przy pierwszym
otwarciu projektu.)

## Co dokładnie sama sprawdziłam, zanim to dostałaś

- **Moduł `:shared` kompiluje się i wszystkie testy przechodzą** — sprawdziłam
  to bezpośrednio kompilatorem Kotlina (7/7 testów zielonych: naliczanie
  ceny z rabatem i VAT, sprawdzanie dostępności rezerwacji w różnych
  scenariuszach).
- **Struktura projektu Gradle jest standardowa i poprawna** dla wielomodułowego
  projektu Android + czysty Kotlin.
- **Czego NIE sprawdziłam:** pełnej kompilacji modułu `:app` (kod UI, Room,
  ViewModel) — wymaga to Android SDK, emulatora i bibliotek Compose/Room,
  których nie mam w tym środowisku. Kod jest napisany zgodnie ze standardowymi,
  aktualnymi wzorcami (Compose + Material 3 + Navigation Compose + Room +
  StateFlow), ale **pierwsze co powinnaś zrobić, to otworzyć projekt w Android
  Studio i przejść przez ewentualne błędy kompilacji** — to normalne i to
  jest właśnie ta część, dzięki której realnie nauczysz się, jak te elementy
  działają razem.

## Możliwe kierunki rozwoju (dobre tematy na rozmowę o dalszym rozwoju projektu)

- Realny moduł Kotlin Multiplatform (dodanie `iosMain` obok Androida)
- Synchronizacja rezerwacji z backendem (np. Twój projekt Store Insights
  Pipeline z Azure — połączenie dwóch projektów portfolio)
- Powiadomienia push o statusie rezerwacji
- Testy UI (Compose Testing) dla ekranów
