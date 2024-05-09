package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.Booking
import org.springframework.data.jpa.repository.JpaRepository

interface BookingRepository : JpaRepository<Booking, Long> {
}