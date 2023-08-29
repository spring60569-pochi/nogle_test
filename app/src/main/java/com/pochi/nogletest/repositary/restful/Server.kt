package com.pochi.nogletest.repositary.restful

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Server {
    private val tag = Server::class.java.name

    private const val URL = "https://api.btse.com"
    private val service: Service

    init {
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service = retrofit.create(Service::class.java)
    }

    suspend fun getMarket(): ArrayList<Map<String, Any>> = withContext(Dispatchers.IO) {
        val response = service.getMarket()
        if (response.isSuccessful) {
            val body = response.body()!!
            return@withContext body.data
        } else {
            throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }
}