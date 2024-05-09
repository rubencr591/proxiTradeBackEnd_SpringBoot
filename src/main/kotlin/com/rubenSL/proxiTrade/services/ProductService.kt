package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.repositories.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getProductById(id: Long): Product {
        return productRepository.findById(id).orElseThrow { RuntimeException("Product not found with id: $id") }
    }

    fun createProduct(product: Product): Product {
        return productRepository.save(product)
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
