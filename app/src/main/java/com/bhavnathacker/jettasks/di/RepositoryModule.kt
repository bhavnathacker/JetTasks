package com.bhavnathacker.jettasks.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.data.local.TaskDao
import com.bhavnathacker.jettasks.data.local.TaskDatabase
import com.bhavnathacker.jettasks.data.repository.DefaultTaskRepository
import com.bhavnathacker.jettasks.data.repository.DefaultUserPreferencesRepository
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import com.bhavnathacker.jettasks.domain.repository.UserPreferencesSerializer
import com.bhavnathacker.jettasks.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providesTaskRepository(taskDao: TaskDao): TaskRepository = DefaultTaskRepository(taskDao)

}