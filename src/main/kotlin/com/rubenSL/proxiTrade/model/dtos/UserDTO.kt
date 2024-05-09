package com.rubenSL.proxiTrade.model.dtos

data class UserDTO(
    var uid: String? = null,
    var name: String?,
    var email: String?,
    var password: String?,
    var address: String? = null,
    var phone: Int? = 0,
    var location: LocationDTO? = null
)