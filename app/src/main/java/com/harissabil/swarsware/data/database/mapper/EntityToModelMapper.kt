package com.harissabil.swarsware.data.database.mapper

import com.harissabil.swarsware.data.database.entity.SoundEntity
import com.harissabil.swarsware.data.database.relation.EmergencyWithSound
import com.harissabil.swarsware.data.database.relation.HistoryWithSound
import com.harissabil.swarsware.domain.model.Emergency
import com.harissabil.swarsware.domain.model.History
import com.harissabil.swarsware.domain.model.Sound

internal fun SoundEntity.toModel(): Sound {
    return Sound(
        id = id,
        name = name,
        description = description,
        priority = priority
    )
}

internal fun HistoryWithSound.toHistory(): History {
    return History(
        id = history.id,
        sound = sound.toModel(),
        timestamp = history.timestamp
    )
}

internal fun EmergencyWithSound.toEmergency(): Emergency {
    return Emergency(
        id = emergency.id,
        name = emergency.name,
        phoneNumber = emergency.phoneNumber,
        sound = sound.toModel(),
    )
}