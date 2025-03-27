package com.harissabil.swarsware.data.database.converter

import androidx.room.TypeConverter
import com.harissabil.swarsware.domain.model.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority?): String? {
        return priority?.name
    }

    @TypeConverter
    fun toPriority(value: String?): Priority? {
        return value?.let { Priority.valueOf(it) }
    }
}