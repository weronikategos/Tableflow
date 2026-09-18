package com.tableflow.shared.logic

import com.tableflow.shared.model.Reservation
import com.tableflow.shared.model.ReservationSlot

/**
 * Sprawdza, czy dany termin rezerwacji jest jeszcze dostępny, biorąc pod
 * uwagę łączną pojemność restauracji (liczbę miejsc) i już istniejące
 * rezerwacje w tym samym terminie (data + godzina).
 */
class ReservationAvailability(private val totalSeats: Int) {

    fun isAvailable(newSlot: ReservationSlot, existingReservations: List<Reservation>): Boolean {
        val seatsAlreadyBooked = existingReservations
            .filter { it.slot.date == newSlot.date && it.slot.time == newSlot.time }
            .fold(0) { acc, r -> acc + r.slot.partySize }

        return seatsAlreadyBooked + newSlot.partySize <= totalSeats
    }

    fun remainingSeats(date: String, time: String, existingReservations: List<Reservation>): Int {
        val booked = existingReservations
            .filter { it.slot.date == date && it.slot.time == time }
            .fold(0) { acc, r -> acc + r.slot.partySize }
        return (totalSeats - booked).coerceAtLeast(0)
    }
}
