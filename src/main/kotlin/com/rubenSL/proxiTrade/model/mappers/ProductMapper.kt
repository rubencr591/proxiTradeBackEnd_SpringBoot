package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.ProductDTO
import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.services.UserService
import org.springframework.stereotype.Component

@Component
class ProductMapper {


    companion object {
        private val userService = UserService()

        fun toProductDTO(product: Product): ProductDTO {
            return ProductDTO(
                id = product.id,
                name = product.name,
                description = product.description,
                category = product.category,
                salePrice = product.salePrice,
                rentedPrice = product.rentedPrice,
                address = product.address,
                availability = product.availability,
                productOwnerId = product.productOwner?.uid,
                images = product.images.map { ImageMapper.toImageDTO(it) }
            )
        }

        fun toProduct(productDTO: ProductDTO): Product {
            return Product(
                id = productDTO.id!!,
                name = productDTO.name!!,
                description = productDTO.description!!,
                category = productDTO.category!!,
                salePrice = productDTO.salePrice!!,
                rentedPrice = productDTO.rentedPrice!!,
                address = productDTO.address!!,
                availability = productDTO.availability!!,
                productOwner = productDTO.productOwnerId?.let { userService.getUserById(it) }
                    ?.let { UserMapper.toUser(it) }
            )
        }
    }
}