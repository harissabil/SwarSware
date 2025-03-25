package com.harissabil.swarsware.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>

    suspend fun deleteAppEntry()
}