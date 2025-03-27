package com.harissabil.swarsware.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.harissabil.swarsware.data.database.entity.EmergencyEntity
import com.harissabil.swarsware.data.database.entity.SoundEntity

data class EmergencyWithSound(
    @Embedded val emergency: EmergencyEntity,
    @Relation(
        parentColumn = "soundId",
        entityColumn = "id"
    )
    val sound: SoundEntity
)