package com.alexc.ph.onealexapp.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexc.ph.onealexapp.R

@Composable
fun SettingsDialog(
    onDismiss:() -> Unit,
    onButtonClicked:() -> Unit
) {
    SettingsDialog1(
        onDismiss,
        onButtonClicked
    )
}

@Composable
fun SettingsDialog1(
    onDismiss:() -> Unit,
    onButtonClicked:() -> Unit
) {
    AlertDialog(
        title = {
            Text("Settings")
        },
        text = {
            HorizontalDivider()
            Column {
                Text("This is the settings dialog")
                Button(
                    onClick = { onButtonClicked() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Click me to change color")
                }
            }


        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Text(
                text = stringResource(R.string.ok),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() }
            )
        }
    )
}