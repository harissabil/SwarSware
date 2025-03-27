package com.harissabil.swarsware.domain.repository

import com.harissabil.swarsware.domain.model.Emergency
import kotlinx.coroutines.flow.Flow

interface EmergencyRepository {
    suspend fun insertEmergency(emergency: Emergency): Long
    suspend fun updateEmergency(emergency: Emergency)
    suspend fun deleteEmergency(emergency: Emergency)
    fun getAllEmergencies(): Flow<List<Emergency>>
}