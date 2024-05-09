package com.rubenSL.proxiTrade.model.entities

import jakarta.persistence.*
import lombok.Data

@Data
@Entity
@Table(name = "conversations")
data class Conversation (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user1_id", referencedColumnName = "uid")
    var user1: User? = null,

    @ManyToOne
    @JoinColumn(name = "user2_id", referencedColumnName = "uid")
    var user2: User? = null
)