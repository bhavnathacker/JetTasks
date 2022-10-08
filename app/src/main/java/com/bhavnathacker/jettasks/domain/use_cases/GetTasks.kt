package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.entity.Task
import kotlinx.coroutines.flow.Flow

interface GetTasks {
    suspend operator fun invoke(): Flow<List<Task>>
}