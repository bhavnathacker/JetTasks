package com.bhavnathacker.jettasks.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import androidx.room.Room
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.data.local.TaskDao
import com.bhavnathacker.jettasks.data.local.TaskDatabase
import com.bhavnathacker.jettasks.data.repository.*
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

/*    @Singleton
    @Provides
    fun providesUserPreferencesRepository(@ApplicationContext context: Context) : UserPreferenceRepository = UserPreferencesRepositoryImpl(context)*/

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
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            migrations =
            listOf(
                SharedPreferencesMigration(
                    appContext,
                    USER_PREFERENCES_NAME
                ) { sharedPrefs: SharedPreferencesView, currentData: UserPreferences ->
                    // Define the mapping from SharedPreferences to UserPreferences
                    if (currentData.sortOrder == UserPreferences.SortOrder.UNSPECIFIED) {
                        currentData.toBuilder().setSortOrder(
                            UserPreferences.SortOrder.valueOf(
                                sharedPrefs.getString(
                                    SORT_ORDER_KEY,
                                    UserPreferences.SortOrder.NONE.name
                                )!!
                            )
                        ).build()
                    } else {
                        currentData
                    }
                }
            )
        )
    }

}