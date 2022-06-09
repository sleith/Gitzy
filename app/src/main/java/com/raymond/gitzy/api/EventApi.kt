package com.raymond.gitzy.api

import com.raymond.gitzy.data.Event
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
  @GET("users/{username}/events/public")
  fun getEvents(
    @Path("username") username: String,
    @Query("page") page: Int = 1,
  ): Single<List<Event>>
}