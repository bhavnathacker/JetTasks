package com.bhavnathacker.jettasks.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.data.dao.TaskDao
import com.bhavnathacker.jettasks.data.database.TaskDatabase
import com.bhavnathacker.jettasks.data.mapper.DBTaskMapper
import com.bhavnathacker.jettasks.data.mapper.TaskMapper
import com.bhavnathacker.jettasks.data.mapper.toDBTask
import com.bhavnathacker.jettasks.data.mapper.toTask
import com.bhavnathacker.jettasks.data.repository.DefaultTaskRepository
import com.bhavnathacker.jettasks.data.repository.DefaultUserPreferencesRepository
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import com.bhavnathacker.jettasks.domain.repository.UserPreferencesSerializer
import com.bhavnathacker.jettasks.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton


@OptIn(ExperimentalCoroutinesApi::class)
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class, PreferencesModule::class, PreferencesUseCasesModule::class, RepositoryModule::class, TaskUseCasesModule::class]
)
object TestAppModule {

    private const val TEST_DATA_STORE_FILE_NAME = "test_user_prefs.pb"

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TaskDatabase =
        Room.inMemoryDatabaseBuilder(
            context,
            TaskDatabase::class.java
        )
            .build()

    @Singleton
    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao = taskDatabase.taskDao()

    @Singleton
    @Provides
    fun providesTaskRepository(taskDao: TaskDao): TaskRepository = DefaultTaskRepository(taskDao)

    @Singleton
    @Provides
    fun providesUserPreferencesRepository(datastore: DataStore<UserPreferences>): UserPreferenceRepository =
        DefaultUserPreferencesRepository(datastore)

    @Singleton
    @Provides
    fun providesDataStore(@ApplicationContext appContext: Context): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { appContext.dataStoreFile(TEST_DATA_STORE_FILE_NAME) },
            scope = TestScope(UnconfinedTestDispatcher())
        )
    }

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