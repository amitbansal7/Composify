package com.example.composify.spotify

import androidx.compose.animation.animate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composify.R
import com.example.composify.spotify.data.DataProvider
import com.example.composify.spotify.ui.graySurface
import com.example.composify.spotify.ui.typography
import com.example.composify.spotify.utils.SpotifyHomeGridItem
import com.example.composify.spotify.utils.SpotifyLaneItem
import com.example.composify.spotify.utils.VerticalGrid
import com.example.composify.spotify.utils.horizontalGradientBackground

@Composable
fun SpotifyHome() {
  val scrollState = rememberScrollState(0f)
  val surfaceGradient = DataProvider.spotifySurfaceGradient(true)
  Box(modifier = Modifier.fillMaxSize()) {
    ScrollableColumn(
        scrollState = scrollState,
        modifier = Modifier.horizontalGradientBackground(surfaceGradient).padding(8.dp)
    ) {
      Spacer(modifier = Modifier.height(20.dp))
      SpotifyTitle("Good Evening")
      HomeGridSection()
      HomeLaneSection()
      Spacer(modifier = Modifier.height(100.dp))
    }
    Icon(
        imageVector = Icons.Outlined.Settings,
        tint = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(start = 12.dp, end = 12.dp, top = 28.dp, bottom = 12.dp)
            .alpha(animate(1f - scrollState.value / 200f))
    )
    PlayerBottomBar(Modifier.align(Alignment.BottomCenter))
  }
}

@Composable
fun HomeLaneSection() {
  val categories = remember { DataProvider.listOfSpotifyHomeLanes }
  categories.forEach { s ->
    SpotifyTitle(title = s)
    SpotifyLane()
  }
}

@Composable
fun SpotifyLane() {
  val itemEven = remember { DataProvider.albums.shuffled() }
  LazyRowFor(items = itemEven) {
    SpotifyLaneItem(album = it)
  }
}

@Composable
fun HomeGridSection() {
  val items = remember { DataProvider.albums }
  VerticalGrid {
    items.take(6).forEach {
      SpotifyHomeGridItem(album = it)
    }
  }
}

@Composable
fun SpotifyTitle(title: String, modifier: Modifier = Modifier) {
  Text(
      text = title,
      style = typography.h5.copy(fontWeight = FontWeight.ExtraBold),
      modifier = modifier.padding(start = 8.dp, end = 4.dp, bottom = 8.dp, top = 16.dp)
  )
}

@Composable
fun PlayerBottomBar(modifier: Modifier) {
  Row(
      modifier = modifier.padding(bottom = 57.dp).fillMaxWidth().background(color = graySurface),
      verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
        bitmap = imageResource(id = R.drawable.eminem),
        modifier = Modifier.preferredSize(65.dp),
        contentScale = ContentScale.Crop
    )
    Text(
        text = "Rap god",
        style = typography.h6.copy(fontSize = 14.sp),
        modifier = Modifier.padding(8.dp).weight(1f)
    )
    Icon(imageVector = Icons.Default.FavoriteBorder, modifier = Modifier.padding(8.dp))
    Icon(imageVector = Icons.Default.PlayArrow, modifier = Modifier.padding(8.dp))
  }
}


@Preview
@Composable
fun PreviewSpotifyHome() {
  SpotifyHome()
}
