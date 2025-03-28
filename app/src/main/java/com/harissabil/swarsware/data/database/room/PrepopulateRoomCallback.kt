package com.harissabil.swarsware.data.database.room

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.harissabil.swarsware.R
import com.harissabil.swarsware.data.database.dao.SoundDao
import com.harissabil.swarsware.data.database.entity.SoundEntity
import com.harissabil.swarsware.domain.model.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class PrepopulateRoomCallback(private val context: Context) : RoomDatabase.Callback(),
    KoinComponent {

    private val soundDao: SoundDao by inject()

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prepopulateSounds()
        }
    }

    @Serializable
    private data class SoundJson(
        val id: Long,
        val name: String,
        val description: String,
        val priority: String? = null,
    )

    private suspend fun prepopulateSounds() {
        try {
            val inputStream = context.resources.openRawResource(R.raw.sounds)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val soundsList = Json.decodeFromString<List<SoundJson>>(jsonString)
            val soundEntities = soundsList.map { sound ->
                SoundEntity(
                    id = sound.id + 1,
                    name = sound.name,
                    description = sound.description,
                    priority = sound.priority?.let { priorityString ->
                        Priority.entries.find { it.displayName == priorityString }
                    }
                )
            }

            soundEntities.forEach { sound ->
                soundDao.insertSound(sound)
            }
            Timber.d("Prepopulate sounds success")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}