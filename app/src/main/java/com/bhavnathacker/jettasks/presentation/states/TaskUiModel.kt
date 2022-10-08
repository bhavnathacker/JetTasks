package com.bhavnathacker.jettasks.presentation.states

import com.bhavnathacker.jettasks.UserPreferences.SortOrder
import com.bhavnathacker.jettasks.domain.entity.Task

data class TaskUiModel(
    val tasks: List<Task>,
    val showCompleted: Boolean,
    val sortOrder: SortOrder
)
