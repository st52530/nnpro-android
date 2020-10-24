package cz.upce.vetalmael.api

import cz.upce.vetalmael.data.model.LoginRequest
import cz.upce.vetalmael.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface VetAlmaelApi {

    @POST("users/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse
}