package com.fatec.salafacil.ui.screens.rooms.tabs

import androidx.compose.ui.graphics.vector.ImageVector

data class ProcessTab(
    val title: String,
    val body: String,
    val value: String? = "",
    val icon: ImageVector,
    var hasProjector: Boolean? = false,
    var hasWhiteboard: Boolean? = false,
    var hasAirConditioning: Boolean? = false,
    var hasWifi: Boolean? = false,
    var hasVideoConference: Boolean? = false,
)
