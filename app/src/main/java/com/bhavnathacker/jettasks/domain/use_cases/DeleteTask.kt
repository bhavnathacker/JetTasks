package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task

interface DeleteTask {
    suspend operator fun invoke(task: Task)
}