package com.bhavnathacker.jettasks.data.repository

import com.bhavnathacker.jettasks.data.dao.TaskDao
import com.bhavnathacker.jettasks.data.mapper.DBTaskMapper
import com.bhavnathacker.jettasks.data.mapper.TaskMapper
import com.bhavnathacker.jettasks.data.model.toDBTask
import com.bhavnathacker.jettasks.data.model.toTask
import com.bhavnathacker.jettasks.domain.entity.Task
import com.bhavnathacker.jettasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val taskDao: TaskDao) : TaskRepository {
    override suspend fun saveTask(task: Task) = taskDao.insert(task.toDBTask())
    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task.toDBTask())
    override suspend fun getTask(id: Int?): Task? = taskDao.getTask(id).toTask()
    override fun getAllTasks(): Flow<List<Task>> = taskDao.getTasks().map { list ->
        list.map { (it.toTask())!! }
    }
}