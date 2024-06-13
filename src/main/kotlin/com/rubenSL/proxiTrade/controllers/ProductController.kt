package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.dtos.ProductDTO
import com.rubenSL.proxiTrade.model.entities.Category
import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.security.FirebaseService
import com.rubenSL.proxiTrade.services.CategoryService
import com.rubenSL.proxiTrade.services.ProductService
import org.springframework.http.ResponseEntity
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
    fun getProductsByUser(@RequestHeader("Authorization") idToken: String): List<ProductDTO>? {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        return productService.getProductByUser(uid)
    }

    @GetMapping("/someProducts")
    fun getProductsNotFromUser(@RequestHeader("Authorization") idToken: String): List<ProductDTO>? {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        return productService.getProductNotFromUser(uid)
    }

    @GetMapping("/nearby")
    fun getNearbyProducts(@RequestHeader("Authorization") idToken: String): List<ProductDTO>? {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        return productService.getNearbyProducts(uid)
    }


    @GetMapping("/categories")
    fun getCategories(): List<Category> {
        return categoryService.getAllCategories()
    }

    @PostMapping("/create")
    fun createProduct(@RequestHeader("Authorization") idToken: String,@RequestBody productDTO: ProductDTO): ProductDTO {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        return productService.createProduct(productDTO, uid)
    }

    @PutMapping("/update")
    fun updateProduct(@RequestHeader("Authorization")idToken: String, @RequestBody product: ProductDTO): ProductDTO {
        val token = idToken.substring(7)
        val uid = firebaseService.getUidFromToken(token)
        firebaseService.verifyToken(token)

        return productService.updateProduct(uid,product)
    }


    @PutMapping("/available/{id}")
    fun updateProductAvailability(@RequestHeader("Authorization") idToken: String, @PathVariable id: Long): ResponseEntity<ProductDTO> {
        val token = idToken.substring(7)
        val uid = firebaseService.getUidFromToken(token)
        firebaseService.verifyToken(token)

        return ResponseEntity.ok(productService.updateProductAvailability(id))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@RequestHeader("Authorization") idToken: String ,@PathVariable id: Long) {
        val token = idToken.substring(7)
        val uid = firebaseService.getUidFromToken(token)
        firebaseService.verifyToken(token)
        productService.deleteProduct(uid,id)
    }
}
