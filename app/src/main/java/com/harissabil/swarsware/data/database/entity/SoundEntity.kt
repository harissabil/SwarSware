package com.harissabil.swarsware.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.harissabil.swarsware.domain.model.Priority

@Entity
data class SoundEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val priority: Priority?
)