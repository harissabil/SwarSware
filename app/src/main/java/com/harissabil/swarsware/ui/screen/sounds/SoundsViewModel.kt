package com.harissabil.swarsware.ui.screen.sounds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.swarsware.domain.model.Priority
import com.harissabil.swarsware.domain.model.Sound
import com.harissabil.swarsware.domain.repository.SoundRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SoundsViewModel(
    private val soundRepository: SoundRepository,
) : ViewModel() {

    private val _sounds = MutableStateFlow<List<Sound>>(emptyList())

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _priorityFilter = MutableStateFlow<Priority?>(Priority.HIGH)
    val priorityFilter: StateFlow<Priority?> = _priorityFilter.asStateFlow()

    private val _soundDetail = MutableStateFlow<Sound?>(null)
    val soundDetail: StateFlow<Sound?> = _soundDetail.asStateFlow()

    private val _soundDetailPriority = MutableStateFlow<Priority?>(null)
    val soundDetailPriority: StateFlow<Priority?> = _soundDetailPriority.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredSounds: Flow<List<Sound>> = combine(
        _sounds,
        _priorityFilter,
        _query
    ) { sounds: List<Sound>, priority: Priority?, query: String ->
        sounds
            .filter { sound ->
                // If priority filter is null, show only sounds with null priority
                // Otherwise, only include sounds with matching priority
                (priority == null && sound.priority == null) || sound.priority == priority
            }
            .filter { sound ->
                sound.name.contains(query, ignoreCase = true)
            }
    }

    init {
        getSounds()
    }

    fun getSounds() = viewModelScope.launch {
        soundRepository.getAllSounds().collect {
            _sounds.value = it
        }
    }

    fun setQuery(query: String) {
        _query.value = query
    }

    fun setPriorityFilter(priority: Priority?) {
        _priorityFilter.value = priority
    }

    fun setSoundDetail(sound: Sound?) {
        _soundDetailPriority.value = sound?.priority
        _soundDetail.value = sound
    }

    fun setSoundDetailPriority(priority: Priority?) = viewModelScope.launch {
        if (soundDetail.value == null || priority == null) return@launch
        soundRepository.updateSoundPriority(
            name = soundDetail.value?.name!!,
            priority = priority
        )
        _soundDetailPriority.value = priority
    }
}