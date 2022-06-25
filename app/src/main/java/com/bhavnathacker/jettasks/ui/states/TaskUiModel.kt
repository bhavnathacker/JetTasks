package com.bhavnathacker.jettasks.ui.states

import com.bhavnathacker.jettasks.UserPreferences.SortOrder
import com.bhavnathacker.jettasks.domain.model.Task

data class TaskUiModel(
    val tasks: List<Task>,
    val showCompleted: Boolean,
    val sortOrder: SortOrder
)
