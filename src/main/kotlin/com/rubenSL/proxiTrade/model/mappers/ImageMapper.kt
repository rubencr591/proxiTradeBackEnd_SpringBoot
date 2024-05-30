package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.ImageDTO
import com.rubenSL.proxiTrade.model.entities.Image
import com.rubenSL.proxiTrade.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.util.*
import javax.sql.rowset.serial.SerialBlob

@Component
class ImageMapper @Autowired constructor(
    @Lazy
    private val productService: ProductService
) {

    fun toImageDTO(image: Image): ImageDTO {
        val imageBase64 = image.base64?.let { blob ->
            val bytes = blob.getBytes(1, blob.length().toInt())
            Base64.getEncoder().encodeToString(bytes)
        }

        return ImageDTO(
            id = image.id,
            base64 = imageBase64,
            productId = image.product?.id
        )
    }

    fun toImage(imageDTO: ImageDTO): Image {
        return Image(
            base64 = imageDTO.base64?.let { SerialBlob(Base64.getDecoder().decode(it)) },
            product = imageDTO.productId?.let { productService.getProductById(it) }
        )
    }
}
