package com.bhavnathacker.jettasks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.ui.screens.TaskDetail
import com.bhavnathacker.jettasks.ui.screens.TaskList

@ExperimentalComposeUiApi
@Composable
fun TaskNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
            startDestination = TaskScreens.ListScreen.name) {

        composable(TaskScreens.ListScreen.name) {
            TaskList(navController)
        }

        composable(TaskScreens.DetailScreen.name + "/{taskId}",
                arguments = listOf(navArgument(name = "taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                })) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")

            TaskDetail(navController, taskId)
        }
    }
}