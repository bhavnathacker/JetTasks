package com.bhavnathacker.jettasks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhavnathacker.jettasks.R
import java.util.*

enum class TaskPriority(val color: Int) {
    HIGH( R.color.salmon), MEDIUM(R.color.orange), LOW( R.color.light_green);

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.name
            }
        }
    }
}

enum class TaskStatus(val color: Int) {
    PENDING(R.color.red), COMPLETED(R.color.green);

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.name
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
        var status: TaskStatus
)