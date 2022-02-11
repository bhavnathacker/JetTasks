package com.bhavnathacker.jettasks.di

import android.content.Context
import androidx.room.Room
import com.bhavnathacker.jettasks.data.local.TaskDao
import com.bhavnathacker.jettasks.data.local.TaskDatabase
import com.bhavnathacker.jettasks.data.repository.TaskRepository
import com.bhavnathacker.jettasks.data.repository.TaskRepositoryImpl
import com.bhavnathacker.jettasks.data.repository.UserPreferenceRepository
import com.bhavnathacker.jettasks.data.repository.UserPreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TaskDatabase = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "task_db")
        .build()

    @Singleton
    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao = taskDatabase.taskDao()

    @Singleton
    @Provides
    fun providesTaskRepository(taskDao: TaskDao) : TaskRepository = TaskRepositoryImpl(taskDao)

    @Singleton
    @Provides
    fun providesUserPreferencesRepository(@ApplicationContext context: Context) : UserPreferenceRepository = UserPreferencesRepositoryImpl(context)

}