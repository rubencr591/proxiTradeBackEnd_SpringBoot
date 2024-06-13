package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.entities.Location
import org.springframework.stereotype.Component

@Component
class LocationMapper {

        fun toLocation(locationDTO: LocationDTO): Location {
            return Location(
                id = locationDTO.id,
                latitude = locationDTO.latitude,
                longitude = locationDTO.longitude,
                street = locationDTO.street,
                postalCode = locationDTO.postalCode,
                numberLetter = locationDTO.numberLetter,
                country = locationDTO.country,
                city = locationDTO.city,
                community = locationDTO.community,
                province = locationDTO.province
            )
        }

        fun toLocationDTO(location: Location?): LocationDTO? {
            return if (location != null) {
                LocationDTO(
                    id = location.id,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    street = location.street,
                    postalCode = location.postalCode,
                    numberLetter = location.numberLetter,
                    country = location.country,
                    city = location.city,
                    community = location.community,
                    province = location.province
                )
            } else {
                null
            }
        }
}