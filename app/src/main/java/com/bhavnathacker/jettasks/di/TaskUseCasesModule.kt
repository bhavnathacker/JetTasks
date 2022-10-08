package com.bhavnathacker.jettasks.di

import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import com.bhavnathacker.jettasks.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskUseCasesModule {
    @Singleton
    @Provides
    fun provideGetTask(repository: TaskRepository): GetTask = DefaultGetTask(repository)

    @Singleton
    @Provides
    fun provideGetTasks(repository: TaskRepository): GetTasks = DefaultGetTasks(repository)


    @Singleton
    @Provides
    fun provideSaveTask(repository: TaskRepository): SaveTask = DefaultSaveTask(repository)


    @Singleton
    @Provides
    fun provideDeleteTask(repository: TaskRepository): DeleteTask = DefaultDeleteTask(repository)
}