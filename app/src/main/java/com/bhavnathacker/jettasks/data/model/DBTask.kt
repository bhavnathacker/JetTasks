package com.bhavnathacker.jettasks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhavnathacker.jettasks.domain.entity.Task
import com.bhavnathacker.jettasks.domain.entity.TaskPriority
import com.bhavnathacker.jettasks.domain.entity.TaskStatus
import java.util.Date

@Entity(tableName = "task_tbl")
data class DBTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var deadline: Date,
    var priority: TaskPriority,
    var status: TaskStatus
)

fun DBTask?.toTask(): Task? = this?.let {
    Task(id = it.id, name = it.name, deadline = it.deadline, priority = it.priority, status = it.status)
}

fun Task.toDBTask(): DBTask = DBTask(id = this.id, name = this.name, deadline = this.deadline, priority = this.priority, status = this.status)
