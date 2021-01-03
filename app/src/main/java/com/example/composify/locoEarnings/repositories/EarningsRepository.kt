package com.example.composify.locoEarnings.repositories

import com.amitbansal.earningsapp.api.RetrofitInstance
import com.example.composify.locoEarnings.models.AttendanceResponse
import com.example.composify.locoEarnings.models.EarningsItem
import retrofit2.Response

class EarningsRepository {

    suspend fun getEarnings(page: Int):Response<List<EarningsItem>> =
        RetrofitInstance.api.getEarnings(page)

    suspend fun getAttendances(cycleId: Int): Response<AttendanceResponse> =
        RetrofitInstance.api.getAttendance(cycleId)
}