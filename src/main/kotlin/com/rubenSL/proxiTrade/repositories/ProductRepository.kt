package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<Product, Long>{

    fun findByProductOwnerUid(uid: String): List<Product>
    @Query("SELECT p FROM Product p WHERE p.productOwner.uid <> :uid AND p.availability = true")
    fun findByProductOwnerUidNot(uid: String): List<Product>
}