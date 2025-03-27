package com.harissabil.swarsware.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.harissabil.swarsware.data.database.entity.HistoryEntity
import com.harissabil.swarsware.data.database.entity.SoundEntity

data class HistoryWithSound(
    @Embedded val history: HistoryEntity,
    @Relation(
        parentColumn = "soundId",
        entityColumn = "id"
    )
    val sound: SoundEntity,
)