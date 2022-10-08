package com.bhavnathacker.jettasks.presentation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bhavnathacker.jettasks.MainActivity
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.presentation.navigation.TaskScreens
import com.bhavnathacker.jettasks.presentation.screens.TaskDetail
import com.bhavnathacker.jettasks.presentation.screens.TaskList
import com.bhavnathacker.jettasks.presentation.theme.JetTasksTheme
import com.bhavnathacker.jettasks.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class TaskEndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @OptIn(ExperimentalComposeUiApi::class)
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @OptIn(ExperimentalComposeUiApi::class)
    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.setContent {
            JetTasksTheme {
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

        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun addNewTask_editLater() {
        // Click on FAB to get to add task screen
        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.add_task)).performClick()

        // Enter texts in task name and click save
        composeRule.onNodeWithTag(TestTags.TASK_NAME).performTextInput("Test Task")
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.save)).performClick()

        // Make sure there is a task in the list
        composeRule.onNodeWithText("Test Task").assertIsDisplayed()
        composeRule.onNodeWithText("Test Task").performClick()

        // Add the text "Updated" to the task name
        composeRule
            .onNodeWithTag(TestTags.TASK_NAME)
            .performTextInput(" Updated")
        // Update the note
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.save)).performClick()

        // Make sure the task name was updated
        composeRule.onNodeWithText("Test Task Updated").assertIsDisplayed()
    }

}