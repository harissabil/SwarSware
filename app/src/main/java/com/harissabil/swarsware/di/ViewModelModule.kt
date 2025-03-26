package com.harissabil.swarsware.di

import com.harissabil.swarsware.MainViewModel
import com.harissabil.swarsware.ui.screen.onboarding.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::OnboardingViewModel)
}