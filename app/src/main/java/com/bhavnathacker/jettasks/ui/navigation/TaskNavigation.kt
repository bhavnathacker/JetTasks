package com.bhavnathacker.jettasks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bhavnathacker.jettasks.screen.TaskList
import com.bhavnathacker.jettasks.ui.screens.TaskDetail
import com.bhavnathacker.jettasks.ui.viewmodels.TaskViewModel

@ExperimentalComposeUiApi
@Composable
fun TaskNavigation() {

    val navController = rememberNavController()
    val taskViewModel = hiltViewModel<TaskViewModel>()

    NavHost(navController = navController,
            startDestination = TaskScreens.ListScreen.name) {

        composable(TaskScreens.ListScreen.name) {
            TaskList(taskViewModel,
                    onViewTask = { navController.navigate(TaskScreens.DetailScreen.name + "/${it.id}") },
                    onNewTask = { navController.navigate(TaskScreens.DetailScreen.name + "/-1") },
                    onDeleteTask = { taskViewModel.removeTask(it) })
        }

        composable(TaskScreens.DetailScreen.name + "/{taskId}",
                arguments = listOf(navArgument(name = "taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                })) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")
            TaskDetail(taskViewModel, taskId, onSaveTask = {
                taskViewModel.saveTask(it)
                navController.navigateUp()
            })
        }


    }
}