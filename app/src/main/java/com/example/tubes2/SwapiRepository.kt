package com.example.tubes2

import android.util.Log
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.*


class SwapiRepository {
    private val swapiService: SwapiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.swapi.tech/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        swapiService = retrofit.create(SwapiService::class.java)
    }

    fun getPeopleDetailsAsync(id: String): Deferred<Response<SwapiResponsePeople>> =
        CoroutineScope(Dispatchers.IO).async {
            swapiService.getDetailsPeople("people", id).execute()
        }

    fun getPlanetsDetailsAsync(id: String): Deferred<Response<SwapiResponsePlanets>> =
        CoroutineScope(Dispatchers.IO).async {
            swapiService.getDetailsPlanets("planets", id).execute()
        }

    fun getStarshipsDetailsAsync(id: String): Deferred<Response<SwapiResponseStarships>> =
        CoroutineScope(Dispatchers.IO).async {
            swapiService.getDetailsStarships("starships", id).execute()
        }

//    fun getPeopleDetails(id: String, callback: Callback<SwapiResponsePeople>) {
//        swapiService.getDetailsPeople("people", id).enqueue(callback)
//    }
//
//    fun getPlanetsDetails(id: String, callback: Callback<SwapiResponsePlanets>) {
//        return swapiService.getDetailsPlanets("planets", id).enqueue(callback)
//    }
//
//    fun getStarshipsDetails(id: String, callback: Callback<SwapiResponseStarships>) {
//        swapiService.getDetailsStarships("starships", id).enqueue(callback)
//    }
}
