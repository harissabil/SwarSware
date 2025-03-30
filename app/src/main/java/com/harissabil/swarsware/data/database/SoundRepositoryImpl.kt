package com.harissabil.swarsware.data.database

import com.harissabil.swarsware.data.database.dao.SoundDao
import com.harissabil.swarsware.data.database.mapper.toEntity
import com.harissabil.swarsware.data.database.mapper.toModel
import com.harissabil.swarsware.domain.model.Priority
import com.harissabil.swarsware.domain.model.Sound
import com.harissabil.swarsware.domain.repository.SoundRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SoundRepositoryImpl(
    private val soundDao: SoundDao,
) : SoundRepository {
    override suspend fun insertSound(sound: Sound): Long {
        return soundDao.insertSound(sound.toEntity())
    }

    override fun getAllSounds(): Flow<List<Sound>> {
        return soundDao.getAllSounds().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override suspend fun getSoundByName(name: String): Sound? {
        return soundDao.getSoundByName(name)?.toModel()
    }

    override suspend fun updateSoundPriority(
        name: String,
        priority: Priority,
    ) {
        soundDao.updateSoundPriority(name, priority)
    }
}