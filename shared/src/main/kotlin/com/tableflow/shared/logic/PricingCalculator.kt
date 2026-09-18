package com.tableflow.shared.logic

import com.tableflow.shared.model.MenuCategory
import com.tableflow.shared.model.Order

/**
 * Logika naliczania ceny zamówienia — reguła biznesowa:
 * jeśli w zamówieniu są co najmniej 3 sztuki dań z kategorii DANIA_GLOWNE,
 * przyznajemy 10% rabatu na całe zamówienie (promocja "stolik dla firmy").
 * Do ceny doliczany jest VAT (w Polsce dla gastronomii zwykle 8%).
 */
object PricingCalculator {

    private const val VAT_RATE = 0.08
    private const val COMBO_DISCOUNT = 0.10
    private const val COMBO_MIN_MAIN_COURSES = 3

    data class PriceBreakdown(
        val subtotalCents: Int,
        val discountCents: Int,
        val vatCents: Int,
        val totalCents: Int
    )

    fun calculate(order: Order): PriceBreakdown {
        val subtotal = order.lines.fold(0) { acc, line -> acc + line.menuItem.priceCents * line.quantity }

        val mainCourseCount = order.lines
            .filter { it.menuItem.category == MenuCategory.DANIA_GLOWNE }
            .fold(0) { acc, line -> acc + line.quantity }

        val isComboEligible = mainCourseCount >= COMBO_MIN_MAIN_COURSES
        val discount = if (isComboEligible) (subtotal * COMBO_DISCOUNT).toInt() else 0

        val afterDiscount = subtotal - discount
        val vat = (afterDiscount * VAT_RATE).toInt()
        val total = afterDiscount + vat

        return PriceBreakdown(
            subtotalCents = subtotal,
            discountCents = discount,
            vatCents = vat,
            totalCents = total
        )
    }
}
