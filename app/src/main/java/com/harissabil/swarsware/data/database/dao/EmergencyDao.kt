package com.harissabil.swarsware.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.harissabil.swarsware.data.database.entity.EmergencyEntity
import com.harissabil.swarsware.data.database.relation.EmergencyWithSound
import kotlinx.coroutines.flow.Flow

@Dao
interface EmergencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergency(emergency: EmergencyEntity): Long

    @Update
    suspend fun updateEmergency(emergency: EmergencyEntity)

    @Delete
    suspend fun deleteEmergency(emergency: EmergencyEntity)

    @Transaction
    @Query("SELECT * FROM EmergencyEntity")
    fun getAllEmergenciesWithSound(): Flow<List<EmergencyWithSound>>
}