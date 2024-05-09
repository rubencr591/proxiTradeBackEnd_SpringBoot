package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.entities.Transaction
import com.rubenSL.proxiTrade.services.TransactionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transactions")
class TransactionController(private val transactionService: TransactionService) {

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Long): Transaction {
        return transactionService.getTransactionById(id)
    }

    @PostMapping
    fun createTransaction(@RequestBody transaction: Transaction): Transaction {
        return transactionService.createTransaction(transaction)
    }

    @PutMapping("/{id}")
    fun updateTransaction(@PathVariable id: Long, @RequestBody transaction: Transaction): Transaction {
        return transactionService.updateTransaction(id, transaction)
    }

    @DeleteMapping("/{id}")
    fun deleteTransaction(@PathVariable id: Long) {
        transactionService.deleteTransaction(id)
    }
}
