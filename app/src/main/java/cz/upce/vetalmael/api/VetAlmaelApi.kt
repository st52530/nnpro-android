package cz.upce.vetalmael.api

import cz.upce.vetalmael.data.model.Animal
import cz.upce.vetalmael.data.model.LoginRequest
import cz.upce.vetalmael.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VetAlmaelApi {

    @POST("user/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @GET("animal/s")
    suspend fun getAnimals(): List<Animal>

    @GET("animal/{id}")
    suspend fun getAnimals(@Path("id") id: Int): Animal
}