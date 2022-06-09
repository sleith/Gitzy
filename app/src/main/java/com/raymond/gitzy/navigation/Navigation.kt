package com.raymond.gitzy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raymond.gitzy.screens.UserDetailScreen
import com.raymond.gitzy.screens.UserListScreen

@Composable
fun Navigation() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = Screens.UserListScreen.route) {
    composable(route = Screens.UserListScreen.route) {
      UserListScreen(navController = navController)
    }
    composable(
      route = Screens.UserDetailScreen.route + "/{username}",
      arguments = listOf(navArgument(name = "username") { type = NavType.StringType })
    ) { entry ->
      UserDetailScreen(navController, entry.arguments?.getString("username").orEmpty())
    }
  }
}