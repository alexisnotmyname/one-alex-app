package com.alexc.ph.onealexapp.ui.todolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alexc.ph.onealexapp.ui.constants.SmallDp
import com.alexc.ph.onealexapp.R


@Composable
fun ToDoListHeader(
    dateToday: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.today),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(SmallDp)
        )
        Text(
            text = dateToday,
            modifier = Modifier.padding(SmallDp)
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