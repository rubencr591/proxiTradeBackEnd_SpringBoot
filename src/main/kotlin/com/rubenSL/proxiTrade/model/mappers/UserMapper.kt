package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.entities.Location
import com.rubenSL.proxiTrade.model.entities.User
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toUserDTO(user: User): UserDTO {
        return UserDTO(
            name = user.name,
            email = user.email,
            password = user.password,
            address = user.address,
            phone = user.phone,
            location = toLocationDTO(user.location)
        )
    }


    fun toUserResponseDTO(user: User): UserResponseDTO {
        return UserResponseDTO(
            uid = user.uid,
            name = user.name,
            email = user.email,
            phone = user.phone,
            location = toLocationDTO(user.location)
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

    fun toLocation(locationDTO: LocationDTO): Location {
        return Location(
            id = locationDTO.id,
            latitude = locationDTO.latitude,
            longitude = locationDTO.longitude
        )
    }

    fun toLocationDTO(location: Location?): LocationDTO? {
        return if (location != null) {
            LocationDTO(
                id = location.id,
                latitude = location.latitude,
                longitude = location.longitude
            )
        } else {
            null
        }
    }

}