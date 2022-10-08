package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.entity.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultGetTasks @Inject constructor(val repository: TaskRepository): GetTasks {
     override suspend operator fun invoke(): Flow<List<Task>> {
        return repository.getAllTasks()
    }
}