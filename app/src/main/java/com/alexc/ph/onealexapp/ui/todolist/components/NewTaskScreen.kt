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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.components.GenericTopAppBar
import com.alexc.ph.onealexapp.ui.components.OneAlexAppIcons.Back

@Composable
fun NewTaskScreen(
    value: String = "",
    navigateBack: () -> Unit,
    onAddNewTask: (String, Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var input by remember { mutableStateOf(value) }
    val dateNow = System.currentTimeMillis()
    var selectedDate by remember { mutableLongStateOf(dateNow) }

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
                title = stringResource(R.string.add_new_item),
                navigation = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Back,
                            contentDescription = stringResource(R.string.cd_back)
                        )
                    }
                },
                action = {
                    IconButton(onClick = { onAddNewTask(input, selectedDate) }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                        )
                    }
                }
            )

            BasicTextField(
                value = input,
                placeHolderText = "Enter new task...",
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textColor = MaterialTheme.colorScheme.primary,
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
                    date = dateNow,
                    onDateSelected = {
                        selectedDate = it ?: dateNow
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {
                onAddNewTask(input, selectedDate)
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
        navigateBack = {},
        onAddNewTask = {_, _ ->}
    )
}
