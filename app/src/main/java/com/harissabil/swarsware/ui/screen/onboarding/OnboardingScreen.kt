package com.harissabil.swarsware.ui.screen.onboarding

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harissabil.swarsware.R
import com.harissabil.swarsware.ui.screen.onboarding.component.OnboardingCard
import com.harissabil.swarsware.ui.screen.onboarding.component.OnboardingImage
import com.harissabil.swarsware.ui.theme.spacing
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onGetStartedClicked: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val images = listOf(
        R.drawable.img_onboarding_1,
        R.drawable.img_onboarding_2,
        R.drawable.img_onboarding_3
    )

    val arePermissionsGranted = viewModel.arePermissionsGranted.collectAsStateWithLifecycle()

    // Set status bar icon color to dark, because the background is always light
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    // Auto scroll the pager every 2.5 seconds
    LaunchedEffect(pagerState) {
        while (true) {
            delay(2500)
            val nextPage = (pagerState.currentPage + 1) % images.size
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    val callPhonePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onPermissionsGranted()
        }
    }

    // Notification permission request (for Android 13+)
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            callPhonePermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }

    // Audio recording permission request
    val audioPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Request notification permission if audio permission is granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                callPhonePermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            HorizontalPager(state = pagerState) { index ->
                OnboardingImage(image = images[index])
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnboardingCard(
                    title = "Welcome to SwarSware",
                    description = "SwarSware helps people with hearing impairments by detecting environmental sounds and sending alerts.\n\n" +
                            "Please allow these permissions to get started!"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = MaterialTheme.spacing.large)
                        .animateContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    FilledTonalButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.large),
                        onClick = {
                            audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    ) {
                        Text(text = "Allow Permissions")
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = MaterialTheme.spacing.large)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = arePermissionsGranted.value,
                        onClick = {
                            viewModel.onGetStartedClicked()
                            onGetStartedClicked()
                        }
                    ) {
                        Text(text = "Get Started")
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(MaterialTheme.spacing.large)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}