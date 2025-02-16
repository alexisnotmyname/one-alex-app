package com.alexc.ph.onealexapp.ui.todolist.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun BasicTextField(
    value: String,
    enabled: Boolean = true,
    textDecoration: TextDecoration?,
    textColor: Color,
    onValueChanged: (String) -> Unit,
    onStoppedEditing: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = textColor,
            textDecoration = textDecoration
        ),
        enabled = enabled,
        onValueChange = {
            onValueChanged(it)
        },
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onStoppedEditing()
            }
        ),
        modifier = modifier
    )
}
