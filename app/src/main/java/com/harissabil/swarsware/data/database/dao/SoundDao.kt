package com.harissabil.swarsware.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harissabil.swarsware.data.database.entity.SoundEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSound(sound: SoundEntity): Long

    @Query("SELECT * FROM SoundEntity")
    fun getAllSounds(): Flow<List<SoundEntity>>
}