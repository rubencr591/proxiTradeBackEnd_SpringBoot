package com.rubenSL.proxiTrade.model.dtos

data class TransactionDTO(
    var id: Long?,
    var userSellerId: Long?,
    var userBuyerId: Long?,
    var productId: Long?,
    var date: String?,
    var type: String?
)