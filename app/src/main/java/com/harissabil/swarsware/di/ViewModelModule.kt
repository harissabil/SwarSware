package com.harissabil.swarsware.di

import com.harissabil.swarsware.MainViewModel
import com.harissabil.swarsware.ui.screen.emergency.EmergencyViewModel
import com.harissabil.swarsware.ui.screen.home.HomeViewModel
import com.harissabil.swarsware.ui.screen.onboarding.OnboardingViewModel
import com.harissabil.swarsware.ui.screen.sounds.SoundsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SoundsViewModel)
    viewModelOf(::EmergencyViewModel)
}