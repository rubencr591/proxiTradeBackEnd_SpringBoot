package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.entities.Location
import com.rubenSL.proxiTrade.services.LocationService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class LocationControllerTest {
    private val locationService: LocationService = Mockito.mock(LocationService::class.java)
    private val locationController = LocationController(locationService)

    @Test
    fun getLocationById() {
        val id = 1L
        val expectedLocation = Location(id, 1.0f, 1.0f, "street", "postalCode", "numberLetter", "country", "city", "community", "province")
        Mockito.`when`(locationService.getLocationById(id)).thenReturn(expectedLocation)

        val actualLocation = locationController.getLocationById(id)

        assertEquals(expectedLocation, actualLocation)
    }

    @Test
    fun createLocation() {
        val location = Location(1L, 1.0f, 1.0f, "street", "postalCode", "numberLetter", "country", "city", "community", "province")
        Mockito.`when`(locationService.createLocation(location)).thenReturn(location)

        val actualLocation = locationController.createLocation(location)

        assertEquals(location, actualLocation)
    }

    @Test
    fun updateLocation() {
        val id = 1L
        val location = Location(id, 1.0f, 1.0f, "street", "postalCode", "numberLetter", "country", "city", "community", "province")
        Mockito.`when`(locationService.updateLocation(id, location)).thenReturn(location)

        val actualLocation = locationController.updateLocation(id, location)

        assertEquals(location, actualLocation)
    }

    @Test
    fun deleteLocation() {
        val id = 1L
        Mockito.doNothing().`when`(locationService).deleteLocation(id)

        locationController.deleteLocation(id)

        Mockito.verify(locationService, Mockito.times(1)).deleteLocation(id)
    }
}