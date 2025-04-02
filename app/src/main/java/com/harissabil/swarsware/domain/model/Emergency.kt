package com.harissabil.swarsware.domain.model

data class Emergency(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val sound: Sound,
    val photo: String
)
