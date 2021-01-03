package com.example.composify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.composify.locoEarnings.ui.EarningsActivity
import com.example.composify.spotify.SpotifyActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    startActivity(Intent(this, EarningsActivity::class.java))
  }
}