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
import com.harissabil.swarsware.common.constant.Status
import com.harissabil.swarsware.domain.model.History
import com.harissabil.swarsware.domain.repository.HistoryRepository
import com.harissabil.swarsware.ui.service.SoundDetectionService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    private val _status = mutableStateOf(Status.IDLE)
    val status: State<Status> = _status

    private val _timeElapsed = mutableLongStateOf(0L)
    val timeElapsed: State<Long> = _timeElapsed

    private val _histories = MutableStateFlow<List<History>>(emptyList())
    val histories: StateFlow<List<History>> = _histories.asStateFlow()

    private var receiver: BroadcastReceiver? = null

    init {
        getHistories()
    }

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

    private fun getHistories() {
        viewModelScope.launch {
            historyRepository.getAllHistories().collect {
                _histories.value = it
            }
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