package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class DefaultUpdateShowCompleted @Inject constructor(val repository: UserPreferenceRepository): UpdateShowCompleted {
    override suspend operator fun invoke(show: Boolean) {
        repository.updateShowCompleted(show)
    }
}