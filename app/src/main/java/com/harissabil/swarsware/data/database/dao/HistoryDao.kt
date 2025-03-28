package com.harissabil.swarsware.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.harissabil.swarsware.data.database.entity.HistoryEntity
import com.harissabil.swarsware.data.database.relation.HistoryWithSound
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity): Long

    @Delete
    suspend fun deleteHistory(history: HistoryEntity)

    @Transaction
    @Query("SELECT * FROM HistoryEntity ORDER BY id DESC")
    fun getAllHistoriesWithSound(): Flow<List<HistoryWithSound>>
}