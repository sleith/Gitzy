package com.raymond.gitzy.common.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BaseScreen(
  title: String?,
  onBackClick: (() -> Unit)?,
  content: @Composable (PaddingValues) -> Unit,
) {
  Scaffold(
    backgroundColor = Color.White,
    topBar = { CommonAppBar(title = title, onBackClick = onBackClick) },
    content = content,
  )
}