package com.bhavnathacker.jettasks.ui.screens

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bhavnathacker.jettasks.MainActivity
import com.bhavnathacker.jettasks.di.AppModule
import com.bhavnathacker.jettasks.ui.navigation.TaskScreens
import com.bhavnathacker.jettasks.ui.theme.JetTasksTheme
import com.bhavnathacker.jettasks.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class TaskListTest {

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
                NavHost(
                    navController = navController,
                    startDestination = TaskScreens.ListScreen.name) {
                    composable(route = TaskScreens.ListScreen.name) {
                        TaskList(navController = navController)
                    }
                }
            }

        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun toggleShowCompletedSwitch() {
        composeRule.onNodeWithTag(TestTags.SHOW_COMPLETED_SWITCH).assertIsOff()
        composeRule.onNodeWithTag(TestTags.SHOW_COMPLETED_SWITCH).performClick()
        composeRule.onNodeWithTag(TestTags.SHOW_COMPLETED_SWITCH).assertIsOn()
    }

}