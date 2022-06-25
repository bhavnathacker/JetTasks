package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository

class GetTask(val repository: TaskRepository) {
    suspend operator fun invoke(id: Int?): Task? {
        return repository.getTask(id)
    }
}