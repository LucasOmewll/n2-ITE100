package com.fatec.salafacil.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import com.fatec.salafacil.ui.routes.AppRoutes
import com.fatec.salafacil.ui.screens.home.data.NavigationItem

val navigationItems = listOf<NavigationItem>(
    NavigationItem(
        title = "Reuniões",
        icon = Icons.Default.Bookmark,
        route = AppRoutes.MEETINGS
    ),
    NavigationItem(
        title = "Reservar",
        icon = Icons.Outlined.AddCircle,
        route = AppRoutes.BOOKING
    ),
    NavigationItem(
        title = "Agenda",
        icon = Icons.Outlined.CalendarMonth,
        route = AppRoutes.SCHEDULE
    )
)