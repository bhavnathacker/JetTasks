package com.bhavnathacker.jettasks.di

import com.bhavnathacker.jettasks.data.dao.TaskDao
import com.bhavnathacker.jettasks.data.mapper.DBTaskMapper
import com.bhavnathacker.jettasks.data.mapper.TaskMapper
import com.bhavnathacker.jettasks.data.repository.DefaultTaskRepository
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providesTaskRepository(taskDao: TaskDao): TaskRepository = DefaultTaskRepository(taskDao)

}