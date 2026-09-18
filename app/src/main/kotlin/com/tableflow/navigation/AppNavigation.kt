package com.tableflow.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tableflow.ui.screens.MenuScreen
import com.tableflow.ui.screens.ReservationScreen
import com.tableflow.viewmodel.OrderViewModel
import com.tableflow.viewmodel.ReservationViewModel

private sealed class Destination(val route: String, val label: String) {
    object Menu : Destination("menu", "Menu")
    object Reservation : Destination("reservation", "Rezerwacja")
}

@Composable
fun TableFlowApp() {
    val navController = rememberNavController()
    val destinations = listOf(Destination.Menu, Destination.Reservation)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination

                destinations.forEach { destination ->
                    val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                if (destination == Destination.Menu) Icons.Filled.RestaurantMenu
                                else Icons.Filled.DateRange,
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Menu.route,
            modifier = Modifier
        ) {
            composable(Destination.Menu.route) {
                MenuScreen(viewModel = viewModel<OrderViewModel>())
            }
            composable(Destination.Reservation.route) {
                ReservationScreen(viewModel = viewModel<ReservationViewModel>())
            }
        }
    }
}
