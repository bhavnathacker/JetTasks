package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository

class UpdateShowCompleted(val repository: UserPreferenceRepository) {
    suspend operator fun invoke(show: Boolean) {
        repository.updateShowCompleted(show)
    }
}