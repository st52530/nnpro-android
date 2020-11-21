package cz.upce.vetalmael.api

import cz.upce.vetalmael.data.model.*
import cz.upce.vetalmael.data.model.dto.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface VetAlmaelApi {

    @POST("users/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @GET("clients/animals")
    suspend fun getAnimals(): List<Animal>

    @GET("animals/{id}")
    suspend fun getAnimal(@Path("id") id: Int): Animal

    @POST("animals/user/{idUser}")
    suspend fun addAnimal(
        @Path("idUser") idUser: Int,
        @Body body: AddAnimalRequest
    ): Animal

    @DELETE("animals/{id}")
    suspend fun deleteAnimal(@Path("id") id: Int): Response<Unit>

    @GET("animals/{id}/messages")
    suspend fun getAnimalMessages(@Path("id") id: Int): List<Message>

    @GET("animals/{id}/reports")
    suspend fun getAnimalReports(@Path("id") id: Int): List<Report>

    @GET("clients/reservations")
    suspend fun getReservations(): List<Reservation>

//    @GET("reservation/{id}")
//    suspend fun getReservation(@Path("id") id: Int): Reservation

    @POST("reservations/clinic/{idClinic}/client/{idClient}")
    suspend fun createReservation(
        @Path("idClinic") idClinic: Int,
        @Path("idClient") idClient: Int,
        @Body body: CreateReservationRequest
    ): Reservation

    @DELETE("reservations/{id}")
    suspend fun deleteReservation(@Path("id") id: Int): Response<Unit>

    @POST("clients/sign-up")
    suspend fun signUp(@Body body: SignUpBody): User

    @GET("export/{idReport}")
    suspend fun exportReport(@Path("idReport") idReport: Int): ResponseBody

    @GET("clinics")
    suspend fun getClinics(): List<Clinic>
}