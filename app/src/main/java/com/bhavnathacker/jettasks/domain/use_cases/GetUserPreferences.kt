package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.UserPreferences
import kotlinx.coroutines.flow.Flow

interface GetUserPreferences {
    suspend operator fun invoke(): Flow<UserPreferences>
}