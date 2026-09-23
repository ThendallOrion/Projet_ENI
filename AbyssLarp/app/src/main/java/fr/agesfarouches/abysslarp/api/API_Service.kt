package fr.agesfarouches.abysslarp.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API_Service {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @GET("gn")
    suspend fun getGnList(): Response<List<GnListItem>>
    @GET("gn/{id}")
    suspend fun getGnDetail(@Path("id") id: Int): Response<GnDetail>

    @GET("nfc/{uid}")
    suspend fun getNfcInfo(@Path("uid") uid: String): Response<NfcLookupResponse>

    @POST("nfc/associate")
    suspend fun associateNfc(@Body body: NfcAssociateRequest): Response<NfcLookupResponse>
}