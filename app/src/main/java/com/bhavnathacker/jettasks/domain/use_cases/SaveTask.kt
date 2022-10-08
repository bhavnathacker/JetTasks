package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.entity.Task

interface SaveTask {
    suspend operator fun invoke(task: Task)
}