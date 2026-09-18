package com.tableflow.data

import com.tableflow.shared.model.Reservation
import com.tableflow.shared.model.ReservationSlot

/**
 * Mapowanie między encją bazodanową (Room, warstwa Android) a modelem
 * domenowym (moduł :shared, czysty Kotlin) — rozdzielenie tych dwóch
 * światów to świadoma decyzja architektoniczna, nie przypadek.
 */

fun ReservationEntity.toDomain(): Reservation =
    Reservation(
        id = id,
        slot = ReservationSlot(date = date, time = time, partySize = partySize),
        guestName = guestName
    )

fun Reservation.toEntity(): ReservationEntity =
    ReservationEntity(
        id = id,
        date = slot.date,
        time = slot.time,
        partySize = slot.partySize,
        guestName = guestName
    )
