package com.shaghayegh.myapplication.ui.screens

import com.shaghayegh.myapplication.R

sealed class Screen(val route: String, val label: String, val icon: Int) {
    object MapScreen : Screen("map", "Map", R.drawable.baseline_map_24)
    object RouteScreen : Screen("route", "Route", R.drawable.baseline_directions_bus_24)
    object AlertScreen : Screen("alert", "Alert", R.drawable.baseline_warning_24)
}