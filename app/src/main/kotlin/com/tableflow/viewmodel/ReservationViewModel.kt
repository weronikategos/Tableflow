package com.tableflow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tableflow.data.AppDatabase
import com.tableflow.data.toDomain
import com.tableflow.data.toEntity
import com.tableflow.shared.logic.ReservationAvailability
import com.tableflow.shared.model.Reservation
import com.tableflow.shared.model.ReservationSlot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID

private const val TOTAL_SEATS = 20

/**
 * ViewModel ekranu rezerwacji. Reguła "czy jest jeszcze miejsce" jest
 * w całości delegowana do ReservationAvailability z modułu :shared —
 * to samo pytanie zadałby backend, gdyby ta appka miała serwer.
 */
class ReservationViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).reservationDao()
    private val availabilityChecker = ReservationAvailability(totalSeats = TOTAL_SEATS)

    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations: StateFlow<List<Reservation>> = _reservations.asStateFlow()

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError: StateFlow<String?> = _lastError.asStateFlow()

    init {
        dao.observeAll()
            .onEach { entities -> _reservations.value = entities.map { it.toDomain() } }
            .launchIn(viewModelScope)
    }

    fun bookTable(date: String, time: String, partySize: Int, guestName: String) {
        viewModelScope.launch {
            val slot = ReservationSlot(date, time, partySize)

            if (!availabilityChecker.isAvailable(slot, _reservations.value)) {
                val remaining = availabilityChecker.remainingSeats(date, time, _reservations.value)
                _lastError.value = "Brak wolnych miejsc na ten termin (dostępne: $remaining)."
                return@launch
            }

            val reservation = Reservation(id = UUID.randomUUID().toString(), slot = slot, guestName = guestName)
            dao.insert(reservation.toEntity())
            _lastError.value = null
        }
    }

    fun clearError() {
        _lastError.value = null
    }
}
