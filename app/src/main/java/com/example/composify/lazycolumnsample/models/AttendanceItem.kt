package com.example.composify.lazycolumnsample.models

data class AttendanceItem(
    val date: Int,
    val driver_id: Int,
    val id: Int?,
    val reason: String?,
    val status: String?
)