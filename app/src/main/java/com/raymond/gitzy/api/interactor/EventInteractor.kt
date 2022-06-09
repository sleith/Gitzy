package com.raymond.gitzy.api.interactor

import com.raymond.gitzy.api.EventApi
import com.raymond.gitzy.data.Event
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class EventInteractor(private val eventApi: EventApi) {
  fun getEventList(username: String, page: Int = 1): Single<List<Event>> {
    return eventApi.getEvents(username, page)
      .subscribeOn(Schedulers.io())
  }
}