package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.entities.Location
import com.rubenSL.proxiTrade.model.entities.User
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class UserMapper {

    companion object{

        fun toUserDTO(user: User): UserDTO {
            val profilePictureBase64: String? = user.profilePicture?.base64?.let { blob ->
                val bytes = blob.getBytes(1, blob.length().toInt())
                String(bytes, StandardCharsets.UTF_8)
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
            return UserResponseDTO(
                uid = user.uid,
                name = user.name,
                email = user.email,
                phone = user.phone,
                location = LocationMapper.toLocationDTO(user.location)
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

}