package com.harissabil.swarsware.ui.screen.emergency

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.swarsware.common.util.deleteImage
import com.harissabil.swarsware.common.util.saveImage
import com.harissabil.swarsware.domain.model.Emergency
import com.harissabil.swarsware.domain.repository.EmergencyRepository
import com.harissabil.swarsware.domain.repository.SoundRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmergencyViewModel(
    private val emergencyRepository: EmergencyRepository,
    private val soundRepository: SoundRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(EmergencyState())
    val state: StateFlow<EmergencyState> = _state.asStateFlow()

    init {
        getAllEmergencies()
        getAllSounds()
    }

    private fun getAllEmergencies() = viewModelScope.launch {
        emergencyRepository.getAllEmergencies().collect { emergencies ->
            _state.update { it.copy(emergencies = emergencies) }
        }
    }

    fun getAllSounds() = viewModelScope.launch {
        soundRepository.getAllSounds().collect { sounds ->
            _state.update {
                it.copy(sounds = sounds)
            }
        }
    }

    fun setName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun setPhoneNumber(phoneNumber: String) {
        _state.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun setSound(soundString: String) = viewModelScope.launch {
        val sound = soundRepository.getSoundByName(soundString)
        _state.update { it.copy(sound = sound) }
    }

    fun setPhotoBitmap(photoBitmap: Bitmap?) {
        _state.update { it.copy(photoBitmap = photoBitmap) }
    }

    fun setEmergency(emergency: Emergency) {
        _state.update {
            it.copy(
                id = emergency.id,
                name = emergency.name,
                phoneNumber = emergency.phoneNumber,
                sound = emergency.sound,
                photoPath = emergency.photo
            )
        }
    }

    fun resetForm() {
        _state.update {
            it.copy(
                id = 0L,
                name = "",
                phoneNumber = "",
                sound = null,
                photoPath = null,
                photoBitmap = null
            )
        }
    }

    fun saveEmergency(emergency: Emergency, context: Context) = viewModelScope.launch {
        val photoPath = _state.value.photoBitmap?.let {
            saveImage(it, emergency.name, context)
        } ?: emergency.photo

        emergencyRepository.insertEmergency(
            emergency.copy(photo = photoPath)
        )
        resetForm()
    }

    fun deleteEmergency(emergency: Emergency) = viewModelScope.launch {
        deleteImage(emergency.photo)
        emergencyRepository.deleteEmergency(emergency)
        resetForm()
    }
}

