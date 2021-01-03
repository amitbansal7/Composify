package com.example.composify.spotify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.example.composify.spotify.data.Album
import com.example.composify.spotify.details.SpotifyDetailScreen
//import androidx.compose.ui.tooling.preview.Preview
import com.example.composify.spotify.ui.ComposifyTheme

class SpotifyDetailActivity : AppCompatActivity() {

  private val album: Album by lazy {
    intent.getSerializableExtra(ALBUM) as Album
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ComposifyTheme {
        SpotifyDetailScreen(album = album)
      }
    }
  }

  companion object {
    const val ALBUM = "album"
    fun newIntent(context: Context, album: Album) =
      Intent(context, SpotifyDetailActivity::class.java).apply {
        putExtra(ALBUM, album)
      }
  }
}
