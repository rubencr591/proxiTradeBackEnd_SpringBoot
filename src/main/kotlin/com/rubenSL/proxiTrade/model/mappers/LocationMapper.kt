package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.entities.Location
import org.springframework.stereotype.Component

@Component
class LocationMapper {

    companion object {
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
}