package com.bhavnathacker.jettasks.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.UserPreferences.SortOrder
import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

/**
 * Class that handles saving and retrieving user preferences
 */
class DefaultUserPreferencesRepository @Inject constructor(private val userPreferencesStore: DataStore<UserPreferences>) :
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

    /**
     * Enable / disable sort by deadline.
     */
    override suspend fun enableSortByDeadline(enable: Boolean) {
        // updateData handles data transactionally, ensuring that if the sort is updated at the same
        // time from another thread, we won't have conflicts
        userPreferencesStore.updateData { currentPreferences ->
            val currentOrder = currentPreferences.sortOrder
            val newSortOrder =
                if (enable) {
                    if (currentOrder == SortOrder.BY_PRIORITY) {
                        SortOrder.BY_DEADLINE_AND_PRIORITY
                    } else {
                        SortOrder.BY_DEADLINE
                    }
                } else {
                    if (currentOrder == SortOrder.BY_DEADLINE_AND_PRIORITY) {
                        SortOrder.BY_PRIORITY
                    } else {
                        SortOrder.NONE
                    }
                }
            currentPreferences.toBuilder().setSortOrder(newSortOrder).build()
        }
    }

    /**
     * Enable / disable sort by priority.
     */
    override suspend fun enableSortByPriority(enable: Boolean) {
        // updateData handles data transactionally, ensuring that if the sort is updated at the same
        // time from another thread, we won't have conflicts
        userPreferencesStore.updateData { currentPreferences ->
            val currentOrder = currentPreferences.sortOrder
            val newSortOrder =
                if (enable) {
                    if (currentOrder == SortOrder.BY_DEADLINE) {
                        SortOrder.BY_DEADLINE_AND_PRIORITY
                    } else {
                        SortOrder.BY_PRIORITY
                    }
                } else {
                    if (currentOrder == SortOrder.BY_DEADLINE_AND_PRIORITY) {
                        SortOrder.BY_DEADLINE
                    } else {
                        SortOrder.NONE
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