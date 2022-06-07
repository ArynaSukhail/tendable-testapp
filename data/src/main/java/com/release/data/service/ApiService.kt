package com.release.data.service

import com.release.data.model.SendRequest
import com.release.data.model.SignInRequest
import com.release.data.model.SignUpRequest
import com.release.data.model.StartResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun signin(
        @Body body: SignInRequest
    )

    @POST("register")
    suspend fun signUp(
        @Body body: SignUpRequest
    )

    @GET("inspections/start")
    suspend fun start(): StartResponse

    @POST("inspections/submit")
    suspend fun send(
        @Body body: SendRequest
    )
}
