package com.tableflow.viewmodel

import androidx.lifecycle.ViewModel
import com.tableflow.data.MenuCatalog
import com.tableflow.shared.logic.PricingCalculator
import com.tableflow.shared.model.MenuItem
import com.tableflow.shared.model.Order
import com.tableflow.shared.model.OrderLine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel ekranu menu/koszyka. Cała logika naliczania ceny (rabaty, VAT)
 * jest delegowana do PricingCalculator z modułu :shared — ViewModel
 * odpowiada wyłącznie za stan UI, nie za reguły biznesowe.
 */
class OrderViewModel : ViewModel() {

    val menuItems: List<MenuItem> = MenuCatalog.items

    private val _cart = MutableStateFlow<Map<String, Int>>(emptyMap()) // menuItemId -> ilość
    val cart: StateFlow<Map<String, Int>> = _cart.asStateFlow()

    /** Przelicza podsumowanie ceny na podstawie aktualnego stanu koszyka.
     *  Wywoływane z UI po każdej zmianie `cart` (np. przez `derivedStateOf`). */
    fun currentBreakdown(): PricingCalculator.PriceBreakdown = calculateBreakdown(_cart.value)

    private fun calculateBreakdown(cartState: Map<String, Int>): PricingCalculator.PriceBreakdown {
        val lines = cartState.mapNotNull { (id, qty) ->
            menuItems.find { it.id == id }?.let { item -> OrderLine(item, qty) }
        }
        return PricingCalculator.calculate(Order(lines))
    }

    fun addToCart(item: MenuItem) {
        _cart.update { current ->
            current.toMutableMap().apply {
                this[item.id] = (this[item.id] ?: 0) + 1
            }
        }
    }

    fun removeFromCart(item: MenuItem) {
        _cart.update { current ->
            current.toMutableMap().apply {
                val qty = (this[item.id] ?: 0) - 1
                if (qty <= 0) remove(item.id) else this[item.id] = qty
            }
        }
    }

    fun clearCart() {
        _cart.value = emptyMap()
    }
}
