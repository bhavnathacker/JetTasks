package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository

class EnableSortByPriority(val repository: UserPreferenceRepository) {
    suspend operator fun invoke(enable: Boolean) {
        repository.enableSortByPriority(enable)
    }
}