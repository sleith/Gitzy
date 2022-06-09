package com.raymond.gitzy.common.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun CommonAppBar(
  title: String?,
  onBackClick: (() -> Unit)?,
) {
  TopAppBar(
    title = { Text(text = title.orEmpty()) },
    elevation = 2.dp,
    navigationIcon = {
      if(onBackClick!=null) {
        IconButton(onClick = onBackClick) {
          Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
          )
        }
      }
    },
  )
}