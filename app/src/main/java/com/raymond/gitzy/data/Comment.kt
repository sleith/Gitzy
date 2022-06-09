package com.raymond.gitzy.data

class Comment(
  val id: Long? = null,
  val url: String? = null,
  val htmlUrl: String? = null,
  val issueUrl: String? = null,
  val nodeId: String? = null,
  val user: User? = null,
  val createdAt: String? = null,
  val updatedAt: String? = null,
  val authorAssociation: String? = null,
  val body: String? = null,
  val reactions: Reactions? = null,
  val performedViaGithubApp: String? = null,
)