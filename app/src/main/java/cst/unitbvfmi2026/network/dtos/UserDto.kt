package cst.unitbvfmi2026.network.dtos

import com.google.gson.annotations.SerializedName
import cst.unitbvfmi2026.data.entities.UserEntity

data class UserDto(
    val id: Long,
    val email: String,
    @SerializedName("first_name") // numele din conventia cu serverul difera fata de ce avem aici => punem adnotarea SerializedName
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val avatar: String
)

fun UserDto.toEntity(addressId: Long?) = UserEntity(
    id = this.id,
    name = "${this.firstName} ${this.lastName}",
    email = this.email,
    avatar = this.avatar,
    addressId = addressId
)

data class UserResponseDto(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val data: List<UserDto>
)
//"page": 1,
//"per_page": 6,
//"total": 12,
//"total_pages": 2,
//"data": [
//{
//    "id": 1,
//    "email": "george.bluth@reqres.in",
//    "first_name": "George",
//    "last_name": "Bluth",
//    "avatar": "https://reqres.in/img/faces/1-image.jpg"
//},