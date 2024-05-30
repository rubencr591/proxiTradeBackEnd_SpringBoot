package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.ProfilePicture
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProfilePictureRepository : JpaRepository<ProfilePicture, Long> {
    fun findByUserUid(uid: String): Optional<ProfilePicture>
}