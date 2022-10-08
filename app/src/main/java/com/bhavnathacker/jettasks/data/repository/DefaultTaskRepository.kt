package com.bhavnathacker.jettasks.data.repository

import com.bhavnathacker.jettasks.data.local.TaskDao
import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(private val taskDao: TaskDao): TaskRepository {
    override suspend fun saveTask(task: Task) = taskDao.insert(task)
    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    override suspend fun getTask(id: Int?): Task? = taskDao.getTask(id)
    override fun getAllTasks(): Flow<List<Task>> = taskDao.getTasks().flowOn(Dispatchers.IO)
            .conflate()

}