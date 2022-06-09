package com.raymond.gitzy.common

import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response

fun Throwable.getMessage(): String {
  return runCatching {
    Gson().fromJson(
      (this as? HttpException)?.response()?.cloneErrorBody().orEmpty(),
      com.raymond.gitzy.data.Error::class.java
    )
  }
    .getOrNull()?.let { error ->
      error.message.orEmpty()
    } ?: "Unknown error"
}

private fun Response<*>.cloneErrorBody(): String? {
  val errorBody = this.errorBody() ?: return null
  val source = errorBody.source()
  source.request(Long.MAX_VALUE)
  val buffer = source.buffer
  return buffer.clone().readUtf8()
}
