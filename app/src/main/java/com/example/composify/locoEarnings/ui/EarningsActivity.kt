package com.example.composify.locoEarnings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.findNavController
import com.example.composify.locoEarnings.models.EarningsItem
import com.example.composify.locoEarnings.models.ListItem
import com.example.composify.locoEarnings.repositories.EarningsRepository
import com.example.composify.spotify.ui.AppThemeState
import com.example.composify.spotify.ui.ComposifyTheme
import com.example.composify.spotify.ui.SystemUiController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.*
import androidx.paging.compose.collectAsLazyPagingItems

class EarningsActivity : AppCompatActivity() {

  lateinit var viewModel: EarningsViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val earningsRepository = EarningsRepository()
    val viewModelProviderFactory = EarningsViewModelProviderFactory(earningsRepository)

    viewModel = viewModelProviderFactory.create(EarningsViewModel::class.java)

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
    val earnings: LazyPagingItems<EarningsItem> = viewModel.earnings.collectAsLazyPagingItems()
    val openedMap = mutableMapOf<Int, MutableState<Boolean>>()

    LazyColumn(
      modifier = Modifier.fillMaxSize().background(Color.White).padding(5.dp)
    ) {
      items(earnings) { item ->
        val showLargeCard: MutableState<Boolean> =
          openedMap[item!!.cycle_id] ?: mutableStateOf(false)
        openedMap[item!!.cycle_id!!] = showLargeCard
        CycleItem(item!!, showLargeCard.value) {
          showLargeCard.value = !showLargeCard.value
        }
      }
    }
  }

  @Composable
  fun CycleItem(earningsItem: EarningsItem, showLargeCard: Boolean, onClickShowMore: () -> Unit) {
    Column(
      modifier = Modifier.padding(5.dp)
    ) {
      Text(
        text = earningsItem.cycle ?: "",
        modifier = Modifier.padding(start = 5.dp),
        fontSize = 15.sp
      )
      Card(
        elevation = 3.dp,
        backgroundColor = Color.White,
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(4.dp)
      ) {
        Column(
          modifier = Modifier.padding(5.dp)
        ) {
          Item(
            earningsItem.net_earnings!!,
            Modifier.fillMaxWidth().padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 7.dp)
          )
          earningsItem.list
            ?.filter { if (showLargeCard) it.lp != null else it.sp != null }
            ?.sortedBy { if (showLargeCard) it.lp else it.sp }
            ?.forEach {
              Item(it, Modifier.fillMaxWidth().padding(5.dp))
            }
          Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(3.dp))
          Surface(
            modifier = Modifier.clickable(onClick = { onClickShowMore() }),
            color = Color.White,
          ) {
            Column(
              modifier = Modifier.fillMaxWidth(),
              horizontalAlignment = Alignment.CenterHorizontally,
            ) {
              Text(
                text = if (showLargeCard) "Show less" else "Show More",
                fontSize = 15.sp,
                color = Color(android.graphics.Color.parseColor("#0000EE")),
                modifier = Modifier.padding(7.dp),
              )
            }
          }
        }
      }
    }
  }

  @Composable
  fun Item(item: ListItem, modifier: Modifier = Modifier) {
    Row(
      modifier = modifier,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      val color = Color(android.graphics.Color.parseColor(item.color!!))
      Text(text = item.label!!, color = color)
      Text(text = item.value!!, color = color)
    }
  }
}