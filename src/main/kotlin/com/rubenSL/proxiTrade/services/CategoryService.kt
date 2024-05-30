package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.model.entities.Category
import com.rubenSL.proxiTrade.repositories.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryService {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun getAllCategories():List<Category>{
        return categoryRepository.findAll()
    }

    fun getCategoryById(id: Long): Category {
        return categoryRepository.findById(id).orElseThrow { RuntimeException("Category not found with id: $id") }
    }

}