package com.example.composify.lazycolumnsample.ui

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.composify.lazycolumnsample.models.EarningsItem
import com.example.composify.lazycolumnsample.repositories.EarningsRepository
import com.example.composify.lazycolumnsample.EarningItemSource
import kotlinx.coroutines.flow.Flow

class EarningsViewModel(private val earningsRepository: EarningsRepository) : ViewModel() {
  val earnings: Flow<PagingData<EarningsItem>> = Pager(PagingConfig(pageSize = 5)) {
    EarningItemSource(earningsRepository)
  }.flow
}