package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultGetUserPreferences @Inject constructor(val repository: UserPreferenceRepository): GetUserPreferences {
    override suspend operator fun invoke(): Flow<UserPreferences> {
        return repository.userPreferencesFlow
    }
}