package com.bhavnathacker.jettasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import com.bhavnathacker.jettasks.ui.navigation.TaskNavigation
import com.bhavnathacker.jettasks.ui.theme.JetTasksTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTasksTheme {
                TaskNavigation()
            }
        }
    }
}
