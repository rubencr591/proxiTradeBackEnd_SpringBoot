package com.rubenSL.proxiTrade.model.dtos

data class LocationDTO(
    var id: Long?,
    var street: String,
    var postalCode:String,
    var numberLetter: String,
    var country: String,
    var city: String,
    var community: String,
    var province: String,
    var latitude: Float,
    var longitude: Float
)