package com.harissabil.swarsware.data.database

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.harissabil.swarsware.data.database.dao.HistoryDao
import com.harissabil.swarsware.data.database.mapper.toEntity
import com.harissabil.swarsware.data.database.mapper.toHistory
import com.harissabil.swarsware.domain.model.History
import com.harissabil.swarsware.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryRepositoryImpl(
    private val historyDao: HistoryDao,
) : HistoryRepository {
    override suspend fun insertHistory(history: History): Long {
        return historyDao.insertHistory(history.toEntity())
    }

    override suspend fun deleteHistory(history: History) {
        historyDao.deleteHistory(history.toEntity())
    }

    override fun getPaginatedHistories(pageSize: Int): Flow<PagingData<History>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false,
                maxSize = pageSize * 3
            ),
            pagingSourceFactory = { historyDao.getPaginatedHistoriesWithSound() }
        ).flow.map { pagingData ->
            pagingData.map { it.toHistory() }
        }
    }
}