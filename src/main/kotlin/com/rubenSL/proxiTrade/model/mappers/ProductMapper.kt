package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.ProductDTO
import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.services.CategoryService
import com.rubenSL.proxiTrade.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
class ProductMapper @Autowired constructor(
    private val userService: UserService,
    private val categoryService: CategoryService,
    @Lazy
    private val userMapper: UserMapper,
    @Lazy
    private val imageMapper: ImageMapper
) {

    fun toProductDTO(product: Product): ProductDTO {
        return ProductDTO(
            id = product.id,
            name = product.name,
            description = product.description,
            category = product.category?.id,
            categoryName = product.category?.name,
            salePrice = product.salePrice,
            latitude = product.latitude,
            longitude = product.longitude,
            availability = product.availability,
            productOwnerId = product.productOwner?.uid,
            images = product.images.map { imageMapper.toImageDTO(it) }
        )
    }

    fun toProduct(productDTO: ProductDTO): Product {
        val category = productDTO.category?.let { categoryService.getCategoryById(it) }

        val user = productDTO.productOwnerId?.let { userService.getUserByUid(it) }
        return Product(
            name = productDTO.name!!,
            description = productDTO.description!!,
            category = category,
            salePrice = productDTO.salePrice!!,
            availability = productDTO.availability!!,
            productOwner = user,
            images = productDTO.images?.map { imageMapper.toImage(it) }?.toMutableList() ?: mutableListOf()
        )
    }
}