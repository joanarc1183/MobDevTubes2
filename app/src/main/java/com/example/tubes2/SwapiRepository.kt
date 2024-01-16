package com.example.tubes2

import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SwapiRepository {

    private val swapiService: SwapiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.swapi.tech/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        swapiService = retrofit.create(SwapiService::class.java)
    }

    fun getPeopleDetails(id: String, callback: Callback<SwapiResponse>) {
        swapiService.getDetails("people", id).enqueue(callback)
    }

    // Tambahkan metode lain sesuai kebutuhan Anda
}
