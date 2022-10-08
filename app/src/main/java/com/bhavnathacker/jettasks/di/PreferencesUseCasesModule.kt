package com.bhavnathacker.jettasks.di

import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import com.bhavnathacker.jettasks.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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