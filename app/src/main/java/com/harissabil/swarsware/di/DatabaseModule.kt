package com.harissabil.swarsware.di

import androidx.room.Room
import com.harissabil.swarsware.data.database.EmergencyRepositoryImpl
import com.harissabil.swarsware.data.database.HistoryRepositoryImpl
import com.harissabil.swarsware.data.database.SoundRepositoryImpl
import com.harissabil.swarsware.data.database.room.PrepopulateRoomCallback
import com.harissabil.swarsware.data.database.room.SwarSwareDatabase
import com.harissabil.swarsware.domain.repository.EmergencyRepository
import com.harissabil.swarsware.domain.repository.HistoryRepository
import com.harissabil.swarsware.domain.repository.SoundRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = SwarSwareDatabase::class.java,
            name = "swarsware_db"
        )
            .addCallback(PrepopulateRoomCallback(androidContext()))
            .build()
    }

    single { get<SwarSwareDatabase>().soundDao() }
    single { get<SwarSwareDatabase>().historyDao() }
    single { get<SwarSwareDatabase>().emergencyDao() }

    single<SoundRepository> { SoundRepositoryImpl(get()) }
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }
    single<EmergencyRepository> { EmergencyRepositoryImpl(get()) }
}