package com.raymond.gitzy.di

import com.raymond.gitzy.screens.UserDetailViewModel
import com.raymond.gitzy.screens.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { UserListViewModel(get()) }
  viewModel { (username: String) -> UserDetailViewModel(username, get(), get()) }
}