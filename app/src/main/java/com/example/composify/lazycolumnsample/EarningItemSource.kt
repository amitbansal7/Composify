package com.example.composify.lazycolumnsample

import androidx.paging.PagingSource
import com.example.composify.lazycolumnsample.models.EarningsItem
import com.example.composify.lazycolumnsample.repositories.EarningsRepository

class EarningItemSource(private val earningsRepository: EarningsRepository) :
  PagingSource<Int, EarningsItem>() {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EarningsItem> {
    return try {
      val nextPage = params.key ?: 1
      if (nextPage == -1) {
        return LoadResult.Page(
          data = listOf(),
          prevKey = null,
          nextKey = null
        )
      }
      val earningsResponse = earningsRepository.getEarnings(nextPage)

      val earnings: List<EarningsItem> = earningsResponse.body()!!
      LoadResult.Page(
        data = earnings,
        prevKey = if (nextPage == 1) null else nextPage - 1,
        nextKey = if (earnings.isNotEmpty()) nextPage + 1 else -1
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

}