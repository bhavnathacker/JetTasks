package com.bhavnathacker.jettasks.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.data.local.TaskDao
import com.bhavnathacker.jettasks.data.local.TaskDatabase
import com.bhavnathacker.jettasks.data.repository.TaskRepositoryImpl
import com.bhavnathacker.jettasks.data.repository.UserPreferencesRepositoryImpl
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

private const val DATA_STORE_FILE_NAME = "user_prefs.pb"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TaskDatabase =
        Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_db"
        )
            .build()

    @Singleton
    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao = taskDatabase.taskDao()

    @Singleton
    @Provides
    fun providesTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)

    @Singleton
    @Provides
    fun providesUserPreferencesRepository(datastore: DataStore<UserPreferences>): UserPreferenceRepository =
        UserPreferencesRepositoryImpl(datastore)

    @Singleton
    @Provides
    fun providesDataStore(@ApplicationContext appContext: Context): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { appContext.dataStoreFile(DATA_STORE_FILE_NAME) },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    @Singleton
    @Provides
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getTask = GetTask(repository),
            getTasks = GetTasks(repository),
            saveTask = SaveTask(repository),
            deleteTask = DeleteTask(repository)
        )
    }

    @Singleton
    @Provides
    fun provideUserPreferenceUseCases(repository: UserPreferenceRepository): UserPreferenceUseCases {
        return UserPreferenceUseCases(
            getUserPreferences = GetUserPreferences(repository),
            updateShowCompleted = UpdateShowCompleted(repository),
            enableSortByDeadline = EnableSortByDeadline(repository),
            enableSortByPriority = EnableSortByPriority(repository)
        )
    }

}