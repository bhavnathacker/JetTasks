package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class DefaultEnableSortByDeadline @Inject constructor(val repository: UserPreferenceRepository): EnableSortByDeadline {
    override suspend operator fun invoke(enable: Boolean) {
        repository.enableSortByDeadline(enable)
    }
}