package com.raymond.gitzy.data

import androidx.annotation.Keep

@Keep
class PullRequest(
  val url: String? = null,
  val title: String? = null,
  val htmlUrl: String? = null,
  val diffUrl: String? = null,
  val patchUrl: String? = null,
  val mergedAt: String? = null
)