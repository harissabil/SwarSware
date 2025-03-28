package com.harissabil.swarsware.data.database.converter

import androidx.room.TypeConverter
import com.harissabil.swarsware.domain.model.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority?): String? {
        return priority?.displayName
    }

    @TypeConverter
    fun toPriority(value: String?): Priority? {
        return when (value) {
            Priority.HIGH.displayName -> Priority.HIGH
            Priority.MEDIUM.displayName -> Priority.MEDIUM
            Priority.LOW.displayName -> Priority.LOW
            else -> null
        }
    }
}