package com.raymond.gitzy.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.raymond.gitzy.R
import com.raymond.gitzy.common.ui.BaseScreen
import com.raymond.gitzy.common.ui.OnBottomReached
import com.raymond.gitzy.common.ui.showDialog
import com.raymond.gitzy.data.User
import com.raymond.gitzy.navigation.Screens
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.getViewModel

@Composable
fun UserListScreen(navController: NavController) {
  BaseScreen(
    title = stringResource(id = R.string.app_name),
    onBackClick = null,
    content = { Body(navController = navController, viewModel = getViewModel()) }
  )
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun Body(navController: NavController, viewModel: UserListViewModel) {
  val state by viewModel.state.collectAsState()
  val listState = rememberLazyListState()

  SwipeRefresh(
    state = SwipeRefreshState(state.isRefreshing),
    onRefresh = { viewModel.refresh() },
    indicator = { state, trigger ->
      SwipeRefreshIndicator(
        // Pass the SwipeRefreshState + trigger through
        state = state,
        refreshTriggerDistance = trigger,
        // Enable the scale animation
        scale = true,
        // Change the color and shape
        backgroundColor = MaterialTheme.colors.primary,
        shape = MaterialTheme.shapes.small,
      )
    }
  ) {
    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
      items(items = state.users) { user ->
        UserRow(
          user = user,
          onClick = { navController.navigate(Screens.UserDetailScreen.route + "/${it.login}") }
        )
        Divider(color = Color.Gray)
      }
    }
    state.errorMessage?.handle()?.let { showDialog(it, viewModel::recompose) }
    listState.OnBottomReached { viewModel.loadMore() }
  }
}

@Composable
private fun UserRow(user: User, onClick: (User) -> Unit) {
  Column(
    Modifier
      .fillMaxSize()
      .clickable(enabled = true, onClick = { onClick(user) })
  ) {
    Row(
      modifier = Modifier.padding(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      GlideImage(
        modifier = Modifier
          .width(50.dp)
          .height(50.dp)
          .clip(CircleShape),
        imageModel = user.avatarUrl.orEmpty(),
        contentScale = ContentScale.Crop,
      )
      Spacer(modifier = Modifier.width(10.dp))
      Text(user.login.orEmpty())
    }
  }
}