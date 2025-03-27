package com.harissabil.swarsware.di

import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    operator fun invoke(): Module = module {
        includes(
            listOf(
                dataStoreModule,
                viewModelModule,
                databaseModule
            )
        )
    }
}