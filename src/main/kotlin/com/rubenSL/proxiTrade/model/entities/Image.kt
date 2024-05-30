package com.rubenSL.proxiTrade.model.entities

import jakarta.persistence.*
import lombok.Data
import java.sql.Blob

@Data
@Entity
@Table(name = "images")
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Lob
    @Column(name = "base64", columnDefinition = "LONGBLOB")
    var base64: Blob? = null,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null
)