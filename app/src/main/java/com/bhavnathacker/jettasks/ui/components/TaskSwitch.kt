package com.bhavnathacker.jettasks.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


@Composable
fun TaskSwitch(text: String, isChecked: Boolean, testTag: String = "", onCheckChanged: (Boolean) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {

        val (label, switch) = createRefs()

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(label) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(switch.start)
                    width = Dimension.fillToConstraints
                }) {
            Text(text = text)
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckChanged,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary,
                uncheckedThumbColor = MaterialTheme.colors.onBackground.copy(0.5f),
                checkedTrackColor = MaterialTheme.colors.primary.copy(0.5f),
                uncheckedTrackColor = MaterialTheme.colors.primary.copy(0.5f)
            ),
            modifier = Modifier
                .size(36.dp, 36.dp)
                .constrainAs(switch) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }.testTag(testTag)
        )
    }

}

