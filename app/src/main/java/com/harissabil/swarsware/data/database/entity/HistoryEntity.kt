package com.harissabil.swarsware.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SoundEntity::class,
            parentColumns = ["id"],
            childColumns = ["soundId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val soundId: Long,
    val timestamp: Instant,
)