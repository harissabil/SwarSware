package com.harissabil.swarsware.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harissabil.swarsware.data.database.converter.DateTimeConverter
import com.harissabil.swarsware.data.database.converter.PriorityConverter
import com.harissabil.swarsware.data.database.dao.EmergencyDao
import com.harissabil.swarsware.data.database.dao.HistoryDao
import com.harissabil.swarsware.data.database.dao.SoundDao
import com.harissabil.swarsware.data.database.entity.EmergencyEntity
import com.harissabil.swarsware.data.database.entity.HistoryEntity
import com.harissabil.swarsware.data.database.entity.SoundEntity

@Database(
    entities = [
        SoundEntity::class,
        HistoryEntity::class,
        EmergencyEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class, PriorityConverter::class)
abstract class SwarSwareDatabase : RoomDatabase() {
    abstract fun soundDao(): SoundDao
    abstract fun historyDao(): HistoryDao
    abstract fun emergencyDao(): EmergencyDao
}