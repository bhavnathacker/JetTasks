package com.bhavnathacker.jettasks.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavnathacker.jettasks.data.model.Task
import com.bhavnathacker.jettasks.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    private val _taskListFlow = MutableStateFlow<List<Task>>(emptyList())
    val taskListFlow = _taskListFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTasks().distinctUntilChanged()
                    .collect { tasks ->
                        if (tasks.isNullOrEmpty()) {
                            _taskListFlow.value = emptyList()
                        } else {
                            _taskListFlow.value = tasks
                        }
                    }

        }
    }

    fun getTaskById(id: Int): Task? {
        val tasks = _taskListFlow.value

        tasks.iterator().forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    fun saveTask(task: Task) = viewModelScope.launch { repository.saveTask(task) }
    fun removeTask(task: Task) = viewModelScope.launch { repository.deleteTask(task) }

}