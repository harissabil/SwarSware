package com.harissabil.swarsware.ui.screen.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.harissabil.swarsware.common.constant.Status
import com.harissabil.swarsware.domain.model.History
import com.harissabil.swarsware.domain.repository.HistoryRepository
import com.harissabil.swarsware.ui.service.SoundDetectionService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    private val _status = mutableStateOf(Status.IDLE)
    val status: State<Status> = _status

    private val _timeElapsed = mutableLongStateOf(0L)
    val timeElapsed: State<Long> = _timeElapsed

    val pagedHistories: Flow<PagingData<History>> =
        historyRepository.getPaginatedHistories()
            .cachedIn(viewModelScope)

    private var receiver: BroadcastReceiver? = null

    fun registerReceiver(context: Context) {
        if (receiver != null) return

        val intentFilter = IntentFilter().apply {
            addAction(SoundDetectionService.Companion.TIMER_TICK)
            addAction(SoundDetectionService.Action.STATUS.toString())
        }

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    SoundDetectionService.Companion.TIMER_TICK -> {
                        val time =
                            intent.getIntExtra(SoundDetectionService.Companion.TIME_ELAPSED, 0)
                        _timeElapsed.longValue = time.toLong()
                    }

                    SoundDetectionService.Action.STATUS.toString() -> {
                        val isRunning =
                            intent.getBooleanExtra(
                                SoundDetectionService.Companion.IS_TIMER_RUNNING,
                                false
                            )
                        _status.value = if (isRunning) Status.PLAYING else Status.IDLE
                        if (!isRunning) {
                            _timeElapsed.longValue = 0L
                        } else {
                            val time =
                                intent.getIntExtra(SoundDetectionService.Companion.TIME_ELAPSED, 0)
                            _timeElapsed.longValue = time.toLong()
                        }
                    }
                }
            }
        }

        ContextCompat.registerReceiver(
            context,
            receiver,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )

        // Request current status from service
        Intent(context, SoundDetectionService::class.java).also { intent ->
            intent.action = SoundDetectionService.Action.STATUS.toString()
            context.startService(intent)
        }
    }

    fun unregisterReceiver(context: Context) {
        receiver?.let {
            context.unregisterReceiver(it)
            receiver = null
        }
    }

    fun deleteHistory(history: History) {
        viewModelScope.launch {
            historyRepository.deleteHistory(history)
        }
    }

    override fun onCleared() {
        super.onCleared()
        receiver = null
    }
}