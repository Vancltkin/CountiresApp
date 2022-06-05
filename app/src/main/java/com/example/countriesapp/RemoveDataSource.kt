package com.example.countriesapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RestCountiesApi {
    @GET("name/{name}")
    suspend fun getCountryByName(@Path("name") cityName: String): List<Country>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://countriesinfo21.herokuapp.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val restCountiesApi = retrofit.create(RestCountiesApi::class.java)