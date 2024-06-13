package com.rubenSL.proxiTrade.model.dtos

data class UserDTO(
    var uid: String? = null,
    var name: String?,
    var email: String?,
    var password: String?,
    var phone: Long? = 0,
    var location: LocationDTO? = null,
    var profilePicture : String? = null,
    var kmRatio: Double? = 50.0,
)