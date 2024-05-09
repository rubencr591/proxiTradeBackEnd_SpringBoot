package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.Rent
import org.springframework.data.jpa.repository.JpaRepository

interface RentRepository : JpaRepository<Rent, Long> {
}