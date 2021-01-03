package com.example.composify.locoEarnings.models

data class EarningsItem(
  val cycle: String?,
  val
  cycle_id: Int?,
  val disputes: Disputes?,
  val earnings_after_tds: String?,
  val list: List<ListItem>?,
  val net_earnings: ListItem?
)