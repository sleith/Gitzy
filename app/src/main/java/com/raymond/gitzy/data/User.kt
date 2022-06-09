package com.raymond.gitzy.data

import androidx.annotation.Keep

@Keep
class User(
  val login: String?,
  val id: Long?,
  val nodeId: String?,
  val avatarUrl: String?,
  val gravatarId: String?,
  val url: String?,
  val htmlUrl: String?,
  val followersUrl: String?,
  val followingUrl: String?,
  val gistsUrl: String?,
  val starredUrl: String?,
  val subscriptionsUrl: String?,
  val organizationUrl: String?,
  val reposUrl: String?,
  val eventsUrl: String?,
  val receivedEventsUrl: String?,
  val type: String?,
  val siteAdmin: Boolean?,
)