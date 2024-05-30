package com.rubenSL.proxiTrade.model.entities

import jakarta.persistence.*
import lombok.Data
import java.sql.Blob

@Data
@Entity
@Table(name = "profile_picture")
data class ProfilePicture (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Lob
    @Column(name = "base64", columnDefinition = "LONGBLOB")
    var base64: Blob? = null,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User? = null
)