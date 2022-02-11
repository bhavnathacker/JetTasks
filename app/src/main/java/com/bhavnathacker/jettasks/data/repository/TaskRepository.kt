package com.bhavnathacker.jettasks.data.repository

import com.bhavnathacker.jettasks.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun saveTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun deleteAllTasks()
    fun getAllTasks(): Flow<List<Task>>
}