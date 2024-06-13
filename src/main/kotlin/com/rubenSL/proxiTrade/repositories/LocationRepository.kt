package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.Location
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, Long>{

}