package com.bhavnathacker.jettasks.di

import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.data.local.TaskDao
import com.bhavnathacker.jettasks.data.local.TaskDatabase
import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import com.bhavnathacker.jettasks.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesUseCasesModule {
    @Singleton
    @Provides
    fun provideGetUserPreferences(repository: UserPreferenceRepository): GetUserPreferences = DefaultGetUserPreferences(repository)

    @Singleton
    @Provides
    fun provideEnableSortByPriority(repository: UserPreferenceRepository): EnableSortByPriority = DefaultEnableSortByPriority(repository)

    @Singleton
    @Provides
    fun provideEnableSortByDeadline(repository: UserPreferenceRepository): EnableSortByDeadline = DefaultEnableSortByDeadline(repository)

    @Singleton
    @Provides
    fun provideUpdateShowCompleted(repository: UserPreferenceRepository): UpdateShowCompleted = DefaultUpdateShowCompleted(repository)

}