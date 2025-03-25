package com.harissabil.swarsware.di

import android.content.Context
import com.harissabil.swarsware.data.datastore.PreferenceRepositoryImpl
import com.harissabil.swarsware.domain.repository.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferenceRepository(
        @ApplicationContext context: Context,
    ): PreferenceRepository {
        return PreferenceRepositoryImpl(context)
    }
}