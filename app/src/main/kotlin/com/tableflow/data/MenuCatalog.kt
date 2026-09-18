package com.tableflow.data

import com.tableflow.shared.model.MenuCategory
import com.tableflow.shared.model.MenuItem

/**
 * W realnym produkcie menu przychodziłoby z API/bazy danych restauracji.
 * Tutaj — do celów demo — jest to statyczna lista, żeby skupić się
 * na logice zamówień i rezerwacji, którą faktycznie testujemy.
 */
object MenuCatalog {
    val items = listOf(
        MenuItem("1", "Pierogi ruskie", MenuCategory.PRZYSTAWKI, 2200),
        MenuItem("2", "Żurek na zakwasie", MenuCategory.PRZYSTAWKI, 1800),
        MenuItem("3", "Schabowy z ziemniakami", MenuCategory.DANIA_GLOWNE, 3200),
        MenuItem("4", "Pierogi ruskie (danie główne)", MenuCategory.DANIA_GLOWNE, 2800),
        MenuItem("5", "Gulasz wołowy", MenuCategory.DANIA_GLOWNE, 3600),
        MenuItem("6", "Sernik", MenuCategory.DESERY, 1400),
        MenuItem("7", "Szarlotka", MenuCategory.DESERY, 1300),
        MenuItem("8", "Kompot", MenuCategory.NAPOJE, 800),
        MenuItem("9", "Woda gazowana", MenuCategory.NAPOJE, 600)
    )
}
