package com.raymond.gitzy.api.interceptor

import android.util.Log
import com.google.gson.Gson
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ErrorInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val response = chain.proceed(request)
    if (response.isSuccessful) return response
    val body = response.body ?: return response

    val bodyJson = body.string()
    val error = Gson().fromJson(bodyJson, Error::class.java)
    Log.e(javaClass.simpleName, error.toString())
    val contentType = body.contentType()
    body.close()
    return response.newBuilder().body(bodyJson.toResponseBody(contentType))
      .message(error.message.orEmpty()).build()
  }
}
