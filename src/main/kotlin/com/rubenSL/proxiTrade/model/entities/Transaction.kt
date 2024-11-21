package com.rubenSL.proxiTrade.model.entities

import java.time.LocalDateTime
import jakarta.persistence.*
import lombok.Data

@Data
@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seller", nullable = false)
    var userSeller: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_buyer", nullable = false)
    var userBuyer: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", nullable = false)
    var product: Product,

    @Column(nullable = false)
    var date: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var amount:Double,

    @Column(nullable = false)
    var type: String
)
