package cst.unitbvfmi2026.network.dtos

data class LoginRequestDto (
    val email: String,
    val password: String
)
data class LoginResponseDto (
    val token: String?
)