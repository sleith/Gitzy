package com.raymond.gitzy.data

import androidx.annotation.Keep

@Keep
class Payload(
  val action: String? = null,
  val issue: Issue? = null,
  val ref: String? = null,
  val refType: String? = null,
  val description: String? = null,
  val pullRequest: PullRequest? = null,
  val comment: Comment? = null,
  val commits: List<Commit> = emptyList(),
  val release: Release? = null,
  val review: Review? = null,
)