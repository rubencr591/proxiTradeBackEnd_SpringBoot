package com.rubenSL.proxiTrade.model.dtos

data class TransactionResponseDTO(
    var userBuyerName: String? = null,
    var userSellerName: String? = null,
    var productName: String?,
    var amount:Double?,
    var type: String?
)