package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow

class GetUserPreferences(val repository: UserPreferenceRepository) {
    operator fun invoke(): Flow<UserPreferences> {
        return repository.userPreferencesFlow
    }
}