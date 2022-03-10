package com.bhavnathacker.jettasks.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavnathacker.jettasks.data.model.Task
import com.bhavnathacker.jettasks.data.model.TaskStatus
import com.bhavnathacker.jettasks.data.repository.SortOrder
import com.bhavnathacker.jettasks.data.repository.TaskRepository
import com.bhavnathacker.jettasks.data.repository.UserPreferenceRepository
import com.bhavnathacker.jettasks.ui.model.TasksUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val userPreferencesRepository: UserPreferenceRepository
) : ViewModel() {

    private val sortOrderFlow = userPreferencesRepository.sortOrderFlow
    private val showCompletedFlow = MutableStateFlow(false)

    // Every time the sort order, the show completed filter or the list of tasks emit,
    // we should recreate the list of tasks
    private val tasksUiModelFlow = combine(
        taskRepository.getAllTasks(),
        sortOrderFlow,
        showCompletedFlow
    ) { tasks: List<Task>, sortOrder: SortOrder, showCompleted: Boolean ->
        return@combine TasksUiModel(
            tasks = filterSortTasks(
                tasks,
                showCompleted,
                sortOrder
            ),
            showCompleted = showCompleted,
            sortOrder = sortOrder
        )
    }

    var tasksUiModelStateFlow = tasksUiModelFlow.stateIn(
        viewModelScope, SharingStarted.Lazily, TasksUiModel(
            emptyList(), false, SortOrder.NONE
        )
    )

    private fun filterSortTasks(
        tasks: List<Task>,
        showCompleted: Boolean,
        sortOrder: SortOrder
    ): List<Task> {
        // filter the tasks
        val filteredTasks = if (showCompleted) {
            tasks
        } else {
            tasks.filter { it.status == TaskStatus.PENDING }
        }
        // sort the tasks
        return when (sortOrder) {
            SortOrder.NONE -> filteredTasks
            SortOrder.BY_DEADLINE -> filteredTasks.sortedBy { it.deadline }
            SortOrder.BY_PRIORITY -> filteredTasks.sortedBy { it.priority.ordinal }
            SortOrder.BY_DEADLINE_AND_PRIORITY -> filteredTasks.sortedWith(
                compareBy <Task> { it.deadline }.thenBy { it.priority.ordinal }
            )
            // We shouldn't get any other values
            else -> throw UnsupportedOperationException("$sortOrder not supported")
        }
    }

    fun showCompletedTasks(show: Boolean) {
        showCompletedFlow.value = show
    }

    fun onSortByDeadlineChanged(enable: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.enableSortByDeadline(enable)
        }
    }

    fun onSortByPriorityChanged(enable: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.enableSortByPriority(enable)
        }
    }

    fun getTaskById(id: Int): Task? {
        val tasks = tasksUiModelStateFlow.value.tasks

        tasks.iterator().forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    fun saveTask(task: Task) = viewModelScope.launch { taskRepository.saveTask(task) }
    fun removeTask(task: Task) = viewModelScope.launch { taskRepository.deleteTask(task) }

}