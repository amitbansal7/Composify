package com.example.composify.spotify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Text
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.composify.spotify.ui.AppThemeState
import com.example.composify.spotify.ui.ComposifyTheme
import com.example.composify.spotify.ui.SystemUiController
import com.example.composify.spotify.ui.graySurface


class SpotifyActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val systemUiController = remember { SystemUiController(window) }
      val appTheme = remember { mutableStateOf(AppThemeState()) }
      ComposifyTheme {
        AppContent()
      }
    }
  }


  @Composable
  fun AppContent() {
    val itemState = savedInstanceState { SpotifyNavType.HOME }
    Scaffold(
      bodyContent = { SpotifyBodyContent(itemState.value) },
      bottomBar = { SpotifyBottomNavigation(itemState) }
    )
  }

  @Composable
  fun SpotifyBottomNavigation(navItemState: MutableState<SpotifyNavType>) {
    BottomNavigation(backgroundColor = graySurface) {
      BottomNavigationItem(
        icon = { Icon(imageVector = Icons.Outlined.Home) },
        selected = navItemState.value == SpotifyNavType.HOME,
        onClick = { navItemState.value = SpotifyNavType.HOME },
        label = { androidx.compose.material.Text(text = "Home") },
      )
      BottomNavigationItem(
        icon = { Icon(imageVector = Icons.Outlined.Search) },
        selected = navItemState.value == SpotifyNavType.SEARCH,
        onClick = { navItemState.value = SpotifyNavType.SEARCH },
        label = { androidx.compose.material.Text(text = "Search") },
      )
      BottomNavigationItem(
        icon = { Icon(imageVector = Icons.Outlined.LibraryMusic) },
        selected = navItemState.value == SpotifyNavType.LIBRARY,
        onClick = { navItemState.value = SpotifyNavType.LIBRARY },
        label = { androidx.compose.material.Text(text = "Library") },
      )
    }
  }


  @Composable
  fun SpotifyBodyContent(navType: SpotifyNavType) {
    Crossfade(current = navType) {
      when (it) {
        SpotifyNavType.HOME -> SpotifyHome()
      }
    }
  }
}