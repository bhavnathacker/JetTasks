package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import javax.inject.Inject

class DefaultGetTask @Inject constructor(val repository: TaskRepository): GetTask {
    override suspend operator fun invoke(id: Int?): Task? {
        return repository.getTask(id)
    }
}