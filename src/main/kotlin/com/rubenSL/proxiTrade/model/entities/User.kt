package com.rubenSL.proxiTrade.model.entities

import jakarta.persistence.*
import lombok.Data

@Data
@Entity
@Table(name = "users")
data class User(

    @Id
    var uid: String? = null,

    @Column(name ="name")
    var name: String = "",

    @Column(name ="email", unique = true)
    var email: String = "",

    @Column(name ="password")
    var password: String = "",

    @Column(name ="address")
    var address: String = "",

    @Column(name ="phone", unique = true)
    var phone: Int = 0,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "location")
    var location: Location? = null
)
