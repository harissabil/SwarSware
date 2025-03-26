package com.harissabil.swarsware.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.harissabil.swarsware.ui.screen.home.HomeScreen
import com.harissabil.swarsware.ui.screen.onboarding.OnboardingScreen

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
        composable<Route.Onboarding> {
            OnboardingScreen(
                onGetStartedClicked = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Onboarding) { inclusive = true }
                    }
                }
            )
        }

        composable<Route.Home> {
            HomeScreen()
        }

        composable<Route.Sounds> { }

        composable<Route.Emergency> { }
    }
}