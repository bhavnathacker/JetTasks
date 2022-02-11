package com.bhavnathacker.jettasks.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


@Composable
fun TaskSwitch(text: String, isChecked: Boolean, onCheckChanged: (Boolean) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {

        val (label, switch) = createRefs()

        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(label) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(switch.start)
                    width = Dimension.fillToConstraints
                }
        )

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckChanged,
            modifier = Modifier
                .size(36.dp, 36.dp)
                .constrainAs(switch) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }

}

