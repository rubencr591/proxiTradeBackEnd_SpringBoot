package com.rubenSL.proxiTrade.model.dtos

data class UserResponseDTO(
    var uid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var location: LocationDTO? = null,
    var profilePicture: String? = null,
)