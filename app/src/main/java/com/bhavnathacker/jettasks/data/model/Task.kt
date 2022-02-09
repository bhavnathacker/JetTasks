package com.bhavnathacker.jettasks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhavnathacker.jettasks.R
import java.util.*

enum class TaskPriority {
    LOW, MEDIUM, HIGH
}

@Entity(tableName = "task_tbl")
data class Task(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        var name: String,
        var deadline: Date,
        var priority: String,
        var completed: Boolean = false
) {
    val priorityColor: Int
        get() {
            return when (priority) {
                TaskPriority.LOW.name -> R.color.yellow
                TaskPriority.MEDIUM.name -> R.color.orange
                TaskPriority.HIGH.name -> R.color.salmon
                else -> R.color.yellow
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
