package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.entities.Location
import com.rubenSL.proxiTrade.model.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserMapper @Autowired constructor(private val locationMapper: LocationMapper) {



        fun toUserDTO(user: User): UserDTO {
            val profilePictureBase64: String? = user.profilePicture?.base64?.let { blob ->
                val bytes = blob.getBytes(1, blob.length().toInt())
                Base64.getEncoder().encodeToString(bytes)  // Convert bytes to base64
            }

            return UserDTO(
                uid = user.uid,
                name = user.name,
                email = user.email,
                password = user.password,
                address = user.address,
                phone = user.phone,
                location = locationMapper.toLocationDTO(user.location),
                profilePicture = profilePictureBase64
            )
        }



        fun toUserResponseDTO(user: User): UserResponseDTO {
            val profilePictureBase64: String? = user.profilePicture?.base64?.let { blob ->
                val bytes = blob.getBytes(1, blob.length().toInt())
                Base64.getEncoder().encodeToString(bytes)  // Convert bytes to base64
            }
            return UserResponseDTO(
                uid = user.uid,
                name = user.name,
                email = user.email,
                location = locationMapper.toLocationDTO(user.location),
                profilePicture = profilePictureBase64
            )
        }

        fun toUserByUserResponseDTO(userResponseDTO: UserResponseDTO): User {
            return User(
                uid = userResponseDTO.uid!!,
                name = userResponseDTO.name!!,
                email = userResponseDTO.email!!,
                location = userResponseDTO.location?.let { locationMapper.toLocation(it) },

            )
        }

        fun toUser(userDTO: UserDTO): User {
            return User(
                uid = userDTO.uid!!,
                name = userDTO.name!!,
                email = userDTO.email!!,
                password = userDTO.password!!,
                address = userDTO.address ?: "",
                phone = userDTO.phone ?: 0,
                location = userDTO.location?.let { locationMapper.toLocation(it) },
            )
        }

}