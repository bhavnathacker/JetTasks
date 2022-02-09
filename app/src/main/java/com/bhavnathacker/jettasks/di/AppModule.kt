package com.bhavnathacker.jettasks.di

import android.content.Context
import androidx.room.Room
import com.bhavnathacker.jettasks.data.local.TaskDao
import com.bhavnathacker.jettasks.data.local.TaskDatabase
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
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao = taskDatabase.taskDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TaskDatabase = Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_db")
            .fallbackToDestructiveMigration()
            .build()
}