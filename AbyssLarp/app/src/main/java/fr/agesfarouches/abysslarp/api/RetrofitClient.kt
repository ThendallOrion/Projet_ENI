package fr.agesfarouches.abysslarp.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    val api: API_Service by lazy {
        retrofit.create(API_Service::class.java)
    }

}