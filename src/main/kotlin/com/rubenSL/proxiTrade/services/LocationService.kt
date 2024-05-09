package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.model.entities.Location
import com.rubenSL.proxiTrade.repositories.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(private val locationRepository: LocationRepository) {

    fun getLocationById(id: Long): Location {
        return locationRepository.findById(id).orElseThrow { RuntimeException("Location not found with id: $id") }
    }

    fun createLocation(location: Location): Location {
        return locationRepository.save(location)
    }

    fun updateLocation(id: Long, location: Location): Location {
        if (!locationRepository.existsById(id)) {
            throw RuntimeException("Location not found with id: $id")
        }
        return locationRepository.save(location)
    }

    fun deleteLocation(id: Long) {
        if (!locationRepository.existsById(id)) {
            throw RuntimeException("Location not found with id: $id")
        }
        locationRepository.deleteById(id)
    }
}
