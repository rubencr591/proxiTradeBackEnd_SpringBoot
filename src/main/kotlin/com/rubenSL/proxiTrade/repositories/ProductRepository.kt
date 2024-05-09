package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import com.rubenSL.proxiTrade.model.entities.User

interface ProductRepository : JpaRepository<Product, Long>{
}