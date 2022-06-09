package com.raymond.gitzy.data

import androidx.annotation.Keep

@Keep
class Release(
  val id: Long? = null,
  val name: String? = null,
  val url: String? = null
)