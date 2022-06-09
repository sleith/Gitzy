package com.raymond.gitzy.screens

import androidx.lifecycle.ViewModel
import com.raymond.gitzy.api.interactor.EventInteractor
import com.raymond.gitzy.api.interactor.UserInteractor
import com.raymond.gitzy.common.getMessage
import com.raymond.gitzy.data.Event
import com.raymond.gitzy.data.UserDetail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserDetailViewModel(
  private val username: String,
  private val userInteractor: UserInteractor,
  private val eventInteractor: EventInteractor,
) : ViewModel() {
  private var canLoadMore = false
  private var currPage = 1
  private val compositeDisposable = CompositeDisposable()
  private val _state = MutableStateFlow(State())
  val state: StateFlow<State> by lazy {
    onViewStartObserving()
    _state
  }

  override fun onCleared() {
    compositeDisposable.clear()
    super.onCleared()
  }

  private fun onViewStartObserving() {
    userInteractor.getUserDetail(username)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { data -> _state.value = _state.value.copy(userDetail = data) },
        {
          _state.value =
            _state.value.copy(errorMessage = com.raymond.gitzy.common.Event(it.getMessage()))
        }
      )
      .let(compositeDisposable::add)

    loadEventsData(isRefresh = true)
  }

  fun refresh() {
    _state.value = _state.value.copy(isRefreshing = true)
    loadEventsData(isRefresh = true)
  }

  fun loadMoreEvents() {
    if (!canLoadMore) return
    loadEventsData()
  }

  private fun loadEventsData(isRefresh: Boolean = false) {
    val page = if (isRefresh) 1 else currPage + 1
    eventInteractor.getEventList(username, page)
      .observeOn(AndroidSchedulers.mainThread())
      .doFinally { _state.value = _state.value.copy(isRefreshing = false) }
      .subscribe(
        { newEvents ->
          canLoadMore = newEvents.isNotEmpty()
          currPage = page
          val items = if (isRefresh) newEvents else _state.value.events + newEvents
          _state.value = _state.value.copy(events = items, hasNoEvents = items.isEmpty())
        },
        {
          _state.value =
            _state.value.copy(errorMessage = com.raymond.gitzy.common.Event(it.getMessage()))
        }
      )
      .let(compositeDisposable::add)
  }

  fun recompose() {
    _state.value = _state.value.copy(recompose = _state.value.recompose + 1)
  }

  data class State(
    val userDetail: UserDetail? = null,
    val events: List<Event> = emptyList(),
    val hasNoEvents: Boolean = false,
    val errorMessage: com.raymond.gitzy.common.Event<String>? = null,
    val recompose: Int = 0,
    val isRefreshing: Boolean = false,
  )
}