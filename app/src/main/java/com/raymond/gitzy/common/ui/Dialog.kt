package com.raymond.gitzy.common.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun showDialog(message: String, onDismiss: () -> Unit) {
  AlertDialog(
    onDismissRequest = { },
    confirmButton = {},
    dismissButton = {
      TextButton(onClick = onDismiss)
      { Text(text = "OK") }
    },
    text = { Text(text = message) }
  )
}