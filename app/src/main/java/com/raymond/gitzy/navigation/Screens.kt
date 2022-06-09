package com.raymond.gitzy.navigation

sealed class Screens(val route: String) {
  object UserListScreen : Screens("userListScreen")
  object UserDetailScreen : Screens("userDetailScreen")
}
