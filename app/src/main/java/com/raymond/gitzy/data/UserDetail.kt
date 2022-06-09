package com.raymond.gitzy.data

import androidx.annotation.Keep

@Keep
class UserDetail(
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
  val name: String?,
  val company: String?,
  val blog: String?,
  val location: String?,
  val email: String?,
  val hireable: Boolean?,
  val bio: String?,
  val twitterUsername: String?,
  val publicRepos: Int?,
  val publicGists: Int?,
  val followers: Int?,
  val following: Int?,
  val createdAt: String?,
  val updatedAt: String?,
)