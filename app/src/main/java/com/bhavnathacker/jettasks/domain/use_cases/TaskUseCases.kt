package com.bhavnathacker.jettasks.domain.use_cases

data class TaskUseCases(
    val getTask: GetTask,
    val getTasks: GetTasks,
    val saveTask: SaveTask,
    val deleteTask: DeleteTask
)