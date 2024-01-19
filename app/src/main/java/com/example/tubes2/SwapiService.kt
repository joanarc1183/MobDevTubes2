package com.example.tubes2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SwapiService {
    @GET("{category}/{id}")
    fun getDetailsPeople(@Path("category") category: String, @Path("id") id: String): Call<SwapiResponsePeople>

    @GET("{category}/{id}")
    fun getDetailsPlanets(@Path("category") category: String, @Path("id") id: String): Call<SwapiResponsePlanets>

    @GET("{category}/{id}")
    fun getDetailsStarships(@Path("category") category: String, @Path("id") id: String): Call<SwapiResponseStarships>

    @GET("{category}/{id}")
    fun getDetailsFilms(@Path("category") category: String, @Path("id") id: String): Call<SwapiResponseFilms>

    @GET("{category}")
    fun getLength(@Path("category") category: String): Call<SwapiResponseLength>
}
