package com.rubenSL.proxiTrade.repositories

import org.springframework.data.jpa.repository.JpaRepository
import com.rubenSL.proxiTrade.model.entities.Transaction

interface TransactionRepository : JpaRepository<Transaction, Long> {
}