package com.harissabil.swarsware.domain.model

data class Sound(
    val id: Long,
    val name: String,
    val description: String,
    val priority: Priority?
)
