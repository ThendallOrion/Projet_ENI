package fr.agesfarouches.abysslarp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface API_Service {
    @GET("gn")
    suspend fun getGnList(): Response<List<GnListItem>>
    @GET("gn/{id}")
    suspend fun getGnDetail(@Path("id") id: Int): Response<GnDetail>
}