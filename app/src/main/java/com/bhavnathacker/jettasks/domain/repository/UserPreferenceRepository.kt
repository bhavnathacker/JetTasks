package com.bhavnathacker.jettasks.domain.repository

import com.bhavnathacker.jettasks.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
  val userPreferencesFlow: Flow<UserPreferences>

  suspend fun enableSortByDeadline(enable: Boolean)

  suspend fun enableSortByPriority(enable: Boolean)

  suspend fun updateShowCompleted(completed: Boolean)
}