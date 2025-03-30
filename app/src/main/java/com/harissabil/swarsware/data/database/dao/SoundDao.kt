package com.harissabil.swarsware.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harissabil.swarsware.data.database.entity.SoundEntity
import com.harissabil.swarsware.domain.model.Priority
import kotlinx.coroutines.flow.Flow

@Dao
interface SoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSound(sound: SoundEntity): Long

    @Query("SELECT * FROM SoundEntity")
    fun getAllSounds(): Flow<List<SoundEntity>>

    @Query("SELECT * FROM SoundEntity WHERE name = :name")
    suspend fun getSoundByName(name: String): SoundEntity?

    @Query("UPDATE SoundEntity SET priority = :priority WHERE name = :name")
    suspend fun updateSoundPriority(name: String, priority: Priority)
}