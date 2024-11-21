package com.rubenSL.proxiTrade.controllers


import org.junit.jupiter.api.Assertions.*
import com.rubenSL.proxiTrade.model.dtos.ProductDTO
import com.rubenSL.proxiTrade.model.entities.Category
import com.rubenSL.proxiTrade.security.FirebaseService
import com.rubenSL.proxiTrade.services.CategoryService
import com.rubenSL.proxiTrade.services.ProductService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.ResponseEntity
class ProductControllerTest {

    private val productService: ProductService = Mockito.mock(ProductService::class.java)
    private val categoryService: CategoryService = Mockito.mock(CategoryService::class.java)
    private val firebaseService: FirebaseService = Mockito.mock(FirebaseService::class.java)
    private val productController = ProductController(productService, categoryService, firebaseService)

    @Test
    fun getProductById() {
        val id = 1L
        val expectedProduct = ProductDTO(1L, "Product Name", "Product Description", 1L, "Category Name", 100.0, 1.0f, 1.0f, true, "uid", emptyList())
        Mockito.`when`(productService.getProductDTOById(id)).thenReturn(expectedProduct)

        val actualProduct = productController.getProductById(id)

        assertEquals(expectedProduct, actualProduct)
    }

    @Test
    fun getProductsNotFromUser() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val expectedProducts = listOf(ProductDTO(1L, "Product Name", "Product Description", 1L, "Category Name", 100.0, 1.0f, 1.0f, true, uid, emptyList()))
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(productService.getProductNotFromUser(uid)).thenReturn(expectedProducts)

        val actualProducts = productController.getProductsNotFromUser(idToken)

        assertEquals(expectedProducts, actualProducts)
    }


    @Test
    fun getNearbyProducts() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val expectedProducts = listOf(ProductDTO(1L, "Product Name", "Product Description", 1L, "Category Name", 100.0, 1.0f, 1.0f, true, uid, emptyList()))
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(productService.getNearbyProducts(uid)).thenReturn(expectedProducts)

        val actualProducts = productController.getNearbyProducts(idToken)

        assertEquals(expectedProducts, actualProducts)
    }

    @Test
    fun getCategories() {
        val expectedCategories = listOf(Category(1L, "Category Name"))
        Mockito.`when`(categoryService.getAllCategories()).thenReturn(expectedCategories)

        val actualCategories = productController.getCategories()

        assertEquals(expectedCategories, actualCategories)
    }
    @Test
    fun createProduct() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val productDTO = ProductDTO(1L, "Product Name", "Product Description", 1L, "Category Name", 100.0, 1.0f, 1.0f, true, uid, emptyList())
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(productService.createProduct(productDTO, uid)).thenReturn(productDTO)

        val actualProduct = productController.createProduct(idToken, productDTO)

        assertEquals(productDTO, actualProduct)
    }

    @Test
    fun updateProduct() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val productDTO = ProductDTO(1L, "Product Name", "Product Description", 1L, "Category Name", 100.0, 1.0f, 1.0f, true, uid, emptyList())
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(productService.updateProduct(uid, productDTO)).thenReturn(productDTO)

        val actualProduct = productController.updateProduct(idToken, productDTO)

        assertEquals(productDTO, actualProduct)
    }

    @Test
    fun updateProductAvailability() {
        val idToken = "Bearer testToken"
        val id = 1L
        val productDTO = ProductDTO(1L, "Product Name", "Product Description", 1L, "Category Name", 100.0, 1.0f, 1.0f, true, "uid", emptyList())
        Mockito.`when`(productService.updateProductAvailability(id)).thenReturn(productDTO)

        val actualProduct = productController.updateProductAvailability(idToken, id)

        assertEquals(ResponseEntity.ok(productDTO), actualProduct)
    }

    @Test
    fun deleteProduct() {
        val idToken = "Bearer testToken"
        val id = 1L
        val uid = "testUid"
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.doNothing().`when`(productService).deleteProduct(uid, id)

        productController.deleteProduct(idToken, id)

        Mockito.verify(productService, Mockito.times(1)).deleteProduct(uid, id)
    }
}