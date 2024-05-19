package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.dtos.ProductDTO
import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.services.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): Product {
        return productService.getProductById(id)
    }

    @PostMapping("/post")
    fun createProduct(@RequestBody productDTO: ProductDTO): ProductDTO {
        return productService.createProduct(productDTO)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): Product {
        return productService.updateProduct(id, product)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) {
        productService.deleteProduct(id)
    }
}
