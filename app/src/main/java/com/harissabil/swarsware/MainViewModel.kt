package com.harissabil.swarsware

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.swarsware.domain.repository.PreferenceRepository
import com.harissabil.swarsware.ui.navigation.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferenceRepository: PreferenceRepository,
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startRoute by mutableStateOf<Route>(Route.Home)
        private set

    init {
        getAppEntry()
    }

    private fun getAppEntry() = viewModelScope.launch {
        startRoute = if (preferenceRepository.readAppEntry().first()) {
            Route.Home
        } else {
            Route.Onboarding
        }
        delay(300)
        splashCondition = false
    }
}