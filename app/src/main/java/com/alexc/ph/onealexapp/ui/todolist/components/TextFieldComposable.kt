package com.alexc.ph.onealexapp.ui.todolist.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun BasicTextField(
    value: String,
    placeHolderText: String,
    enabled: Boolean = true,
    textDecoration: TextDecoration?,
    textColor: Color,
    colors: TextFieldColors,
    onValueChanged: (String) -> Unit,
    onStoppedEditing: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        placeholder = {
            Text(placeHolderText)
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = textColor,
            textDecoration = textDecoration
        ),
        enabled = enabled,
        onValueChange = {
            onValueChanged(it)
        },
        colors = colors,
        keyboardActions = KeyboardActions(
            onDone = {
                onStoppedEditing()
            }
        ),
        modifier = modifier
    )
}
