package com.bhavnathacker.jettasks.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.data.model.Task
import com.bhavnathacker.jettasks.data.model.TaskPriority
import com.bhavnathacker.jettasks.ui.components.*
import com.bhavnathacker.jettasks.ui.viewmodels.TaskViewModel
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun TaskDetail(taskViewModel: TaskViewModel, taskId: Int?, onSaveTask: (Task) -> Unit) {
    val context = LocalContext.current
    val defaultDate = Date(Calendar.getInstance().timeInMillis)

    var task: Task? = null
    taskId?.let {
        task = taskViewModel.getTaskById(taskId)
    }

    var name by remember {
        mutableStateOf(task?.name ?: "")
    }

    var selectedDate: Date by remember {
        mutableStateOf(task?.deadline ?: defaultDate)
    }


    val taskPriorities = listOf(TaskPriority.LOW.name, TaskPriority.MEDIUM.name, TaskPriority.HIGH.name)
    var taskPriorityExpanded by remember { mutableStateOf(false) }

    val defaultPriorityIndex = if (taskPriorities.indexOf(task?.priority) != -1) {
        taskPriorities.indexOf(task?.priority)
    } else {
        0
    }
    var selectedPriorityIndex by remember { mutableStateOf(defaultPriorityIndex) }

    var isCompleted by remember { mutableStateOf(task?.completed ?: false) }

    Column {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        }, backgroundColor = MaterialTheme.colors.primary)

        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalAlignment = Alignment.Start) {

            TaskInputText(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                    text = name,
                    label = "Add a Task",
                    onTextChange = { name = it })

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = stringResource(R.string.task_deadline))
            TaskDatePicker(selectedDate) { timeInMillis ->
                selectedDate = Date(timeInMillis)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = stringResource(R.string.task_priority))
            TaskMenu(
                    menuItems = taskPriorities,
                    menuExpandedState = taskPriorityExpanded,
                    selectedIndex = selectedPriorityIndex,
                    updateMenuExpandStatus = {
                        taskPriorityExpanded = true
                    },
                    onDismissMenuView = {
                        taskPriorityExpanded = false
                    },
                    onMenuItemClick = { index ->
                        selectedPriorityIndex = index
                        taskPriorityExpanded = false
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            TaskSwitch(stringResource(id = R.string.completed), isCompleted, onCheckChanged = { isCompleted = it })

            Spacer(modifier = Modifier.height(20.dp))

            val taskUpdatedMsg = stringResource(R.string.task_updated)
            val taskAddedMsg = stringResource(R.string.task_added)
            val addTaskMsg = stringResource(R.string.add_task_first)

            TaskButton(text = stringResource(R.string.save), modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                    onClick = {
                        var toastMessage = ""
                        if (name.isNotEmpty()) {
                            if (task != null) {
                                //Update Task
                                task!!.name = name
                                task!!.deadline = selectedDate
                                task!!.priority = taskPriorities[selectedPriorityIndex]
                                task!!.completed = isCompleted
                                toastMessage = taskUpdatedMsg
                            } else {
                                //Add Task
                                task = Task(name = name,
                                        deadline = selectedDate, priority = taskPriorities[selectedPriorityIndex], completed = isCompleted)
                                toastMessage = taskAddedMsg
                            }
                            //Save it in Room
                            onSaveTask(task!!)
                        } else {
                            toastMessage = addTaskMsg
                        }
                        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                    })
        }

    }

}
