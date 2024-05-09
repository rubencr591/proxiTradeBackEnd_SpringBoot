package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.entities.Booking
import com.rubenSL.proxiTrade.services.BookingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/bookings")
class BookingController(private val bookingService: BookingService) {

    @GetMapping("/{id}")
    fun getBookingById(@PathVariable id: Long): Booking {
        return bookingService.getBookingById(id)
    }

    @PostMapping
    fun createBooking(@RequestBody booking: Booking): Booking {
        return bookingService.createBooking(booking)
    }

    @PutMapping("/{id}")
    fun updateBooking(@PathVariable id: Long, @RequestBody booking: Booking): Booking {
        return bookingService.updateBooking(id, booking)
    }

    @DeleteMapping("/{id}")
    fun deleteBooking(@PathVariable id: Long) {
        bookingService.deleteBooking(id)
    }
}
