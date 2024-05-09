package com.rubenSL.proxiTrade.model.dtos

data class RentDTO(
    var id: Long?,
    var userId: Long?,
    var productId: Long?,
    var startDate: String?,
    var endDate: String?
)