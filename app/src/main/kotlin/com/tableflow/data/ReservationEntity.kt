package com.tableflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Encja Room — reprezentuje rezerwację zapisaną lokalnie na urządzeniu.
 * Pola odpowiadają modelowi domenowemu Reservation z modułu :shared,
 * dzięki czemu mapowanie między nimi jest trywialne (patrz Mappers.kt).
 */
@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey val id: String,
    val date: String,
    val time: String,
    val partySize: Int,
    val guestName: String
)
