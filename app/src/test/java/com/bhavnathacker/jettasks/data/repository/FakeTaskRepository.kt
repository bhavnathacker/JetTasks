package com.bhavnathacker.jettasks.data.repository

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskRepository: TaskRepository {
    private val tasks = mutableListOf<Task>()

    override suspend fun saveTask(task: Task) {
       tasks.add(task)
    }

    override suspend fun deleteTask(task: Task) {
        tasks.remove(task)
    }

    override suspend fun getTask(id: Int?): Task? {
        return tasks.find { it.id == id }
    }

    override fun getAllTasks(): Flow<List<Task>> {
       return flow { emit(tasks) }
    }
}