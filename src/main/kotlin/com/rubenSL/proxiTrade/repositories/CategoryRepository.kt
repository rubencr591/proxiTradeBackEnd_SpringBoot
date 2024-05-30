package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {

    fun findByName(name: String): Category?


}