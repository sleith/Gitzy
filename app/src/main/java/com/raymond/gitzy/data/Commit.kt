package com.raymond.gitzy.data

import androidx.annotation.Keep

@Keep
class Commit(
  val sha: String? = null,
  val message: String? = null,
  val url: String? = null,
)