package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.dtos.ProductDTO
import com.rubenSL.proxiTrade.model.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import com.rubenSL.proxiTrade.model.entities.User
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<Product, Long>{

    fun findByProductOwnerUid(uid: String): List<Product>
    @Query("SELECT p FROM Product p WHERE p.productOwner.uid <> :uid")
    fun findByProductOwnerUidNot(uid: String): List<Product>
}