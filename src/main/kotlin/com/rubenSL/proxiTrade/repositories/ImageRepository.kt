package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long> {
    fun findByProductId(productId: Long): List<Image>
    fun deleteByProductId(productId: Long)
}