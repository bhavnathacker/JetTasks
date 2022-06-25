package com.bhavnathacker.jettasks.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.UserPreferences.SortOrder
import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.model.TaskStatus
import com.bhavnathacker.jettasks.domain.use_cases.TaskUseCases
import com.bhavnathacker.jettasks.domain.use_cases.UserPreferenceUseCases
import com.bhavnathacker.jettasks.ui.events.TaskListEvent
import com.bhavnathacker.jettasks.ui.states.TaskUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val userPreferenceUseCases: UserPreferenceUseCases
) : ViewModel() {

    private lateinit var userPreferencesFlow: Flow<UserPreferences>
    private lateinit var tasksFlow: Flow<List<Task>>

    init {
        viewModelScope.launch {
            userPreferencesFlow = userPreferenceUseCases.getUserPreferences()
            tasksFlow = taskUseCases.getTasks()
        }
    }

    // Every time the sort order, the show completed filter or the list of tasks emit,
    // we should recreate the list of tasks
    private val tasksUiModelFlow = combine(
        tasksFlow,
        userPreferencesFlow
    ) { tasks: List<Task>, userPreferences: UserPreferences ->
        return@combine TaskUiModel(
            tasks = filterSortTasks(
                tasks,
                userPreferences.showCompleted,
                userPreferences.sortOrder
            ),
            showCompleted = userPreferences.showCompleted,
            sortOrder = userPreferences.sortOrder
        )
    }

    var tasksUiModelStateFlow = tasksUiModelFlow.stateIn(
        viewModelScope, SharingStarted.Lazily, TaskUiModel(
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
            SortOrder.UNSPECIFIED -> filteredTasks
            SortOrder.NONE -> filteredTasks
            SortOrder.BY_DEADLINE -> filteredTasks.sortedBy { it.deadline }
            SortOrder.BY_PRIORITY -> filteredTasks.sortedBy { it.priority.ordinal }
            SortOrder.BY_DEADLINE_AND_PRIORITY -> filteredTasks.sortedWith(
                compareBy<Task> { it.deadline }.thenBy { it.priority.ordinal }
            )
            // We shouldn't get any other values
            else -> throw UnsupportedOperationException("$sortOrder not supported")
        }
    }

    fun onEvent(event: TaskListEvent) {
        when (event) {
            is TaskListEvent.DeleteTask ->
                viewModelScope.launch { taskUseCases.deleteTask(event.task) }
            is TaskListEvent.ShowCompletedTasks -> viewModelScope.launch {
                userPreferenceUseCases.updateShowCompleted(event.show)
            }
            is TaskListEvent.ChangeSortByDeadline -> viewModelScope.launch {
                userPreferenceUseCases.enableSortByDeadline(event.enable)
            }
            is TaskListEvent.ChangeSortByPriority -> viewModelScope.launch {
                userPreferenceUseCases.enableSortByPriority(event.enable)
            }
        }
    }


}