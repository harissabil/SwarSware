package com.harissabil.swarsware.ui.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.swarsware.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val preferenceRepository: PreferenceRepository,
) : ViewModel() {

    private val _arePermissionsGranted = MutableStateFlow(false)
    val arePermissionsGranted: StateFlow<Boolean> = _arePermissionsGranted.asStateFlow()

    fun onPermissionsGranted() {
        _arePermissionsGranted.update { true }
    }

    fun onGetStartedClicked() = viewModelScope.launch {
        preferenceRepository.saveAppEntry()
    }
}