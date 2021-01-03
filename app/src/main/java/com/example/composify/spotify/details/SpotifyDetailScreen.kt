package com.example.composify.spotify.details

import androidx.compose.animation.animate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composify.spotify.data.Album
import com.example.composify.spotify.data.DataProvider
import com.example.composify.spotify.ui.green700
import com.example.composify.spotify.ui.typography
import com.example.composify.spotify.utils.generateDominantColorState
import com.example.composify.spotify.utils.horizontalGradientBackground
import com.example.composify.spotify.utils.verticalGradientBackground
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SpotifyDetailScreen(album: Album) {
  val album = remember { album }
  val scrollState = rememberScrollState(0f)
  val image = imageResource(id = album.imageId).asAndroidBitmap()
  val swatch = remember(album.id) { generateDominantColorState(image) }
  val dominantColors = listOf(Color(swatch.rgb), MaterialTheme.colors.surface)
  val dominantGradient = remember { dominantColors }
  val surfaceGradient = DataProvider.spotifySurfaceGradient(isSystemInDarkTheme()).asReversed()

  Box(modifier = Modifier.fillMaxSize().verticalGradientBackground(dominantGradient)) {
    BoxTopSection(album = album, scrollState = scrollState)
    TopSectionOverlay(scrollState = scrollState)
    BottomScrollableContent(scrollState = scrollState, surfaceGradient = surfaceGradient)
    AnimatedToolBar(album, scrollState, surfaceGradient)
  }

}

@Composable
fun AnimatedToolBar(album: Album, scrollState: ScrollState, surfaceGradient: List<Color>) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
      .horizontalGradientBackground(
        if (Dp(scrollState.value) < 1080.dp)
          listOf(Color.Transparent, Color.Transparent) else surfaceGradient
      )
      .padding(horizontal = 8.dp, vertical = 8.dp)
  ) {
    Icon(imageVector = Icons.Default.ArrowBack, tint = MaterialTheme.colors.onSurface)
    Text(
      text = album.song,
      color = MaterialTheme.colors.onSurface,
      modifier = Modifier.padding(16.dp)
        .alpha(((scrollState.value + 0.001f) / 1000).coerceIn(0f, 1f))
    )
    Icon(imageVector = Icons.Default.MoreVert, tint = MaterialTheme.colors.onSurface)
  }
}

@Composable
fun BottomScrollableContent(scrollState: ScrollState, surfaceGradient: List<Color>) {
  ScrollableColumn(scrollState = scrollState, modifier = Modifier) {
    Spacer(modifier = Modifier.height(480.dp))
    Column(modifier = Modifier.horizontalGradientBackground(surfaceGradient)) {
      SongListScrollingSection()
    }
    Spacer(modifier = Modifier.height(50.dp))
  }
}

@Composable
fun SongListScrollingSection() {
  ShuffleButton()
  DownloadRow()
  val items = remember { DataProvider.albums.shuffled() }
  items.forEach {
    SongListItem(album = it)
  }
}

@Composable
fun DownloadRow() {
  Row(
    modifier = Modifier.fillMaxWidth().padding(16.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = "Download",
      style = typography.h6.copy(fontSize = 14.sp),
      color = MaterialTheme.colors.onSurface
    )
    var switched by remember { mutableStateOf(true) }
    Switch(
      checked = switched,
      onCheckedChange = { switched = it },
      modifier = Modifier.padding(8.dp),
      colors = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colors.primary
      )
    )
  }
}

@Composable
fun ShuffleButton() {
  Button(
    onClick = { },
    colors = ButtonDefaults.buttonColors(backgroundColor = green700),
    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 100.dp)
      .clip(CircleShape)
  ) {
    Text(
      text = "SHUFFLE PLAY",
      style = typography.h6.copy(fontSize = 14.sp),
      color = MaterialTheme.colors.onSurface,
      modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    )
  }
}

@Composable
fun TopSectionOverlay(scrollState: ScrollState) {
  val dynamicAlpha = ((scrollState.value + 0.00f) / 1000).coerceIn(0f, 1f)
  Box(
    modifier = Modifier.fillMaxWidth()
      .height(400.dp)
      .background(
        MaterialTheme.colors.surface.copy(
          alpha = animate(dynamicAlpha)
        )
      )
  )
}

@Composable
fun BoxTopSection(album: Album, scrollState: ScrollState) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Spacer(modifier = Modifier.height(100.dp).fillMaxWidth())

    val dynamicValue =
      if (250.dp - Dp(scrollState.value / 50) < 10.dp) 10.dp
      else 250.dp - Dp(scrollState.value / 20)
    val animatedImageSize = animate(dynamicValue)
    Image(
      bitmap = imageResource(id = album.imageId),
      modifier = Modifier.preferredSize(animatedImageSize)
        .padding(8.dp)
    )
    Text(
      text = album.song,
      style = typography.h5.copy(fontWeight = FontWeight.ExtraBold),
      modifier = Modifier.padding(8.dp),
      color = MaterialTheme.colors.onSurface
    )
    Text(
      text = "FOLLOW",
      color = MaterialTheme.colors.onSurface,
      style = typography.h6.copy(fontSize = 12.sp),
      modifier = Modifier.padding(4.dp)
        .border(
          border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant),
          shape = RoundedCornerShape(12.dp)
        )
        .padding(vertical = 4.dp, horizontal = 24.dp)
    )
    Text(
      text = album.descriptions,
      style = typography.subtitle2,
      modifier = Modifier.padding(4.dp)
    )
  }
}
