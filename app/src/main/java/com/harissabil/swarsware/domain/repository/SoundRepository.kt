package com.harissabil.swarsware.domain.repository

import com.harissabil.swarsware.domain.model.Sound
import kotlinx.coroutines.flow.Flow

interface SoundRepository {
    suspend fun insertSound(sound: Sound): Long
    fun getAllSounds(): Flow<List<Sound>>
    suspend fun getSoundByName(name: String): Sound?
}