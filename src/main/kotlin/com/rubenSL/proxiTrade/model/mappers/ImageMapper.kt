package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.ImageDTO
import com.rubenSL.proxiTrade.model.entities.Image
import com.rubenSL.proxiTrade.services.ProductService

class ImageMapper {

    companion object{
        val productService = ProductService();
        fun toImageDTO(image: Image): ImageDTO {
            return ImageDTO(
                id = image.id,
                base64 = image.base64,
                productId = image.product?.id
            )
        }

        fun toImage(imageDTO: ImageDTO): Image {
            return Image(
                id = imageDTO.id!!,
                base64 = imageDTO.base64!!,
                product = imageDTO.productId?.let { productService.getProductById(it) }
            )
        }
    }
}