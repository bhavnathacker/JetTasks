package com.bhavnathacker.jettasks.data.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
  val sortOrderFlow: Flow<SortOrder>

  suspend fun enableSortByDeadline(enable: Boolean)

  suspend fun enableSortByPriority(enable: Boolean)
}