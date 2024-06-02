package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.dtos.ProductDTO
import com.rubenSL.proxiTrade.model.entities.Category
import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.security.FirebaseService
import com.rubenSL.proxiTrade.services.CategoryService
import com.rubenSL.proxiTrade.services.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService,
                        private val categoryService: CategoryService,
                        private val firebaseService: FirebaseService) {

    @GetMapping("/product/{id}")
    fun getProductById(@PathVariable id: Long): ProductDTO {
        return productService.getProductDTOById(id)
    }

    @GetMapping("/myProducts")
    fun getProductsByUser(@RequestHeader("Authorization") idToken: String): List<ProductDTO> {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        return productService.getProductByUser(uid)
    }

    @GetMapping("/someProducts")
    fun getProductsNotFromUser(@RequestHeader("Authorization") idToken: String): List<ProductDTO>? {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        return productService.getProductNotFromUser(uid)
    }

    @GetMapping("/categories")
    fun getCategories(): List<Category> {
        return categoryService.getAllCategories()
    }

    @PostMapping("/create")
    fun createProduct(@RequestHeader("Authorization") idToken: String,@RequestBody productDTO: ProductDTO): ProductDTO {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        productDTO.productOwner = uid
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
