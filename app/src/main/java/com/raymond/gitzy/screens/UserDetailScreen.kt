package com.raymond.gitzy.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.raymond.gitzy.R
import com.raymond.gitzy.common.ui.BaseScreen
import com.raymond.gitzy.common.ui.OnBottomReached
import com.raymond.gitzy.common.ui.showDialog
import com.raymond.gitzy.data.Event
import com.raymond.gitzy.data.UserDetail
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserDetailScreen(navController: NavController, username: String) {
  BaseScreen(
    title = username,
    onBackClick = { navController.popBackStack() },
    content = { Body(viewModel = getViewModel() { parametersOf(username) }) }
  )
}

@Composable
private fun Body(viewModel: UserDetailViewModel) {
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
    Column(modifier = Modifier.fillMaxSize()) {
      UserRow(state.userDetail)
      if (state.hasNoEvents) {
        Spacer(Modifier.height(20.dp))
        Text(
          text = "No activities ...",
          modifier = Modifier.fillMaxSize(),
          textAlign = TextAlign.Center,
        )
      } else {
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
          items(items = state.events) { event ->
            EventRow(event = event)
            Divider(color = Color.Gray)
          }
        }
      }
    }

    state.errorMessage?.handle()?.let { showDialog(it, viewModel::recompose) }
    listState.OnBottomReached { viewModel.loadMoreEvents() }
  }
}

@Composable
private fun UserRow(user: UserDetail?) {
  val data = user ?: return
  Column(
    Modifier.padding(10.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      GlideImage(
        modifier = Modifier
          .width(100.dp)
          .height(100.dp)
          .clip(CircleShape),
        imageModel = data.avatarUrl.orEmpty(),
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(),
      )
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(data.login.orEmpty(), fontSize = 20.sp)
        Text(data.name.orEmpty())
        Text(data.location.orEmpty())
        Spacer(modifier = Modifier.height(3.dp))
        Row {
          ImageWithText(R.drawable.ic_repo, data.publicRepos.toString())
          Spacer(modifier = Modifier.width(12.dp))
          ImageWithText(R.drawable.ic_gist, data.publicGists.toString())
          Spacer(modifier = Modifier.width(12.dp))
          ImageWithText(R.drawable.ic_follower, data.followers.toString())
          Spacer(modifier = Modifier.width(12.dp))
          ImageWithText(R.drawable.ic_following, data.following.toString())
        }
      }
    }
  }
}

@Composable
private fun ImageWithText(@DrawableRes iconResId: Int, text: String) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Image(
      painter = painterResource(iconResId),
      contentScale = ContentScale.Inside,
      contentDescription = "",
      modifier = Modifier
        .width(20.dp)
        .height(20.dp)
        .clip(RoundedCornerShape(12.dp))
    )
    Spacer(modifier = Modifier.width(2.dp))
    Text(text)
  }
}

@Composable
private fun EventRow(event: Event) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(all = 10.dp),
  ) {
    Text(event.repo?.name.orEmpty(), color = Color.Blue)
    ImageWithText(iconResId = R.drawable.ic_org, text = event.org?.login.orEmpty())
    Spacer(modifier = Modifier.height(5.dp))

    when (event.type) {
      "IssueCommentEvent" -> CommentEventRow(event)
      "PushEvent" -> PushEventRow(event)
      "DeleteEvent" -> DeleteEventRow(event)
      "PullRequestEvent" -> PullRequestEventRow(event)
      "WatchEvent" -> WatchEventRow(event)
      "CreateEvent" -> CreateEventRow(event)
      "ReleaseEvent" -> ReleaseEventRow(event)
      "CommitCommentEvent" -> CommitCommentEventRow(event)
      "PullRequestReviewEvent" -> PullRequestReviewEventRow(event)
    }
  }
}

@Composable
private fun CommentEventRow(event: Event) {
  val comment = event.payload?.comment
  if (comment != null) {
    Row {
      GlideImage(
        modifier = Modifier
          .width(30.dp)
          .height(30.dp)
          .clip(CircleShape),
        imageModel = comment.user?.avatarUrl.orEmpty(),
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(),
      )
      Spacer(modifier = Modifier.width(5.dp))
      Text(comment.body.orEmpty(), color = Color.DarkGray)
    }
  }
}

@Composable
private fun CreateEventRow(event: Event) {
  Text(
    "Created ${event.payload?.refType} ${event.payload?.ref}: ${event.payload?.description}",
    color = Color.DarkGray
  )
}


@Composable
private fun CommitCommentEventRow(event: Event) {
  val comment = event.payload?.comment
  if (comment != null) {
    Row {
      GlideImage(
        modifier = Modifier
          .width(30.dp)
          .height(30.dp)
          .clip(CircleShape),
        imageModel = comment.user?.avatarUrl.orEmpty(),
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(),
      )
      Spacer(modifier = Modifier.width(5.dp))
      Text(comment.body.orEmpty(), color = Color.DarkGray)
    }
  }
}

@Composable
private fun ReleaseEventRow(event: Event) {
  Text("${event.payload?.action} ${event.payload?.release?.name}", color = Color.DarkGray)
}

@Composable
private fun PushEventRow(event: Event) {
  Text("Pushed ${event.payload?.commits?.size} commits", color = Color.DarkGray)
}

@Composable
private fun DeleteEventRow(event: Event) {
  Text("Deleted ${event.payload?.refType}: ${event.payload?.ref}", color = Color.DarkGray)
}

@Composable
private fun PullRequestEventRow(event: Event) {
  Text("${event.payload?.action}: ${event.payload?.pullRequest?.title}", color = Color.DarkGray)
}

@Composable
private fun PullRequestReviewEventRow(event: Event) {
  val review = event.payload?.review
  if (review != null) {
    Column {
      Text("${event.payload.action}:", color = Color.DarkGray)
      Row {
        GlideImage(
          modifier = Modifier
            .width(30.dp)
            .height(30.dp)
            .clip(CircleShape),
          imageModel = review.user?.avatarUrl.orEmpty(),
          contentScale = ContentScale.Crop,
          circularReveal = CircularReveal(),
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(review.body.orEmpty(), color = Color.DarkGray)
      }
    }
  }
}

@Composable
private fun WatchEventRow(event: Event) {
  Text("${event.payload?.action} to watch", color = Color.DarkGray)
}
