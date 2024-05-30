package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.model.dtos.ProductDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.entities.Image
import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.model.mappers.ProductMapper
import com.rubenSL.proxiTrade.repositories.ProductRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ProductService {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var categoryService: CategoryService

    @Autowired
    private lateinit var userService: UserService

    @Lazy
    @Autowired
    private lateinit var productMapper: ProductMapper


    fun getProductById(id: Long): Product {
        return  productRepository.findById(id).orElseThrow { RuntimeException("Product not found with id: $id") }

    }

    fun getProductDTOById(id: Long): ProductDTO {
        return productRepository.findById(id).map { productMapper.toProductDTO(it) }.orElseThrow { RuntimeException("Product not found with id: $id") }
    }
    fun getProductByUser(uid: String): List<ProductDTO> {
        return productRepository.findByProductOwnerUid(uid).map { productMapper.toProductDTO(it) }
    }
    fun getProductNotFromUser(uid: String): List<ProductDTO>? {
        // Si no existe este usuario, lanzar una excepción
        userService.getUserById(uid)

        val productsNotFromUser = productRepository.findByProductOwnerUidNot(uid)
        return if (productsNotFromUser.isNotEmpty()) {
            productsNotFromUser.map { product ->
                val dto = productMapper.toProductDTO(product)
                // Solo incluir la primera imagen
                if (dto.images?.isNotEmpty() == true) {
                    dto.images = listOf(dto.images!!.first())
                }
                dto
            }
        } else {
            null
        }
    }

    fun createProduct(productDTO: ProductDTO): ProductDTO {
        // Separar la cadena "data/jpge, BASE64" en cada ImageDTO
        productDTO.images?.forEach { imageDTO ->
            imageDTO.base64 = imageDTO.base64?.substringAfter(",")
        }

        // Convertir el DTO a la entidad sin guardarla aún
        val newProduct = productMapper.toProduct(productDTO)

        // Asociar las imágenes con el producto
        newProduct.images.forEach { it.product = newProduct }

        // Guardar el producto con las imágenes asociadas
        return productRepository.save(newProduct).let { productMapper.toProductDTO(it) }
    }

    fun updateProduct(id: Long, product: Product): Product {
        if (!productRepository.existsById(id)) {
            throw RuntimeException("Product not found with id: $id")
        }
        return productRepository.save(product)
    }

    fun deleteProduct(id: Long) {
        if (!productRepository.existsById(id)) {
            throw RuntimeException("Product not found with id: $id")
        }
        productRepository.deleteById(id)
    }
}
