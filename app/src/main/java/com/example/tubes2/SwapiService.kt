package com.example.tubes2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SwapiService {
    @GET("{category}/{id}")
    fun getDetails(@Path("category") category: String, @Path("id") id: String): Call<SwapiResponse>
}
