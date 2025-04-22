package com.alexc.ph.onealexapp.ui.todolist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.components.BasicTextField
import com.alexc.ph.onealexapp.ui.components.GenericTopAppBar
import com.alexc.ph.onealexapp.ui.components.OneAlexAppIcons.Back
import com.alexc.ph.onealexapp.ui.todolist.TodoListAction

@Composable
fun NewTaskScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    todoItem: TodoItem? = null,
    navigateBack: () -> Unit,
    onSaveClick: (String, Long?) -> Unit
) {
    var input by remember { mutableStateOf(todoItem?.title ?: "") }
    var selectedDate by remember { mutableStateOf(todoItem?.dateTimeDue) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            GenericTopAppBar(
                title = title,
                navigation = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = stringResource(R.string.cd_back)
                        )
                    }
                },
                action = {
                    IconButton(onClick = { onSaveClick(input, selectedDate) }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                        )
                    }
                }
            )

            BasicTextField(
                value = input,
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_new_item),
                        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary),
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textColor = MaterialTheme.colorScheme.primary,
                textStyle = MaterialTheme.typography.bodyMedium,
                textDecoration = null,
                onValueChanged = {
                    input = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                DateAssistChip(
                    date = selectedDate,
                    onDateSelected = {
                        selectedDate = it
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {
                onSaveClick(input, selectedDate)
            },
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewTaskScreenPreview() {
    NewTaskScreen(
        todoItem = TodoItem(),
        title = stringResource(R.string.add_new_item),
        navigateBack = { },
        onSaveClick = {_, _ ->}
    )
}
