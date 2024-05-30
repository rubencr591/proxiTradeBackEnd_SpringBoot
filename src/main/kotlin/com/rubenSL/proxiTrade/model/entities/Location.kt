package com.rubenSL.proxiTrade.model.entities

import jakarta.persistence.*
import lombok.Data

import kotlin.math.*

@Entity
@Data
@Table(name = "locations")
data class Location(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var latitude: Float = 0f,
    var longitude: Float = 0f,
    var street: String = "",
    var postalCode: String = "",
    var numberLetter: String = "",
    var country: String = "",
    var city: String = "",
    var province: String = ""

) {
    fun calculateDistance(location: Location): Float {
        val lat1 = this.latitude
        val lon1 = this.longitude
        val lat2 = location.latitude
        val lon2 = location.longitude

        val theta = lon1 - lon2
        var dist = (sin(Math.toRadians(lat1.toDouble())) * sin(Math.toRadians(lat2.toDouble())) +
                cos(Math.toRadians(lat1.toDouble())) * cos(Math.toRadians(lat2.toDouble())) *
                cos(Math.toRadians(theta.toDouble())))
        dist = acos(dist)
        dist = Math.toDegrees(dist)
        dist *= 60 * 1.1515f
        dist *= 1.609344f

        return dist.toFloat()
    }
}
