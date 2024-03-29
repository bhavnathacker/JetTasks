package com.bhavnathacker.jettasks.presentation.events

import com.bhavnathacker.jettasks.domain.entity.TaskPriority
import com.bhavnathacker.jettasks.domain.entity.TaskStatus
import java.util.*

sealed class TaskDetailEvent {
    data class ChangeDeadline(val newDate: Date): TaskDetailEvent()
    data class ChangeName(val newName: String): TaskDetailEvent()
    data class ChangePriority(val newPriority: TaskPriority): TaskDetailEvent()
    data class ChangeStatus(val newStatus: TaskStatus): TaskDetailEvent()
    object SaveTask: TaskDetailEvent()
}