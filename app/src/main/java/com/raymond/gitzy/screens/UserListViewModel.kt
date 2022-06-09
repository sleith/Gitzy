package com.raymond.gitzy.screens

import androidx.lifecycle.ViewModel
import com.raymond.gitzy.api.interactor.UserInteractor
import com.raymond.gitzy.common.Event
import com.raymond.gitzy.common.getMessage
import com.raymond.gitzy.data.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserListViewModel(private val userInteractor: UserInteractor) : ViewModel() {
  private var canLoadMore = false
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
    loadData(isRefresh = true)
  }

  fun refresh() {
    _state.value = _state.value.copy(isRefreshing = true)
    loadData(isRefresh = true)
  }

  fun loadMore() {
    if (!canLoadMore) return
    loadData()
  }

  private fun loadData(isRefresh: Boolean = false) {
    val since = if (isRefresh) null else _state.value.users.lastOrNull()?.id
    userInteractor.getUserList(since)
      .observeOn(AndroidSchedulers.mainThread())
      .doFinally { _state.value = _state.value.copy(isRefreshing = false) }
      .subscribe(
        { newItems ->
          canLoadMore = newItems.isNotEmpty()
          val items = if (isRefresh) newItems else _state.value.users + newItems
          _state.value = _state.value.copy(users = items)
        },
        { _state.value = _state.value.copy(errorMessage = Event(it.getMessage())) }
      )
      .let(compositeDisposable::add)
  }

  fun recompose() {
    _state.value = _state.value.copy(recompose = _state.value.recompose + 1)
  }

  data class State(
    val users: List<User> = emptyList(),
    val errorMessage: Event<String>? = null,
    val recompose: Int = 0,
    val isRefreshing: Boolean = false,
  )
}
