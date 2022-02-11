package com.bhavnathacker.jettasks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhavnathacker.jettasks.R
import java.util.*

enum class TaskPriority {
    HIGH, MEDIUM, LOW;

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }
    }
}

@Entity(tableName = "task_tbl")
data class Task(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        var name: String,
        var deadline: Date,
        var priority: TaskPriority,
        var completed: Boolean = false
) {
    val priorityColor: Int
        get() {
            return when (priority) {
                TaskPriority.LOW -> R.color.light_green
                TaskPriority.MEDIUM -> R.color.orange
                TaskPriority.HIGH -> R.color.salmon
            }
        }

    val statusString: Int
        get() {
            return if (completed) {
                R.string.completed
            } else {
                R.string.pending
            }
        }

    val statusColor: Int
        get() {
            return if (completed) {
                R.color.green
            } else {
                R.color.red
            }
        }

}
