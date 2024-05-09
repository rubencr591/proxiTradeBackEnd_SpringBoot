package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.model.entities.Rent
import com.rubenSL.proxiTrade.repositories.RentRepository
import org.springframework.stereotype.Service

@Service
class RentService(private val rentRepository: RentRepository) {

    fun getRentById(id: Long): Rent {
        return rentRepository.findById(id).orElseThrow { RuntimeException("Rent not found with id: $id") }
    }

    fun createRent(rent: Rent): Rent {
        return rentRepository.save(rent)
    }

    fun updateRent(id: Long, rent: Rent): Rent {
        if (!rentRepository.existsById(id)) {
            throw RuntimeException("Rent not found with id: $id")
        }
        return rentRepository.save(rent)
    }

    fun deleteRent(id: Long) {
        if (!rentRepository.existsById(id)) {
            throw RuntimeException("Rent not found with id: $id")
        }
        rentRepository.deleteById(id)
    }
}
