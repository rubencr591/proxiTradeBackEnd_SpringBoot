package com.rubenSL.proxiTrade.controllers
import com.rubenSL.proxiTrade.model.entities.Location
import com.rubenSL.proxiTrade.services.LocationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/locations")
class LocationController(private val locationService: LocationService) {

    @GetMapping("/{id}")
    fun getLocationById(@PathVariable id: Long): Location {
        return locationService.getLocationById(id)
    }

    @PostMapping
    fun createLocation(@RequestBody location: Location): Location {
        return locationService.createLocation(location)
    }

    @PutMapping("/{id}")
    fun updateLocation(@PathVariable id: Long, @RequestBody location: Location): Location {
        return locationService.updateLocation(id, location)
    }

    @DeleteMapping("/{id}")
    fun deleteLocation(@PathVariable id: Long) {
        locationService.deleteLocation(id)
    }
}
