package com.rubenSL.proxiTrade.model.entities

import java.time.LocalDateTime
import jakarta.persistence.*
import lombok.Data

@Data
@Entity
@Table(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user")
    var user: User? = null,

    @ManyToOne
    @JoinColumn(name = "product")
    var product: Product? = null,

    var startDate: LocalDateTime? = null,

    var endDate: LocalDateTime? = null,

    var status: String? = null
)
