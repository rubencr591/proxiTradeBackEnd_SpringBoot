package com.rubenSL.proxiTrade.model.entities

import jakarta.persistence.*
import lombok.Data
import java.time.LocalDateTime

@Data
@Entity
@Table(name = "rents")
class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    private val user: User? = null

    @ManyToOne
    @JoinColumn(name = "product_id")
    private val product: Product? = null
    private val startDate: LocalDateTime? = null
    private val endDate: LocalDateTime? = null
}