package com.example.composify.spotify.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composify.spotify.SpotifyDetailActivity
import com.example.composify.spotify.data.Album
import com.example.composify.spotify.ui.graySurface
import com.example.composify.spotify.ui.typography
import android.graphics.Bitmap
import androidx.palette.graphics.Palette

@Composable
fun SpotifyHomeGridItem(album: Album) {
  Card(
    elevation = 4.dp,
    backgroundColor = graySurface,
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .padding(8.dp)
      .clickable(onClick = {})
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Image(
        bitmap = imageResource(id = album.imageId),
        modifier = Modifier.preferredSize(55.dp),
        contentScale = ContentScale.Crop
      )
      Text(
        text = album.song,
        style = typography.h6.copy(fontSize = 14.sp),
        modifier = Modifier.padding(horizontal = 8.dp)
      )
    }
  }
}

fun generateDominantColorState(bitmap: Bitmap): Palette.Swatch {
  return Palette.Builder(bitmap)
    .resizeBitmapArea(0)
    .maximumColorCount(16)
    .generate()
    .swatches
    .maxBy { swatch -> swatch.population }!!
}

@Composable
fun SpotifyLaneItem(album: Album) {
  val context = AmbientContext.current
  val album = remember { album }
  Column(
    modifier = Modifier.preferredWidth(180.dp).padding(8.dp)
      .clickable(onClick = {
        context.startActivity(SpotifyDetailActivity.newIntent(context, album))
      })

  ) {
    Image(
      bitmap = imageResource(id = album.imageId),
      modifier = Modifier.preferredWidth(180.dp).preferredHeight(160.dp),
      contentScale = ContentScale.Crop
    )
    Text(
      text = "${album.song}: ${album.descriptions}",
      style = typography.body2,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier.padding(vertical = 8.dp)
    )
  }
}