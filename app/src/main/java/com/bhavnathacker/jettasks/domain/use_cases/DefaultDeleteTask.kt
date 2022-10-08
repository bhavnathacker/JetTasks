package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import javax.inject.Inject

class DefaultDeleteTask @Inject constructor(val repository: TaskRepository): DeleteTask{
    override suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}