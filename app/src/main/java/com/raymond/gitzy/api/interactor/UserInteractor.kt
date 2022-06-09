package com.raymond.gitzy.api.interactor

import com.raymond.gitzy.api.UserApi
import com.raymond.gitzy.data.User
import com.raymond.gitzy.data.UserDetail
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserInteractor(private val userApi: UserApi) {
  fun getUserList(since: Long? = null): Single<List<User>> {
    return userApi.getUsers(since)
      .subscribeOn(Schedulers.io())
  }

  fun getUserDetail(username: String): Single<UserDetail> {
    return userApi.getUserDetail(username)
      .subscribeOn(Schedulers.io())
  }
}