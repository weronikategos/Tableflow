package com.tableflow.shared.model

/**
 * Modele domenowe napisane w czystym Kotlinie, bez żadnej zależności od Androida.
 * Dzięki temu ten moduł można w przyszłości przenieść bez zmian do commonMain
 * w projekcie Kotlin Multiplatform (współdzielona logika Android + iOS + web).
 */

enum class MenuCategory {
    PRZYSTAWKI, DANIA_GLOWNE, DESERY, NAPOJE
}

data class MenuItem(
    val id: String,
    val name: String,
    val category: MenuCategory,
    val priceCents: Int // ceny w groszach, żeby uniknąć błędów zaokrągleń przy Double
)

data class OrderLine(
    val menuItem: MenuItem,
    val quantity: Int
)

data class Order(
    val lines: List<OrderLine>
)

data class ReservationSlot(
    val date: String,     // format ISO: "2026-09-20"
    val time: String,     // format "HH:mm", np. "19:00"
    val partySize: Int
)

data class Reservation(
    val id: String,
    val slot: ReservationSlot,
    val guestName: String
)
