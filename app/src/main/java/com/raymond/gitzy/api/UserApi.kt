package com.raymond.gitzy.api

import com.raymond.gitzy.data.User
import com.raymond.gitzy.data.UserDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
  @GET("users")
  fun getUsers(@Query("since") since: Long? = null): Single<List<User>>

  @GET("users/{username}")
  fun getUserDetail(@Path("username") username: String): Single<UserDetail>
}