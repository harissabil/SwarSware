package com.harissabil.swarsware.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.harissabil.swarsware.R
import com.harissabil.swarsware.common.component.BottomNavBar
import com.harissabil.swarsware.common.component.BottomNavItem
import com.harissabil.swarsware.ui.screen.emergency.EmergencyScreen
import com.harissabil.swarsware.ui.screen.home.HomeScreen
import com.harissabil.swarsware.ui.screen.onboarding.OnboardingScreen
import com.harissabil.swarsware.ui.screen.sounds.SoundsScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: Route,
) {
    val navController = rememberNavController()

    val routes = listOf(
        Route.Onboarding,
        Route.Home,
        Route.Sounds,
        Route.Emergency,
    )

    val bottoomNavItems = listOf(
        BottomNavItem(
            selectedIcon = R.drawable.ic_home_filled,
            unselectedIcon = R.drawable.ic_home_outlined,
            text = "Home",
        ),
        BottomNavItem(
            selectedIcon = R.drawable.ic_sounds_filled,
            unselectedIcon = R.drawable.ic_sounds_outlined,
            text = "Sounds",
        ),
        BottomNavItem(
            selectedIcon = R.drawable.ic_emergency_filled,
            unselectedIcon = R.drawable.ic_emergency_outlined,
            text = "Emergency",
        ),
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination
    var currentRoute by remember { mutableStateOf<Route?>(null) }
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    routes.forEach { route ->
        if (currentDestination?.hierarchy?.any { it.hasRoute(route::class) } == true) {
            currentRoute = route
            selectedItem = when (currentRoute) {
                Route.Onboarding -> 0
                Route.Home -> 0
                Route.Sounds -> 1
                Route.Emergency -> 2
                else -> 0
            }
        }
    }
    val showBottomBar: (route: Route) -> Boolean = { route ->
        when (route) {
            Route.Onboarding -> false
            else -> true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar(currentRoute ?: startDestination),
                enter = slideInVertically(initialOffsetY = { it / 3 }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it / 3 }) + fadeOut(),
            ) {
                BottomNavBar(
                    items = bottoomNavItems,
                    selected = selectedItem,
                    onItemClick = {
                        when (it) {
                            0 -> navController.navigateToTab(Route.Home)
                            1 -> navController.navigateToTab(Route.Sounds)
                            2 -> navController.navigateToTab(Route.Emergency)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController,
            startDestination = startDestination,
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

            composable<Route.Sounds> {
                SoundsScreen()
            }

            composable<Route.Emergency> {
                EmergencyScreen()
            }
        }
    }
}

private fun NavController.navigateToTab(route: Route) {
    this.navigate(route) {
        popUpTo(this@navigateToTab.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}