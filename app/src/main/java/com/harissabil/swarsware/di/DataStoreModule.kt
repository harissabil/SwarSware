package com.harissabil.swarsware.di

import com.harissabil.swarsware.data.datastore.PreferenceRepositoryImpl
import com.harissabil.swarsware.domain.repository.PreferenceRepository
import org.koin.dsl.module

val dataStoreModule = module {
    single<PreferenceRepository> { PreferenceRepositoryImpl(get()) }
}