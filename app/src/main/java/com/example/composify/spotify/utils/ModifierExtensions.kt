package com.example.composify.spotify.utils

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.HorizontalGradient
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.VerticalGradient

fun Modifier.horizontalGradientBackground(colors: List<Color>): Modifier {
  return gradientBackground(colors) { gradientColors, size ->
    HorizontalGradient(
        colors = gradientColors,
        startX = 0f,
        endX = size.width
    )
  }
}

fun Modifier.verticalGradientBackground(colors: List<Color>):Modifier {
  return gradientBackground(colors) { gradientColors, size ->
    VerticalGradient(
        colors = gradientColors,
        startY = 0f,
        endY = size.width
    )
  }
}

fun Modifier.gradientBackground(
    colors: List<Color>,
    brushProvider: (List<Color>, Size) -> LinearGradient
): Modifier = composed {
  var size by remember { mutableStateOf(Size.Zero) }
  val gradient = remember(colors, size) { brushProvider(colors, size) }
  drawWithContent {
    size = this.size
    drawRect(brush = gradient)
    drawContent()
  }
}
