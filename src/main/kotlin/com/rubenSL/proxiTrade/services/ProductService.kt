package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.model.dtos.ProductDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.entities.Image
import com.rubenSL.proxiTrade.model.entities.Location
import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.model.mappers.ImageMapper
import com.rubenSL.proxiTrade.model.mappers.ProductMapper
import com.rubenSL.proxiTrade.repositories.ImageRepository
import com.rubenSL.proxiTrade.repositories.ProductRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

    @Lazy
    @Autowired
    private lateinit var imageMapper: ImageMapper

    @Autowired
    private lateinit var imageRepository: ImageRepository


    fun getProductById(id: Long): Product {
        return  productRepository.findById(id).orElseThrow { RuntimeException("Product not found with id: $id") }

    }

    fun getProductDTOById(id: Long): ProductDTO {
        //La latitud y la longitud del producto son las mismas que las del usuario
        val product = productRepository.findById(id).orElseThrow { RuntimeException("Product not found with id: $id") }
        val productDTO = productMapper.toProductDTO(product)
        productDTO.latitude = product.productOwner?.location?.latitude
        productDTO.longitude = product.productOwner?.location?.longitude

        return productDTO
    }
    fun getProductByUser(uid: String): List<ProductDTO> {
        // Si no existe este usuario, lanzar una excepción
        userService.getUserById(uid)

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

    fun getNearbyProducts(uid: String): List<ProductDTO>? {
        // Obtener el usuario actual
        val currentUser = userService.getUserByUid(uid)
        val userLocation = currentUser.location ?: throw RuntimeException("User location not found")
        val kmRatio = currentUser.kmRatio ?: throw RuntimeException("User kmRatio not found")

        // Obtener todos los productos disponibles
        val allProducts = productRepository.findByProductOwnerUidNot(uid)

        // Filtrar los productos según la distancia
        val nearbyProducts = allProducts.filter { product ->
            val userProductOwner = userService.getUserByUid(product.productOwner?.uid!!)

            val productLocation = Location(
                latitude = userProductOwner.location!!.latitude,
                longitude = userProductOwner.location!!.longitude
            )
            val distance = userLocation.calculateDistance(productLocation.latitude, productLocation.longitude)
            distance <= kmRatio
        }

        //Si esta vacío, devolver null
        return if (nearbyProducts.isNotEmpty()) {
            nearbyProducts.map { productMapper.toProductDTO(it) }
        } else {
            null
        }
    }


    fun createProduct(productDTO: ProductDTO, uid:String): ProductDTO {
        productDTO.productOwnerId = uid

        userService.getUserById(uid)

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

    @Transactional
    fun updateProduct(uidUser: String, productDTO: ProductDTO): ProductDTO {
        val productToUpdate = productDTO.id?.let {
            productRepository.findById(it).orElseThrow {
                RuntimeException("Product not found with id: ${productDTO.id}")
            }
        }

        if (productToUpdate != null) {
            if (productToUpdate.productOwner?.uid != uidUser) {
                throw RuntimeException("You are not the owner of this product")
            }

            productToUpdate.name = productDTO.name ?: productToUpdate.name
            productToUpdate.description = productDTO.description ?: productToUpdate.description
            productToUpdate.salePrice = productDTO.salePrice ?: productToUpdate.salePrice
            productToUpdate.category = productDTO.category?.let { categoryService.getCategoryById(it) }
            productToUpdate.availability = productDTO.availability ?: productToUpdate.availability


            imageRepository.deleteByProductId(productToUpdate.id)


            productToUpdate.images.clear()
            productDTO.images?.forEach { imageDTO ->
                imageDTO.base64 = imageDTO.base64?.substringAfter(",")
                val imageEntity = imageMapper.toImage(imageDTO)
                imageEntity.product = productToUpdate
                productToUpdate.images.add(imageEntity)
            }

            val updatedProduct = productRepository.save(productToUpdate)
            return productMapper.toProductDTO(updatedProduct)

        }else{
            throw EntityNotFoundException("Product not found with id: ${productDTO.id}")
        }
    }



    fun updateProductAvailability(id: Long): ProductDTO {
         val product = productRepository.findById(id).orElseThrow { RuntimeException("Product not found with id: $id") }

        product.availability = !product.availability

        return productRepository.save(product).let { productMapper.toProductDTO(it) }
    }

    fun deleteProduct(uidUser:String,id: Long) {
        val product = productRepository.findById(id).orElseThrow { RuntimeException("Product not found with id: $id") }

        if(product.productOwner?.uid != uidUser){
            throw RuntimeException("You are not the owner of this product")
        }
        if (!productRepository.existsById(id)) {
            throw RuntimeException("Product not found with id: $id")
        }
        productRepository.deleteById(id)
    }
}
