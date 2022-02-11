package com.bhavnathacker.jettasks.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskChip(
    name: String,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit,
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.small,
        color = if (isSelected) MaterialTheme.colors.secondary else Color.LightGray
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(it)
                }
            )
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


