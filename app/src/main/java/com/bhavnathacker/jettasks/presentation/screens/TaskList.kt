package com.bhavnathacker.jettasks.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.domain.entity.Task
import com.bhavnathacker.jettasks.presentation.components.TaskChip
import com.bhavnathacker.jettasks.presentation.events.TaskListEvent
import com.bhavnathacker.jettasks.presentation.navigation.TaskScreens
import com.bhavnathacker.jettasks.presentation.viewmodels.TaskListViewModel
import com.bhavnathacker.jettasks.util.*

@ExperimentalComposeUiApi
@Composable
fun TaskList(
    navController: NavController,
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val taskUiState = viewModel.tasksUiModelStateFlow.collectAsState().value
    val tasks = taskUiState.tasks
    val showCompleted = taskUiState.showCompleted
    val sortOrder = taskUiState.sortOrder

    val isPrioritySortSelected =
        sortOrder == UserPreferences.SortOrder.BY_PRIORITY || sortOrder == UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY
    val isDeadlineSortSelected =
        sortOrder == UserPreferences.SortOrder.BY_DEADLINE || sortOrder == UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY

    Column {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        }, backgroundColor = MaterialTheme.colors.primary)

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val (list, bottomControls, noTasksText) = createRefs()

            LazyColumn(modifier = Modifier
                .constrainAs(list) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomControls.top)
                    height = Dimension.fillToConstraints
                }) {
                items(tasks) { task ->
                    TaskRow(task = task,
                        onViewTask = { navController.navigate(TaskScreens.DetailScreen.name + "/${it.id}") },
                        onDeleteTask = { viewModel.onEvent(TaskListEvent.DeleteTask(it)) })
                }
            }

            if (tasks.isEmpty()) {
                Text(
                    modifier = Modifier
                        .constrainAs(noTasksText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    text = stringResource(R.string.msg_no_tasks),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
            }

            Surface(modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(bottomControls) {
                    bottom.linkTo(parent.bottom)
                }) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(MaterialTheme.colors.secondary.copy(0.25f))) {

                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(0.dp)
                            .weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(R.string.show_completed_tasks))
                            Switch(
                                checked = showCompleted,
                                onCheckedChange = { viewModel.onEvent(TaskListEvent.ShowCompletedTasks(it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colors.secondary,
                                    uncheckedThumbColor = MaterialTheme.colors.onBackground.copy(0.5f),
                                    checkedTrackColor = MaterialTheme.colors.secondary.copy(0.5f),
                                    uncheckedTrackColor = MaterialTheme.colors.secondary.copy(0.5f)
                                ), modifier = Modifier.testTag(TestTags.SHOW_COMPLETED_SWITCH))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(R.string.sort_by))
                            TaskChip(
                                name = stringResource(id = R.string.priority),
                                isSelected = isPrioritySortSelected,
                                onSelectionChanged = { viewModel.onEvent(TaskListEvent.ChangeSortByPriority(it))})
                            TaskChip(
                                name = stringResource(id = R.string.deadline),
                                isSelected = isDeadlineSortSelected,
                                onSelectionChanged = { viewModel.onEvent(TaskListEvent.ChangeSortByDeadline(it))})
                        }
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .padding(8.dp), onClick = { navController.navigate(TaskScreens.DetailScreen.name + "/-1") }
                    ) {
                        Icon(Icons.Filled.Add, stringResource(R.string.add_task))
                    }
                }
            }
        }
    }
}


@Composable
fun TaskRow(
    modifier: Modifier = Modifier,
    task: Task,
    onViewTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(task.bgColor)) {
            Column(modifier
                .width(0.dp)
                .weight(1f)
                .clickable { onViewTask(task) }
                .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.Start) {
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold,
                    color = task.contentColor
                )
                Text(
                    text = "Priority ${task.priority.name}",
                    style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.SemiBold,
                    color = task.contentColor
                )
                Text(
                    text = task.status.name,
                    style = MaterialTheme.typography.subtitle2,
                    color = task.contentColor
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange, tint = task.contentColor,
                        contentDescription = stringResource(id = R.string.task_deadline)
                    )
                    Text(
                        text = task.deadline.formatDateToString(),
                        style = MaterialTheme.typography.body1,
                        color = task.contentColor
                    )
                }
            }
            Icon(imageVector = Icons.Default.Delete, tint = task.contentColor,
                contentDescription = stringResource(R.string.delete_task),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onDeleteTask(task) })
        }
    }
}

