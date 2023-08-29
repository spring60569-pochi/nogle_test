package com.pochi.nogletest.repositary.restful

import retrofit2.Response
import retrofit2.http.GET

interface Service {

    data class GetResponse(
        val code: Int,
        val msg: String,
        val time: Long,
        val data: ArrayList<Map<String, Any>>,
        val success: Boolean,
    )
    @GET("/futures/api/inquire/initial/market")
    suspend fun getMarket(): Response<GetResponse>
}