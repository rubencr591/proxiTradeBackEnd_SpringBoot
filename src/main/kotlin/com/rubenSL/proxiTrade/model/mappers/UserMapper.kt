package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.entities.Location
import com.rubenSL.proxiTrade.model.entities.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserMapper {


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
                location = LocationMapper.toLocationDTO(user.location),
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
                location = LocationMapper.toLocationDTO(user.location),
                profilePicture = profilePictureBase64
            )
        }

        fun toUserByUserResponseDTO(userResponseDTO: UserResponseDTO): User {
            return User(
                uid = userResponseDTO.uid!!,
                name = userResponseDTO.name!!,
                email = userResponseDTO.email!!,

                location = if (userResponseDTO.location != null) {
                    userResponseDTO.location?.latitude?.let {
                        userResponseDTO.location?.longitude?.let { it1 ->
                            Location(
                                id = userResponseDTO.location?.id,
                                latitude = it,
                                longitude = it1
                            )
                        }
                    }
                } else {
                    null
                },

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
                location = if (userDTO.location != null) {
                    userDTO.location?.latitude?.let {
                        userDTO.location?.longitude?.let { it1 ->
                            Location(
                                id = userDTO.location?.id,
                                latitude = it,
                                longitude = it1
                            )
                        }
                    }
                } else {
                    null
                }
            )
        }

}