package com.bhavnathacker.jettasks.domain.use_cases

interface UpdateShowCompleted {
    suspend operator fun invoke(show: Boolean)
}