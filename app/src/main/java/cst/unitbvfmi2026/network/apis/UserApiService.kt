package cst.unitbvfmi2026.network.apis

import cst.unitbvfmi2026.network.dtos.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("api/users") // defineste ruta
    suspend fun getUsers(@Query("page") page: Int): UserResponseDto //functie suspendata = comunicare async cu serverul
}