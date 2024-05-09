package com.rubenSL.proxiTrade.model.entities

import jakarta.persistence.*
import lombok.Data

@Data
@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var name: String = "",

    var description: String = "",

    var category: String = "",

    @Column(name = "sale_price")
    var salePrice: Double = 0.0,

    @Column(name = "rented_price")
    var rentedPrice: Double = 0.0,

    var address: String = "",

    var availability: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "productOwner")
    var productOwner: User? = null
)