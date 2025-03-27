package com.harissabil.swarsware.domain.model

import kotlinx.datetime.Instant

data class History(
    val id: Long,
    val sound: Sound,
    val timestamp: Instant,
)
