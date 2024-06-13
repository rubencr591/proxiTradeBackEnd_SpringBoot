package com.rubenSL.proxiTrade.model.dtos

data class ProductDTO(
    var id: Long?,
    var name: String?,
    var description: String?,
    var category: Long?,
    val categoryName: String?,
    var salePrice: Double?,
    var rentedPrice: Double?,
    var latitude: Float?,
    var longitude: Float?,
    var availability: Boolean?,
    var productOwnerId: String?,
    var images: List<ImageDTO>?
)
