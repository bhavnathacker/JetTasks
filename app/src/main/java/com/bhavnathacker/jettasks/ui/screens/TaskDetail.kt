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
import com.bhavnathacker.jettasks.data.model.TaskStatus
import com.bhavnathacker.jettasks.ui.components.*
import com.bhavnathacker.jettasks.util.getDateWithoutTime
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun TaskDetail(task: Task?, onSaveTask: (Task) -> Unit) {
    val context = LocalContext.current
    val defaultDate = Date(Calendar.getInstance().timeInMillis)

    var name by remember { mutableStateOf(task?.name ?: "") }
    var selectedDate: Date by remember { mutableStateOf(task?.deadline ?: defaultDate) }
    var priorityExpanded by remember { mutableStateOf(false) }
    val defaultPriorityIndex = TaskPriority.values().indexOf(task?.priority)
    var selectedPriorityIndex by remember { mutableStateOf(if(defaultPriorityIndex != -1) defaultPriorityIndex else 0) }
    var status by remember { mutableStateOf(task?.status ?: TaskStatus.PENDING) }

    Column {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        }, backgroundColor = MaterialTheme.colors.primary)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            TaskInputText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = name,
                label = stringResource(R.string.label_add_task),
                onTextChange = { name = it })

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = stringResource(R.string.task_deadline))
            TaskDatePicker(selectedDate) { date ->
                selectedDate = date
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = stringResource(R.string.task_priority))
            TaskMenu(
                menuItems = TaskPriority.getList(),
                menuExpandedState = priorityExpanded,
                selectedIndex = selectedPriorityIndex,
                updateMenuExpandStatus = {
                    priorityExpanded = true
                },
                onDismissMenuView = {
                    priorityExpanded = false
                },
                onMenuItemClick = { index ->
                    selectedPriorityIndex = index
                    priorityExpanded = false
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            TaskSwitch(
                stringResource(id = R.string.completed),
                status == TaskStatus.COMPLETED,
                onCheckChanged = { isChecked -> status =
                    if(isChecked) TaskStatus.COMPLETED else TaskStatus.PENDING })

            Spacer(modifier = Modifier.height(20.dp))

            val taskUpdatedMsg = stringResource(R.string.task_updated)
            val taskAddedMsg = stringResource(R.string.task_added)
            val addTaskMsg = stringResource(R.string.add_task_first)

            TaskButton(text = stringResource(R.string.save), modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
                onClick = {
                    var toastMessage: String
                    if (name.isNotEmpty()) {
                        if (task != null) {
                            //Update Task
                            task.name = name
                            task.deadline = selectedDate.getDateWithoutTime()
                            task.priority = TaskPriority.values()[selectedPriorityIndex]
                            task.status = status
                            toastMessage = taskUpdatedMsg
                            onSaveTask(task)
                        } else {
                            //Add Task
                            val newTask = Task(
                                name = name,
                                deadline = selectedDate.getDateWithoutTime(),
                                priority = TaskPriority.values()[selectedPriorityIndex],
                                status = status
                            )
                            toastMessage = taskAddedMsg
                            onSaveTask(newTask)
                        }
                    } else {
                        toastMessage = addTaskMsg
                    }
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                })
        }

    }

}
