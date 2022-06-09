package com.raymond.gitzy.data

import androidx.annotation.Keep

@Keep
class Repo(
  val id: Long? = null,
  val name: String? = null,
  val url: String? = null
)