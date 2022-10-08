package com.bhavnathacker.jettasks.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhavnathacker.jettasks.presentation.theme.Amber500
import com.bhavnathacker.jettasks.presentation.theme.Green500
import com.bhavnathacker.jettasks.presentation.theme.Orange500
import com.bhavnathacker.jettasks.presentation.theme.Red500
import java.util.*

@Entity(tableName = "task_tbl")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var deadline: Date,
    var priority: TaskPriority,
    var status: TaskStatus
) {
    val bgColor: Color
        get() {
            return if (status == TaskStatus.COMPLETED) {
                Green500
            } else when (priority) {
                TaskPriority.LOW -> Amber500
                TaskPriority.MEDIUM -> Orange500
                TaskPriority.HIGH -> Red500
            }
        }

    val contentColor: Color
        get() = Color.White
}