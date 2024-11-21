package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.TransactionDTO
import com.rubenSL.proxiTrade.model.dtos.TransactionResponseDTO
import com.rubenSL.proxiTrade.model.entities.Product
import com.rubenSL.proxiTrade.model.entities.Transaction
import com.rubenSL.proxiTrade.model.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TransactionMapper {

    fun toTransactionDTO(transaction: Transaction): TransactionDTO {
        return TransactionDTO(
            id = transaction.id,
            userSellerUid = transaction.userSeller.uid,
            userBuyerUid = transaction.userBuyer.uid,
            productId = transaction.product.id,
            amount = transaction.amount,
            type = transaction.type
        )
    }

    fun toTransaction(transactionDTO: TransactionDTO): Transaction {
        val userSeller = User(uid = transactionDTO.userSellerUid!!)
        val userBuyer = User(uid = transactionDTO.userBuyerUid!!)
        val product = Product(id = transactionDTO.productId!!)
        return Transaction(
            userSeller = userSeller,
            userBuyer = userBuyer,
            product = product,
            amount = transactionDTO.amount!!,
            type = transactionDTO.type!!
        )
    }

    fun toTransactionResponseDTO(transaction: Transaction): TransactionResponseDTO {
        return TransactionResponseDTO(
            userBuyerName = transaction.userBuyer.name,
            userSellerName = transaction.userSeller.name,
            productName = transaction.product.name,
            amount = transaction.amount,
            type = transaction.type
        )
    }
}
