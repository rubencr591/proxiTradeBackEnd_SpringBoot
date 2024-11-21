package com.rubenSL.proxiTrade.model.dtos

data class TransactionDTO(
    var id: Long?,
    var userSellerUid: String?,
    var userBuyerUid: String? = null,
    var productId: Long?,
    var amount:Double?,
    var type: String?
)