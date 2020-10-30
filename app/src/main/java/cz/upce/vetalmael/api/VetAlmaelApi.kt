package cz.upce.vetalmael.api

import cz.upce.vetalmael.data.model.Animal
import cz.upce.vetalmael.data.model.LoginRequest
import cz.upce.vetalmael.data.model.LoginResponse
import cz.upce.vetalmael.data.model.Reservation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VetAlmaelApi {

    @POST("users/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @GET("/clients/animals")
    suspend fun getAnimals(): List<Animal>

//    @GET("animals/{id}")
//    suspend fun getAnimal(@Path("id") id: Int): Animal

    @GET("/clients/reservations")
    suspend fun getReservations(): List<Reservation>

//    @GET("reservation/{id}")
//    suspend fun getReservation(@Path("id") id: Int): Reservation
}