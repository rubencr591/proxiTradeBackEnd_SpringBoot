package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.entities.Rent
import com.rubenSL.proxiTrade.services.RentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rents")
class RentController(private val rentService: RentService) {

    @GetMapping("/{id}")
    fun getRentById(@PathVariable id: Long): Rent {
        return rentService.getRentById(id)
    }

    @PostMapping
    fun createRent(@RequestBody rent: Rent): Rent {
        return rentService.createRent(rent)
    }

    @PutMapping("/{id}")
    fun updateRent(@PathVariable id: Long, @RequestBody rent: Rent): Rent {
        return rentService.updateRent(id, rent)
    }

    @DeleteMapping("/{id}")
    fun deleteRent(@PathVariable id: Long) {
        rentService.deleteRent(id)
    }
}
