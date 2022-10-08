package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class DefaultEnableSortByPriority @Inject constructor(val repository: UserPreferenceRepository): EnableSortByPriority {
    override suspend operator fun invoke(enable: Boolean) {
        repository.enableSortByPriority(enable)
    }
}