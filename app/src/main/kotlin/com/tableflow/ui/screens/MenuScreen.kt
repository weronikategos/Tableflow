package com.tableflow.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tableflow.viewmodel.OrderViewModel

@Composable
fun MenuScreen(viewModel: OrderViewModel) {
    val cart by viewModel.cart.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Menu", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)

        LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
            items(viewModel.menuItems) { item ->
                val quantity = cart[item.id] ?: 0

                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(item.name)
                            Text("${item.priceCents / 100.0} zł")
                        }
                        Row {
                            OutlinedButton(onClick = { viewModel.removeFromCart(item) }) { Text("-") }
                            Text(
                                text = "$quantity",
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            OutlinedButton(onClick = { viewModel.addToCart(item) }) { Text("+") }
                        }
                    }
                }
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        val breakdown = viewModel.currentBreakdown()
        Text("Suma częściowa: ${breakdown.subtotalCents / 100.0} zł")
        if (breakdown.discountCents > 0) {
            Text("Rabat (promocja 3+ dania): -${breakdown.discountCents / 100.0} zł")
        }
        Text("VAT (8%): ${breakdown.vatCents / 100.0} zł")
        Text(
            "Do zapłaty: ${breakdown.totalCents / 100.0} zł",
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = { viewModel.clearCart() },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Złóż zamówienie")
        }
    }
}
