package com.harissabil.swarsware.domain.repository

import androidx.paging.PagingData
import com.harissabil.swarsware.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun insertHistory(history: History): Long
    suspend fun deleteHistory(history: History)
    fun getPaginatedHistories(pageSize: Int = 20): Flow<PagingData<History>>
}