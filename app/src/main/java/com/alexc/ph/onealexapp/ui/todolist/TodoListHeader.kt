package com.alexc.ph.onealexapp.ui.todolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.constants.DateSubHeaderTextStyle
import com.alexc.ph.onealexapp.ui.constants.MediumDp
import com.alexc.ph.onealexapp.ui.constants.TodayHeaderTextStyle


@Composable
fun ToDoListHeader(
    modifier: Modifier = Modifier,
    dateToday: String,
) {
    Column(
        modifier = modifier.padding(MediumDp)
    ) {
        Text(
            text = stringResource(R.string.today),
            style = TodayHeaderTextStyle,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = dateToday,
            style = DateSubHeaderTextStyle
        )
    }
}

@Preview
@Composable
fun ToDoListHeaderPreview() {
    ToDoListHeader(
        dateToday = "Sept 19 - Thursday",
        modifier = Modifier
    )
}