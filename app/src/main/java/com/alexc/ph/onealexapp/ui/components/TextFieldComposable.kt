package com.alexc.ph.onealexapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme

@Composable
fun BasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: @Composable () -> Unit = {},
    enabled: Boolean = true,
    singleLine: Boolean = false,
    textDecoration: TextDecoration?,
    textStyle: TextStyle,
    textColor: Color,
    colors: TextFieldColors,
    onValueChanged: (String) -> Unit = {},
    onStoppedEditing: () -> Unit = {},
) {
    TextField(
        value = value,
        placeholder = placeholder,
        textStyle = textStyle.copy(
            color = textColor,
            textDecoration = textDecoration
        ),
        enabled = enabled,
        singleLine = singleLine,
        onValueChange = {
            onValueChanged(it)
        },
        colors = colors,
        keyboardActions = KeyboardActions(
            onDone = {
                onStoppedEditing()
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences
        ),
        modifier = modifier
    )
}

@Composable
fun SearchTextField(
    modifier: Modifier,
    value: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit,
) {
    TextField(
        modifier = modifier
            .minimumInteractiveComponentSize(),
        value = value,
        onValueChange = onSearchQueryChange,
        textStyle = MaterialTheme.typography.labelMedium,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        ),
        maxLines = 1,
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.typography.labelMedium
            )
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeSearch()
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        leadingIcon = {
            Icon(
                imageVector = OneAlexAppIcons.Search,
                contentDescription = stringResource( R.string.search),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotBlank()
            ) {
                IconButton(onClick = {
                        onSearchQueryChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.clear_search),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun BasicTextFieldPreview() {
    BasicTextField(
        value = "",
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
        onValueChanged = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        modifier = Modifier,
        value = "",
        onSearchQueryChange = {},
        onImeSearch = {}
    )
}
