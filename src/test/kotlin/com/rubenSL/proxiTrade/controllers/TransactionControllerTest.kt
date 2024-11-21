package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.dtos.TransactionDTO
import com.rubenSL.proxiTrade.model.dtos.TransactionResponseDTO
import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.model.entities.Transaction
import com.rubenSL.proxiTrade.model.entities.User
import com.rubenSL.proxiTrade.security.FirebaseService
import com.rubenSL.proxiTrade.services.TransactionService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

class TransactionControllerTest {
    private val transactionService: TransactionService = Mockito.mock(TransactionService::class.java)
    private val firebaseService: FirebaseService = Mockito.mock(FirebaseService::class.java)
    private val transactionController = TransactionController(transactionService, firebaseService)

    @Test
    fun getTransactionById() {
        val id = 1L
        val userSeller = User("sellerUid", "sellerName", "sellerEmail", "sellerPassword", 1234567890L, null, null, 1.0)
        val userBuyer = User("buyerUid", "buyerName", "buyerEmail", "buyerPassword", 1234567890L, null, null, 1.0)
        val date = LocalDateTime.now()
        val product = Product(1L, "productName", "productDescription", null, 0.0, 0.0f, 0.0f, true, userSeller, mutableListOf())
        val expectedTransaction = Transaction(id, userSeller, userBuyer, product, date, 0.0, "type")
        Mockito.`when`(transactionService.getTransactionById(id)).thenReturn(expectedTransaction)

        val actualTransaction = transactionController.getTransactionById(id)

        assertEquals(expectedTransaction, actualTransaction)
    }

    @Test
    fun getMyPurchasedProducts() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val expectedTransactions = listOf(TransactionResponseDTO("userBuyerName", "userSellerName", "productName", 0.0, "type"))
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(transactionService.getMyPurchasedProducts(uid)).thenReturn(expectedTransactions)

        val actualTransactions = transactionController.getMyPurchasedProducts(idToken)

        assertEquals(expectedTransactions, actualTransactions)
    }

    @Test
    fun getMySoldProducts() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val expectedTransactions = listOf(TransactionResponseDTO("userBuyerName", "userSellerName", "productName", 0.0, "type"))
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(transactionService.getMySoldProducts(uid)).thenReturn(expectedTransactions)

        val actualTransactions = transactionController.getMySoldProducts(idToken)

        assertEquals(expectedTransactions, actualTransactions)
    }

    @Test
    fun createTransaction() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val userSeller = User("sellerUid", "sellerName", "sellerEmail", "sellerPassword", 1234567890L, null, null, 1.0)
        val userBuyer = User("buyerUid", "buyerName", "buyerEmail", "buyerPassword", 1234567890L, null, null, 1.0)
        val product = Product(1L, "productName", "productDescription", null, 0.0, 0.0f, 0.0f, true, userSeller, mutableListOf())
        val date = LocalDateTime.now()
        val transactionDTO = TransactionDTO(1L, userSeller.uid, uid, product.id, 0.0, "type")
        val expectedTransaction = Transaction(1L, userSeller, userBuyer, product, date, 0.0, "type")
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(transactionService.createTransaction(transactionDTO)).thenReturn(expectedTransaction)

        val actualTransaction = transactionController.createTransaction(idToken, transactionDTO)

        assertEquals(expectedTransaction, actualTransaction)
    }

    @Test
    fun updateTransaction() {
        val id = 1L
        val userSeller = User("sellerUid", "sellerName", "sellerEmail", "sellerPassword", 1234567890L, null, null, 1.0)
        val userBuyer = User("buyerUid", "buyerName", "buyerEmail", "buyerPassword", 1234567890L, null, null, 1.0)
        val product = Product(1L, "productName", "productDescription", null, 0.0, 0.0f, 0.0f, true, userSeller, mutableListOf())
        val date = LocalDateTime.now()
        val transaction = Transaction(id, userSeller, userBuyer, product, date, 0.0, "type")
        Mockito.`when`(transactionService.updateTransaction(id, transaction)).thenReturn(transaction)

        val actualTransaction = transactionController.updateTransaction(id, transaction)

        assertEquals(transaction, actualTransaction)
    }

    @Test
    fun deleteTransaction() {
        val id = 1L
        Mockito.doNothing().`when`(transactionService).deleteTransaction(id)

        transactionController.deleteTransaction(id)

        Mockito.verify(transactionService, Mockito.times(1)).deleteTransaction(id)
    }
}