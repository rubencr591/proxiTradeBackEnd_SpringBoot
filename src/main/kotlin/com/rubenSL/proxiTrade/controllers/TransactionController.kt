package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.dtos.TransactionDTO
import com.rubenSL.proxiTrade.model.dtos.TransactionResponseDTO
import com.rubenSL.proxiTrade.model.entities.Transaction
import com.rubenSL.proxiTrade.security.FirebaseService
import com.rubenSL.proxiTrade.services.TransactionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transactions")
class TransactionController(private val transactionService: TransactionService, private val firebaseService: FirebaseService) {

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Long): Transaction {
        return transactionService.getTransactionById(id)
    }

    @GetMapping("my-purchased-products")
    fun getMyPurchasedProducts(@RequestHeader("Authorization") idToken:String): List<TransactionResponseDTO> {
        val token = idToken.substring(7)
        firebaseService.verifyToken(token)
        val uid = firebaseService.getUidFromToken(token)
        return transactionService.getMyPurchasedProducts(uid)
    }

    @GetMapping("my-sold-products")
    fun getMySoldProducts(@RequestHeader("Authorization") idToken:String): List<TransactionResponseDTO> {
        val token = idToken.substring(7)
        firebaseService.verifyToken(token)
        val uid = firebaseService.getUidFromToken(token)
        return transactionService.getMySoldProducts(uid)
    }
    @PostMapping
    fun createTransaction(@RequestHeader("Authorization") idToken:String,@RequestBody transaction: TransactionDTO): Transaction {
        val token = idToken.substring(7)
        firebaseService.verifyToken(token)
        val uidBuyer = firebaseService.getUidFromToken(token)
        transaction.userBuyerUid = uidBuyer
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
