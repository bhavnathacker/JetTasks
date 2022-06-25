package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository

class DeleteTask(val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}