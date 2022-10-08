package com.bhavnathacker.jettasks.presentation.components

import android.app.DatePickerDialog
import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bhavnathacker.jettasks.R
import com.bhavnathacker.jettasks.util.formatDateToString
import java.util.*

@Composable
fun TaskDatePicker(
    selectedDate: Date,
    testTag: String = "",
    onDateSelected: (Date) -> Unit
) {
    val activity = LocalContext.current as ComponentActivity

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .border(0.5.dp, MaterialTheme.colors.onBackground.copy(alpha = 0.5f))
            .clickable {
                showDatePicker(activity, onDateSelected)
            }.testTag(testTag)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val (label, iconView) = createRefs()

            Text(
                text = selectedDate.formatDateToString(),
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
    onDateSelected: (Date) -> Unit
) {

    val calendar = Calendar.getInstance()

    val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        onDateSelected(calendar.time)
    }

    DatePickerDialog(
        activity,
        listener,
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    ).show()
}


