package com.alexc.ph.onealexapp.ui.todolist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.constants.LargeDp
import com.alexc.ph.onealexapp.ui.constants.MediumDp
import com.alexc.ph.onealexapp.ui.constants.TodoInputBarFabSize
import com.alexc.ph.onealexapp.ui.constants.TodoInputBarHeight
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme


@Composable
fun TodoInputBar(
    modifier: Modifier = Modifier,
    onAddButtonClick: (String) -> Unit = {}
) {
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(size = 4.dp),
        modifier = modifier
            .padding(MediumDp)
            .height(TodoInputBarHeight)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = LargeDp)
    ){
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.labelMedium,
                value = input,
                onValueChange = { newText ->
                    input = newText
                    enableAddButton = input.isNotEmpty()
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.todo_hint),
                        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary),
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            val fabColor = MaterialTheme.colorScheme.onPrimary
            val iconTint = MaterialTheme.colorScheme.primary
            val addFabColor = if (enableAddButton) fabColor else fabColor.copy(alpha = 0.5f)
            val addIconTint = if (enableAddButton) iconTint else iconTint.copy(alpha = 0.5f)
            FloatingActionButton(
                containerColor = addFabColor,
                onClick = {
                    if (enableAddButton) {
                        onAddButtonClick(input)
                        input = ""
                    } else {
                        return@FloatingActionButton
                    }
                },
                shape = CircleShape,
                modifier = Modifier.size(TodoInputBarFabSize),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = addIconTint
                )
            }
            Spacer(modifier = Modifier.width(MediumDp))
        }
    }
}


@Preview
@Composable
fun TodoInputBarPreview() {
    OneAlexAppTheme  {
        TodoInputBar()
    }
}