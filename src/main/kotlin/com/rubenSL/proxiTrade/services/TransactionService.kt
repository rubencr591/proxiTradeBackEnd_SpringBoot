package com.rubenSL.proxiTrade.services
import com.rubenSL.proxiTrade.model.entities.Transaction
import com.rubenSL.proxiTrade.repositories.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionService(private val transactionRepository: TransactionRepository) {

    fun getTransactionById(id: Long): Transaction {
        return transactionRepository.findById(id).orElseThrow { RuntimeException("Transaction not found with id: $id") }
    }

    fun createTransaction(transaction: Transaction): Transaction {
        return transactionRepository.save(transaction)
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
