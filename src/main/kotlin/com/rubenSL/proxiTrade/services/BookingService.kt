package com.rubenSL.proxiTrade.services
import com.rubenSL.proxiTrade.model.entities.Booking
import com.rubenSL.proxiTrade.repositories.BookingRepository
import org.springframework.stereotype.Service

@Service
class BookingService(private val bookingRepository: BookingRepository) {

    fun getBookingById(id: Long): Booking {
        return bookingRepository.findById(id).orElseThrow { RuntimeException("Booking not found with id: $id") }
    }

    fun createBooking(booking: Booking): Booking {
        return bookingRepository.save(booking)
    }

    fun updateBooking(id: Long, booking: Booking): Booking {
        if (!bookingRepository.existsById(id)) {
            throw RuntimeException("Booking not found with id: $id")
        }
        return bookingRepository.save(booking)
    }

    fun deleteBooking(id: Long) {
        if (!bookingRepository.existsById(id)) {
            throw RuntimeException("Booking not found with id: $id")
        }
        bookingRepository.deleteById(id)
    }
}
