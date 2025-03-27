package com.harissabil.swarsware.ui.screen.home.component

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.harissabil.swarsware.common.constant.Status
import com.harissabil.swarsware.ui.service.SoundDetectionService

class HomeViewModel : ViewModel() {
    private val _status = mutableStateOf(Status.IDLE)
    val status: State<Status> = _status

    private val _timeElapsed = mutableLongStateOf(0L)
    val timeElapsed: State<Long> = _timeElapsed

    private var receiver: BroadcastReceiver? = null

    fun registerReceiver(context: Context) {
        if (receiver != null) return

        val intentFilter = IntentFilter().apply {
            addAction(SoundDetectionService.TIMER_TICK)
            addAction(SoundDetectionService.Action.STATUS.toString())
        }

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    SoundDetectionService.TIMER_TICK -> {
                        val time = intent.getIntExtra(SoundDetectionService.TIME_ELAPSED, 0)
                        _timeElapsed.longValue = time.toLong()
                    }

                    SoundDetectionService.Action.STATUS.toString() -> {
                        val isRunning =
                            intent.getBooleanExtra(SoundDetectionService.IS_TIMER_RUNNING, false)
                        _status.value = if (isRunning) Status.PLAYING else Status.IDLE
                        if (!isRunning) {
                            _timeElapsed.longValue = 0L
                        } else {
                            val time = intent.getIntExtra(SoundDetectionService.TIME_ELAPSED, 0)
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

    override fun onCleared() {
        super.onCleared()
        receiver = null
    }
}