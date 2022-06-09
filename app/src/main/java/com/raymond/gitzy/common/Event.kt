package com.raymond.gitzy.common

import androidx.annotation.MainThread

class Event<out T : Any>(private val content: T) {
  private var isHandled = false

  @MainThread
  fun handle(): T? {
    return if (isHandled) {
      null
    } else {
      isHandled = true
      content
    }
  }
}
