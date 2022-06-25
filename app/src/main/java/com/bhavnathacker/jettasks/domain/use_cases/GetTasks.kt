package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasks(val repository: TaskRepository) {
     operator fun invoke(): Flow<List<Task>> {
        return repository.getAllTasks()
    }
}