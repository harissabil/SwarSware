package com.harissabil.swarsware.data.database.mapper

import com.harissabil.swarsware.data.database.entity.EmergencyEntity
import com.harissabil.swarsware.data.database.entity.HistoryEntity
import com.harissabil.swarsware.data.database.entity.SoundEntity
import com.harissabil.swarsware.domain.model.Emergency
import com.harissabil.swarsware.domain.model.History
import com.harissabil.swarsware.domain.model.Sound

internal fun Sound.toEntity(): SoundEntity {
    return SoundEntity(
        id = id,
        name = name,
        description = description,
        priority = priority
    )
}

internal fun History.toEntity(): HistoryEntity {
    return HistoryEntity(
        id = id,
        soundId = sound.id,
        timestamp = timestamp
    )
}

internal fun Emergency.toEntity(): EmergencyEntity {
    return EmergencyEntity(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        soundId = sound.id
    )
}