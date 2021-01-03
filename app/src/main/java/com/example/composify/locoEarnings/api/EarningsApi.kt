package com.amitbansal.earningsapp.api

import com.example.composify.locoEarnings.models.AttendanceResponse
import com.example.composify.locoEarnings.models.EarningsItem
import com.amitbansal.earningsapp.util.Constants.AUTH_TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface EarningsApi {

    @Headers("Authorization: $AUTH_TOKEN", "User-Agent: locodrive")
    @GET("earnings")
    suspend fun getEarnings(
        @Query("page")
        page: Int = 1
    ): Response<List<EarningsItem>>

    @Headers("Authorization: $AUTH_TOKEN", "User-Agent: locodrive")
    @GET("attendances")
    suspend fun getAttendance(
        @Query("cycle_id")
        cycleId: Int
    ):Response<AttendanceResponse>
}