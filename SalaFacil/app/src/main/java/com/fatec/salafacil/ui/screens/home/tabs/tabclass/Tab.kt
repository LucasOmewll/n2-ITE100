package com.fatec.salafacil.ui.screens.home.tabs.tabclass

sealed class Tab(val route: String) {
    object Meetings: Tab("meetings_tab")
    object Booking: Tab("booking_tab")
    object Schedule: Tab("schedule_tab")
}