package com.bhavnathacker.jettasks.domain.repository

import com.bhavnathacker.jettasks.domain.entity.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun saveTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun getTask(id: Int?): Task?
    fun getAllTasks(): Flow<List<Task>>
}