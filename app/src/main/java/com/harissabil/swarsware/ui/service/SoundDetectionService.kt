package com.harissabil.swarsware.ui.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Notification.FOREGROUND_SERVICE_IMMEDIATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.AudioRecord
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.harissabil.swarsware.MainActivity
import com.harissabil.swarsware.R
import com.harissabil.swarsware.domain.model.History
import com.harissabil.swarsware.domain.repository.HistoryRepository
import com.harissabil.swarsware.domain.repository.SoundRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.tensorflow.lite.support.audio.TensorAudio
import org.tensorflow.lite.task.audio.classifier.AudioClassifier
import timber.log.Timber
import java.util.Timer
import java.util.TimerTask

class SoundDetectionService : Service(), KoinComponent {

    // Coroutine setup
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    // Timer variables
    private var timeElapsed: Int = 0
    private var isTimerRunning = false
    private var updateTimer = Timer()
    private var detectionTimer = Timer()

    // Notification
    private lateinit var notificationManager: NotificationManager

    // Audio classification
    private var recorder: AudioRecord? = null
    private var classifierTask = Timer()
    private var modelPath = "yamnet.tflite"
    private var probabilityThreshold: Float = 0.3f
    private lateinit var classifier: AudioClassifier
    private lateinit var tensor: TensorAudio

    // Repository
    private val soundRepository: SoundRepository by inject()
    private val historyRepository: HistoryRepository by inject()

    override fun onBind(intent: Intent): IBinder? {
        Timber.d("onBind")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getNotificationManager()

        when (intent?.action) {
            Action.START.toString() -> start()
            Action.STOP.toString() -> stop()
            Action.STATUS.toString() -> sendStatus()
        }

        return START_STICKY
    }

    fun start() {
        isTimerRunning = true

        sendStatus()

        record()

        detectionTimer = Timer()
        detectionTimer.schedule(object : TimerTask() {
            override fun run() {
                val stopwatchIntent = Intent()
                stopwatchIntent.action = TIMER_TICK

                timeElapsed++

                stopwatchIntent.putExtra(TIME_ELAPSED, timeElapsed)
                sendBroadcast(stopwatchIntent)
            }
        }, 0, 1000)

        startForegroundService()
    }

    private fun stop() {
        isTimerRunning = false

        sendStatus()

        serviceScope.launch {
            classifierTask.cancel()
            classifierTask.purge()
            recorder?.release()
            recorder = null
            classifier.close()

            timeElapsed = 0
            Timber.d("Stopwatch Stopped")
            updateTimer.cancel()
            updateTimer.purge()
            detectionTimer.cancel()
            detectionTimer.purge()
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    private fun startForegroundService() {
        if (isTimerRunning) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                startForeground(
                    1,
                    buildNotification(),
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
                )
            } else {
                startForeground(1, buildNotification())
            }

            updateTimer = Timer()

            updateTimer.schedule(object : TimerTask() {
                override fun run() {
                    updateNotification()
                }
            }, 0, 1000)
        }
    }

    private fun sendStatus() {
        val statusIntent = Intent()
        statusIntent.action = Action.STATUS.toString()
        statusIntent.putExtra(IS_TIMER_RUNNING, isTimerRunning)
        statusIntent.putExtra(TIME_ELAPSED, timeElapsed)
        sendBroadcast(statusIntent)
    }

    private fun getNotificationManager() {
        notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
    }

    private fun buildNotification(): Notification {
        val hours: Int = timeElapsed.div(60).div(60)
        val minutes: Int = (timeElapsed.div(60)).rem(60)
        val seconds: Int = timeElapsed.rem(60)

        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentTitle("Listening your surrounding for")
            .setOngoing(true)
            .setContentText(
                "${"%02d".format(hours)}:${"%02d".format(minutes)}:${"%02d".format(seconds)}"
            )
            .setColorized(true)
            .setColor(ContextCompat.getColor(this, R.color.secondary_container))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pIntent)
            .setOnlyAlertOnce(true)
            .setAutoCancel(false)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .addAction(
                R.drawable.ic_stop,
                "Stop",
                PendingIntent.getService(
                    this,
                    0,
                    Intent(this, SoundDetectionService::class.java).also {
                        it.action = Action.STOP.toString()
                    },
                    PendingIntent.FLAG_IMMUTABLE
                )
            )

        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "Timer Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.setShowBadge(true)
        notificationChannel.enableVibration(false)

        notificationBuilder.setChannelId(CHANNEL_ID)
        notificationManager.createNotificationChannel(notificationChannel)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            notificationBuilder.setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)
        }

        return notificationBuilder.build()
    }

    private fun updateNotification() {
        notificationManager.notify(
            1,
            buildNotification()
        )
    }

    @SuppressLint("DiscouragedApi")
    private fun initClassifier() {
        // Initialize classifier only when needed
        classifier = AudioClassifier.createFromFile(this, modelPath)
        tensor = classifier.createInputTensorAudio()
    }

    @SuppressLint("DiscouragedApi")
    private fun record() {
        if (!::classifier.isInitialized) {
            initClassifier()
        }

        recorder = classifier.createAudioRecord()
        recorder?.startRecording()

        classifierTask = Timer()

        val task = object : TimerTask() {
            override fun run() {
                // Classifying audio data
                tensor.load(recorder)
                val output = classifier.classify(tensor)

                // Filtering out classifications with low probability
                val filteredModelOutput = output[0].categories.filter {
                    it.score > probabilityThreshold
                }

                // Creating a multiline string with the filtered results
                val outputStr =
                    filteredModelOutput.sortedBy { -it.score }
                        .joinToString(separator = "\n") { "${it.label} -> ${it.score} " }

                // Log the results
                if (outputStr.isNotEmpty()) {
                    Timber.d("Output: $outputStr")
                }

                // Save the results to the database
                serviceScope.launch {
                    filteredModelOutput.forEach { category ->
                        if (category.label != "Silence") {
                            val sound = soundRepository.getSoundByName(category.label)
                            if (sound != null) {
                                val history = History(
                                    id = 0,
                                    sound = sound,
                                    timestamp = System.now()
                                )
                                historyRepository.insertHistory(history)
                            }
                        }
                    }
                }
            }
        }

        classifierTask.scheduleAtFixedRate(task, 0, 500)
    }

    enum class Action {
        START,
        STOP,
        STATUS
    }

    companion object {
        const val IS_TIMER_RUNNING = "is_timer_running"
        const val TIME_ELAPSED = "time_elapsed"
        const val TIMER_TICK = "timer_tick"
        const val CHANNEL_ID = "timer_channel"
    }
}