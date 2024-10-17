package com.alexc.ph.onealexapp.ui.search

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.onealexapp.ui.components.OneAlexAppIcons
import com.alexc.ph.onealexapp.R.string as searchR
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme

@Composable
fun SearchTextField(
    modifier: Modifier,
    @StringRes searchPlaceHolder: Int = searchR.search,
    onSearchQueryChanged: (String) -> Unit,
) {
    TextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                imageVector = OneAlexAppIcons.Search,
                contentDescription = stringResource(
                    id = searchR.search,
                ),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        shape = RoundedCornerShape(32.dp),
        value = "",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        maxLines = 1,
        singleLine = true,
        onValueChange =  {
            if ("\n" !in it) onSearchQueryChanged(it)
        },
        placeholder = {
            Text(stringResource(id = searchPlaceHolder))
        }
    )
}

@Composable
@Preview
fun SearchToolBarPreview() {
    OneAlexAppTheme {
        SearchTextField(
            modifier = Modifier,
            searchPlaceHolder = searchR.search,
            onSearchQueryChanged = {}
        )
    }
}
