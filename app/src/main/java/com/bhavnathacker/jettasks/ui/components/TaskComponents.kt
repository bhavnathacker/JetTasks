package com.bhavnathacker.jettasks.ui.components

import android.app.DatePickerDialog
import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.util.formatDate
import java.util.*


@ExperimentalComposeUiApi
@Composable
fun TaskInputText(
        modifier: Modifier = Modifier,
        text: String,
        label: String,
        maxLine: Int = 1,
        onTextChange: (String) -> Unit,
        onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent),
            maxLines = maxLine,
            label = { Text(text = label) },
            keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onImeAction()
                keyboardController?.hide()

            }),
            modifier = modifier
    )

}

@Composable
fun TaskButton(
        modifier: Modifier = Modifier,
        text: String,
        onClick: () -> Unit,
        enabled: Boolean = true
) {
    Button(onClick = onClick,
            shape = CircleShape,
            enabled = enabled,
            modifier = modifier) {
        Text(text)
    }

}


@Composable
fun TaskDatePicker(selectedDate: Date,
                   onDateSelected: (Long) -> Unit) {
    val activity = LocalContext.current as ComponentActivity

    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
                    .clickable {
                        showDatePicker(activity, onDateSelected)
                    }
    ) {

        ConstraintLayout(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
        ) {

            val (label, iconView) = createRefs()

            Text(
                    text = formatDate(selectedDate),
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(label) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(iconView.start)
                                width = Dimension.fillToConstraints
                            }
            )

            Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.date_picker),
                    modifier = Modifier
                            .size(20.dp, 20.dp)
                            .constrainAs(iconView) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                    tint = MaterialTheme.colors.onSurface
            )

        }

    }
}


private fun showDatePicker(
        activity: ComponentActivity,
        onDateSelected: (Long) -> Unit) {

    val calendar = Calendar.getInstance()

    val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        calendar[Calendar.MONTH] = month
        calendar[Calendar.YEAR] = year
        onDateSelected(calendar.timeInMillis)
    }

    DatePickerDialog(
            activity,
            listener,
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
    ).show()
}


@Composable
fun TaskMenu(
        menuItems: List<String>,
        menuExpandedState: Boolean,
        selectedIndex: Int,
        updateMenuExpandStatus: () -> Unit,
        onDismissMenuView: () -> Unit,
        onMenuItemClick: (Int) -> Unit,
) {
    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
                    .clickable(
                            onClick = {
                                updateMenuExpandStatus()
                            },
                    ),

            ) {

        ConstraintLayout(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
        ) {

            val (label, iconView) = createRefs()

            Text(
                    text = menuItems[selectedIndex],
                    modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(label) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(iconView.start)
                                width = Dimension.fillToConstraints
                            }
            )

            val displayIcon = painterResource(id = R.drawable.ic_drop_down)

            Icon(
                    painter = displayIcon,
                    contentDescription = stringResource(R.string.menu_icon),
                    modifier = Modifier
                            .size(20.dp, 20.dp)
                            .constrainAs(iconView) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                    tint = MaterialTheme.colors.onSurface
            )

            DropdownMenu(
                    expanded = menuExpandedState,
                    onDismissRequest = { onDismissMenuView() },
                    modifier = Modifier
                            .fillMaxWidth()
            ) {
                menuItems.forEachIndexed { index, title ->
                    DropdownMenuItem(
                            onClick = {
                                onMenuItemClick(index)
                            }) {
                        Text(text = title)
                    }
                }
            }
        }
    }
}


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


