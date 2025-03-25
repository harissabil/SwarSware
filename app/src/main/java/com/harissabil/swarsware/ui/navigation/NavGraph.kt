package com.harissabil.swarsware.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: Route,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Route.Onboarding> { }
        composable<Route.Home> { }
        composable<Route.Sounds> { }
        composable<Route.Emergency> { }
    }
}