package com.rubenSL.proxiTrade.model.dtos

data class UserResponseDTO(
    var uid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: Int? = null,
    var location: LocationDTO? = null
)