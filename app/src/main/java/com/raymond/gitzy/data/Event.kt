package com.raymond.gitzy.data

class Event(
  val id: String? = null,
  val type: String? = null,
  val actor: Actor? = null,
  val repo: Repo? = null,
  val payload: Payload? = null,
  val public: Boolean? = null,
  val createdAt: String? = null,
  val org: Org? = null,
)