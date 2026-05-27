package cst.unitbvfmi2026.network.apis

import cst.unitbvfmi2026.network.dtos.LoginRequestDto
import cst.unitbvfmi2026.network.dtos.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequestDto): LoginResponseDto
}