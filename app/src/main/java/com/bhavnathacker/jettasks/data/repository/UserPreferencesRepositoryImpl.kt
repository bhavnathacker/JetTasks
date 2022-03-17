package com.bhavnathacker.jettasks.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.bhavnathacker.jettasks.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

const val USER_PREFERENCES_NAME = "user_preferences"
const val SORT_ORDER_KEY = "sort_order"

const val DATA_STORE_FILE_NAME = "user_prefs.pb"


/**
 * Class that handles saving and retrieving user preferences
 */
class UserPreferencesRepositoryImpl @Inject constructor(private val userPreferencesStore: DataStore<UserPreferences>) :
    UserPreferenceRepository {

    private val TAG: String = "UserPrefRepoImpl"


    override val userPreferencesFlow: Flow<UserPreferences> = userPreferencesStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }


    override suspend fun enableSortByDeadline(enable: Boolean) {
        // updateData handles data transactionally, ensuring that if the sort is updated at the same
        // time from another thread, we won't have conflicts
        userPreferencesStore.updateData { currentPreferences ->
            val currentOrder = currentPreferences.sortOrder
            val newSortOrder =
                if (enable) {
                    if (currentOrder == UserPreferences.SortOrder.BY_PRIORITY) {
                        UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY
                    } else {
                        UserPreferences.SortOrder.BY_DEADLINE
                    }
                } else {
                    if (currentOrder == UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY) {
                        UserPreferences.SortOrder.BY_PRIORITY
                    } else {
                        UserPreferences.SortOrder.NONE
                    }
                }
            currentPreferences.toBuilder().setSortOrder(newSortOrder).build()
        }
    }

    override suspend fun enableSortByPriority(enable: Boolean) {
        // updateData handles data transactionally, ensuring that if the sort is updated at the same
        // time from another thread, we won't have conflicts
        userPreferencesStore.updateData { currentPreferences ->
            val currentOrder = currentPreferences.sortOrder
            val newSortOrder =
                if (enable) {
                    if (currentOrder == UserPreferences.SortOrder.BY_DEADLINE) {
                        UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY
                    } else {
                        UserPreferences.SortOrder.BY_PRIORITY
                    }
                } else {
                    if (currentOrder == UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY) {
                        UserPreferences.SortOrder.BY_DEADLINE
                    } else {
                        UserPreferences.SortOrder.NONE
                    }
                }
            currentPreferences.toBuilder().setSortOrder(newSortOrder).build()
        }
    }

    override suspend fun updateShowCompleted(completed: Boolean) {
        userPreferencesStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setShowCompleted(completed).build()
        }
    }
}