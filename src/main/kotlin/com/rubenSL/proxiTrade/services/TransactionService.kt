package com.rubenSL.proxiTrade.services
import com.rubenSL.proxiTrade.model.dtos.TransactionDTO
import com.rubenSL.proxiTrade.model.dtos.TransactionResponseDTO
import com.rubenSL.proxiTrade.model.entities.Transaction
import com.rubenSL.proxiTrade.model.mappers.TransactionMapper
import com.rubenSL.proxiTrade.repositories.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionService(private val transactionRepository: TransactionRepository,
                         private val transactionMapper: TransactionMapper,
                         private val productService: ProductService) {

    fun getTransactionById(id: Long): Transaction {
        return transactionRepository.findById(id).orElseThrow { RuntimeException("Transaction not found with id: $id") }
    }

    fun getMyPurchasedProducts(userId: String): List<TransactionResponseDTO> {

        val transactions = transactionRepository.findByUserBuyerUid(userId)
        return transactions.map { transactionMapper.toTransactionResponseDTO(it) }
    }

    fun getMySoldProducts(userId: String): List<TransactionResponseDTO> {

        val transactions = transactionRepository.findByUserSellerUid(userId)
        return transactions.map { transactionMapper.toTransactionResponseDTO(it) }
    }

    fun createTransaction(transaction: TransactionDTO): Transaction {
        val transactionEntity = transactionMapper.toTransaction(transaction)
        productService.updateProductAvailability(transaction.productId!!)
        return transactionRepository.save(transactionEntity)
    }

    fun updateTransaction(id: Long, transaction: Transaction): Transaction {
        if (!transactionRepository.existsById(id)) {
            throw RuntimeException("Transaction not found with id: $id")
        }
        return transactionRepository.save(transaction)
    }

    fun deleteTransaction(id: Long) {
        if (!transactionRepository.existsById(id)) {
            throw RuntimeException("Transaction not found with id: $id")
        }
        transactionRepository.deleteById(id)
    }
}
