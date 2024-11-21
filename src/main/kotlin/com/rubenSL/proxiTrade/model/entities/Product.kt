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

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null,

    @Column(name = "sale_price")
    var salePrice: Double = 0.0,


    var latitude: Float = 0.0f,

    var longitude: Float = 0.0f,

    var availability: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "productOwner")
    var productOwner: User? = null,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL])
    var images: MutableList<Image> = mutableListOf()
)