package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task

interface SaveTask {
    suspend operator fun invoke(task: Task)
}