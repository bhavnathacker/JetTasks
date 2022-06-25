package com.bhavnathacker.jettasks.domain.use_cases

data class UserPreferenceUseCases(
    val getUserPreferences: GetUserPreferences,
    val updateShowCompleted: UpdateShowCompleted,
    val enableSortByDeadline: EnableSortByDeadline,
    val enableSortByPriority: EnableSortByPriority
)