package com.harissabil.swarsware.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    object Onboarding : Route()

    @Serializable
    object Home : Route()

    @Serializable
    object Sounds : Route()

    @Serializable
    object Emergency : Route()
}