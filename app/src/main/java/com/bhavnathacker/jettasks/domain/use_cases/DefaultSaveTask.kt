package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import javax.inject.Inject

class DefaultSaveTask @Inject constructor(val repository: TaskRepository): SaveTask {
    override suspend operator fun invoke(task: Task) {
        repository.saveTask(task)
    }
}