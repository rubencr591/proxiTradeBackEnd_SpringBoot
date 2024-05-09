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

    @ManyToOne
    @JoinColumn(name = "userSeller")
    var userSeller: User? = null,

    @ManyToOne
    @JoinColumn(name = "userBuyer")
    var userBuyer: User? = null,

    @ManyToOne
    @JoinColumn(name = "product")
    var product: Product? = null,

    var date: LocalDateTime? = null,

    var type: String? = null
)
