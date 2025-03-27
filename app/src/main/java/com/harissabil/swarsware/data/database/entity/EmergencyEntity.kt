package com.harissabil.swarsware.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
data class EmergencyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val soundId: Long
)