package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.data.repository.FakeTaskRepository
import com.bhavnathacker.jettasks.domain.entity.Task
import com.bhavnathacker.jettasks.domain.entity.TaskPriority
import com.bhavnathacker.jettasks.domain.entity.TaskStatus
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class DefaultGetTaskTest {
    private lateinit var getTask: DefaultGetTask
    private lateinit var fakeTaskRepository: FakeTaskRepository

    @Before
    fun setup() {
       fakeTaskRepository = FakeTaskRepository()
       getTask = DefaultGetTask(fakeTaskRepository)

        val tasksToInsert = mutableListOf<Task>()
        tasksToInsert.add(Task(0, name =  "Task1", deadline = Date(), priority = TaskPriority.LOW, status = TaskStatus.PENDING))
        tasksToInsert.add(Task(1, name =  "Task2", deadline = Date(), priority = TaskPriority.MEDIUM, status = TaskStatus.COMPLETED))
        tasksToInsert.add(Task(2, name =  "Task3", deadline = Date(), priority = TaskPriority.HIGH, status = TaskStatus.PENDING))
        tasksToInsert.add(Task(3, name =  "Task4", deadline = Date(), priority = TaskPriority.LOW, status = TaskStatus.COMPLETED))

        tasksToInsert.shuffle()
        runBlocking {
            tasksToInsert.forEach { fakeTaskRepository.saveTask(it) }
        }
    }

    @Test
    fun `get task by id validate name`() = runBlocking {
        val note = getTask(0)

        assertThat(note?.name).isEqualTo("Task1")
    }

    @Test
    fun `get task by id validate priority`() = runBlocking {
        val note = getTask(1)

        assertThat(note?.priority).isEqualTo(TaskPriority.MEDIUM)
    }


    @Test
    fun `get task by id validate status`() = runBlocking {
        val note = getTask(2)

        assertThat(note?.status).isEqualTo(TaskStatus.PENDING)
    }
}