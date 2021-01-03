package com.example.composify.locoEarnings.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.composify.locoEarnings.models.AttendanceResponse
import com.example.composify.locoEarnings.models.EarningsItem
import com.example.composify.locoEarnings.repositories.EarningsRepository
import com.amitbansal.earningsapp.util.Resource
import com.example.composify.locoEarnings.EarningItemSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class EarningsViewModel(private val earningsRepository: EarningsRepository) : ViewModel() {
  val earnings: Flow<PagingData<EarningsItem>> = Pager(PagingConfig(pageSize = 5)) {
    EarningItemSource(earningsRepository)
  }.flow
}