package com.tableflow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tableflow.viewmodel.ReservationViewModel

@Composable
fun ReservationScreen(viewModel: ReservationViewModel) {
    val reservations by viewModel.reservations.collectAsState()
    val error by viewModel.lastError.collectAsState()

    var guestName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("2026-09-20") }
    var time by remember { mutableStateOf("19:00") }
    var partySize by remember { mutableStateOf("2") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Zarezerwuj stolik", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = guestName, onValueChange = { guestName = it },
            label = { Text("Imię i nazwisko") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        OutlinedTextField(
            value = date, onValueChange = { date = it },
            label = { Text("Data (RRRR-MM-DD)") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        OutlinedTextField(
            value = time, onValueChange = { time = it },
            label = { Text("Godzina (GG:MM)") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        OutlinedTextField(
            value = partySize, onValueChange = { partySize = it },
            label = { Text("Liczba osób") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        error?.let {
            Text(it, color = androidx.compose.ui.graphics.Color.Red, modifier = Modifier.padding(top = 8.dp))
        }

        Button(
            onClick = {
                val size = partySize.toIntOrNull() ?: 1
                viewModel.bookTable(date, time, size, guestName.ifBlank { "Gość" })
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
        ) {
            Text("Zarezerwuj")
        }

        Divider()
        Text("Twoje rezerwacje", style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn {
            items(reservations) { reservation ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${reservation.guestName} — ${reservation.slot.partySize} os.")
                        Text("${reservation.slot.date} o ${reservation.slot.time}")
                    }
                }
            }
        }
    }
}
