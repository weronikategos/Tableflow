package com.tableflow.shared.logic

import com.tableflow.shared.model.Reservation
import com.tableflow.shared.model.ReservationSlot
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

class ReservationAvailabilityTest {

    private val checker = ReservationAvailability(totalSeats = 20)

    private val existing = listOf(
        Reservation("r1", ReservationSlot("2026-09-20", "19:00", 6), "Anna"),
        Reservation("r2", ReservationSlot("2026-09-20", "19:00", 8), "Piotr")
    )

    @Test
    fun rezerwacjaMiesciSieWLimicieMiejsc() {
        val newSlot = ReservationSlot("2026-09-20", "19:00", 4)
        assertTrue(checker.isAvailable(newSlot, existing))
    }

    @Test
    fun rezerwacjaPrzekraczaLimitMiejsc() {
        val newSlot = ReservationSlot("2026-09-20", "19:00", 10)
        assertFalse(checker.isAvailable(newSlot, existing))
    }

    @Test
    fun innaGodzinaMaPelnaDostepnosc() {
        val newSlot = ReservationSlot("2026-09-20", "20:00", 15)
        assertTrue(checker.isAvailable(newSlot, existing))
    }

    @Test
    fun remainingSeatsLiczyPoprawnieWolneMiejsca() {
        assertEquals(6, checker.remainingSeats("2026-09-20", "19:00", existing))
    }
}
