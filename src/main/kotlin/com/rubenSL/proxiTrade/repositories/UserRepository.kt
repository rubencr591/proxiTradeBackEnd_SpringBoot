package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface UserRepository : JpaRepository<User, String> {

    fun findByEmail(email: String): User?

    fun findByName(name: String): User?

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT(:name, '%'))")
    fun findAllByName(name: String): List<User>

    fun findByPhone(phone: Int): User?
}