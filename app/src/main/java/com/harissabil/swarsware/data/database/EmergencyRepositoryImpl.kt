package com.harissabil.swarsware.data.database

import com.harissabil.swarsware.data.database.dao.EmergencyDao
import com.harissabil.swarsware.data.database.mapper.toEmergency
import com.harissabil.swarsware.data.database.mapper.toEntity
import com.harissabil.swarsware.domain.model.Emergency
import com.harissabil.swarsware.domain.repository.EmergencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EmergencyRepositoryImpl(
    private val emergencyDao: EmergencyDao
) : EmergencyRepository {
    override suspend fun insertEmergency(emergency: Emergency): Long {
        return emergencyDao.insertEmergency(emergency.toEntity())
    }

    override suspend fun updateEmergency(emergency: Emergency) {
        emergencyDao.updateEmergency(emergency.toEntity())
    }

    override suspend fun deleteEmergency(emergency: Emergency) {
        emergencyDao.deleteEmergency(emergency.toEntity())
    }

    override fun getAllEmergencies(): Flow<List<Emergency>> {
        return emergencyDao.getAllEmergenciesWithSound().map { relations ->
            relations.map { it.toEmergency() }
        }
    }
}