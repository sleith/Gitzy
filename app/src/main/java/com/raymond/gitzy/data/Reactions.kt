package com.raymond.gitzy.data

import androidx.annotation.Keep

@Keep
class Reactions(
  val url: String? = null,
  val totalCount: Int? = null,
  val laugh: Int? = null,
  val hooray: Int? = null,
  val confused: Int? = null,
  val heart: Int? = null,
  val rocket: Int? = null,
  val eyes: Int? = null
)