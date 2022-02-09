package com.bhavnathacker.jettasks.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.data.model.Task
import com.bhavnathacker.jettasks.ui.viewmodels.TaskViewModel
import com.bhavnathacker.jettasks.util.*

@ExperimentalComposeUiApi
@Composable
fun TaskList(
        taskViewModel: TaskViewModel,
        onNewTask: () -> Unit,
        onViewTask: (Task) -> Unit,
        onDeleteTask: (Task) -> Unit) {

    val tasks = taskViewModel.taskListFlow.collectAsState().value

    Column {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        }, backgroundColor = MaterialTheme.colors.primary)


        ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)) {
            val (list, fab, noTasksText) = createRefs()

            LazyColumn(modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                items(tasks) { task ->
                    TaskRow(task = task,
                            onViewTask = { onViewTask(it) },
                            onDeleteTask = { onDeleteTask(it) })
                }
            }

            if (tasks.isEmpty()) {
                Text(modifier = Modifier
                        .constrainAs(noTasksText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }, text = stringResource(R.string.msg_no_tasks), style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
            }

            FloatingActionButton(modifier = Modifier.constrainAs(fab) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }, onClick = { onNewTask() }) {
                Icon(Icons.Filled.Add, stringResource(R.string.add_task))
            }
        }
    }

}

@Composable
fun TaskRow(
        modifier: Modifier = Modifier,
        task: Task,
        onViewTask: (Task) -> Unit,
        onDeleteTask: (Task) -> Unit) {
    Surface(
            modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(2.dp)),
            border = BorderStroke(1.dp, color = Color.LightGray),
            elevation = 4.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier
                    .width(0.dp)
                    .weight(1f)
                    .clickable { onViewTask(task) }
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.Start) {
                Text(text = task.name,
                        style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                Text(text = formatDate(task.deadline),
                        style = MaterialTheme.typography.caption)
                Text(text = stringResource(task.statusString),
                        style = MaterialTheme.typography.caption, color = colorResource(task.statusColor))
                Text(text = task.priority,
                        style = MaterialTheme.typography.caption, fontWeight = FontWeight.SemiBold,
                color = colorResource(task.priorityColor))

            }

            Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete_task), modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onDeleteTask(task) })

        }
    }
}

