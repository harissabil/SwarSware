package com.harissabil.swarsware.ui.screen.home

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.harissabil.swarsware.ui.screen.home.component.HomeTopBar
import com.harissabil.swarsware.ui.screen.home.HomeViewModel
import com.harissabil.swarsware.ui.screen.home.component.Timer
import com.harissabil.swarsware.ui.service.SoundDetectionService
import com.harissabil.swarsware.ui.service.SoundDetectionService.Action
import com.harissabil.swarsware.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        canScroll = { false }
    )

    val status by viewModel.status
    val timeElapsed by viewModel.timeElapsed

    // Register/unregister broadcast receiver
    DisposableEffect(context) {
        viewModel.registerReceiver(context)
        onDispose {
            viewModel.unregisterReceiver(context)
        }
    }

    // Set status bar icon color to adjust with the theme
    // This is required because in the onboarding screen we set the status bar icon color to dark
    val darkTheme = isSystemInDarkTheme()
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopBar(
                title = "SwarSware",
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            item {
                Timer(
                    modifier = Modifier.padding(
                        vertical = MaterialTheme.spacing.extraLarge,
                        horizontal = MaterialTheme.spacing.large + MaterialTheme.spacing.medium
                    ),
                    timeElapsed = timeElapsed,
                    status = status,
                    onStart = {
                        Intent(context, SoundDetectionService::class.java).also { intent ->
                            intent.action = Action.START.toString()
                            context.startService(intent)
                        }
                    },
                    onStop = {
                        Intent(context, SoundDetectionService::class.java).also { intent ->
                            intent.action = Action.STOP.toString()
                            context.startService(intent)
                        }
                    },
                )
            }
//            item {
//                HorizontalDivider()
//            }
        }
    }
}