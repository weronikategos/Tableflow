plugins {
    id("org.jetbrains.kotlin.jvm")
}

// Moduł celowo NIE zależy od Androida — to czysty Kotlin.
// Dzięki temu logikę biznesową (ceny, dostępność rezerwacji) można
// przetestować bez emulatora i w przyszłości przenieść do modułu
// Kotlin Multiplatform (commonMain) bez zmian w kodzie.

dependencies {
    testImplementation("junit:junit:4.13.2")
}

tasks.test {
    useJUnit()
}
