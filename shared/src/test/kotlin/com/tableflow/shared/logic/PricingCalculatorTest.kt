package com.tableflow.shared.logic

import com.tableflow.shared.model.MenuCategory
import com.tableflow.shared.model.MenuItem
import com.tableflow.shared.model.Order
import com.tableflow.shared.model.OrderLine
import org.junit.Test
import org.junit.Assert.assertEquals

class PricingCalculatorTest {

    private val pierogi = MenuItem("1", "Pierogi ruskie", MenuCategory.DANIA_GLOWNE, 2500)
    private val schabowy = MenuItem("2", "Schabowy", MenuCategory.DANIA_GLOWNE, 3200)
    private val kompot = MenuItem("3", "Kompot", MenuCategory.NAPOJE, 800)

    @Test
    fun zamowienieBezRabatuNaliczanePoprawnie() {
        val order = Order(listOf(OrderLine(kompot, 2)))
        val result = PricingCalculator.calculate(order)

        assertEquals(1600, result.subtotalCents)
        assertEquals(0, result.discountCents)
        assertEquals(128, result.vatCents) // 8% z 1600
        assertEquals(1728, result.totalCents)
    }

    @Test
    fun trzyDaniaGlowneUruchamiajaRabatCombo() {
        val order = Order(
            listOf(
                OrderLine(pierogi, 2),
                OrderLine(schabowy, 1)
            )
        )
        val result = PricingCalculator.calculate(order)

        val expectedSubtotal = 2500 * 2 + 3200
        assertEquals(expectedSubtotal, result.subtotalCents)
        assertEquals((expectedSubtotal * 0.10).toInt(), result.discountCents)
    }

    @Test
    fun dwaDaniaGlowneNieUruchamiajaRabatu() {
        val order = Order(listOf(OrderLine(pierogi, 2)))
        val result = PricingCalculator.calculate(order)

        assertEquals(0, result.discountCents)
    }
}
