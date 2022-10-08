package com.bhavnathacker.jettasks.domain.entity

import androidx.compose.ui.graphics.Color
import com.bhavnathacker.jettasks.presentation.theme.Amber500
import com.bhavnathacker.jettasks.presentation.theme.Green500
import com.bhavnathacker.jettasks.presentation.theme.Orange500
import com.bhavnathacker.jettasks.presentation.theme.Red500
import java.util.Date

data class Task(
    val id: Int,
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