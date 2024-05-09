package com.rubenSL.proxiTrade.model.dtos

data class BookingDTO(
    var id: Long?,
    var userId: Long?,
    var productId: Long?,
    var startDate: String?,
    var endDate: String?,
    var status: String?
)