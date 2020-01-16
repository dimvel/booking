package com.booking.hotel.service;

import com.booking.hotel.entity.Booking;
import com.booking.hotel.entity.Hotel;
import com.booking.hotel.exception.ResourceNotFoundException;

import java.util.List;

public interface BookingService {
    List<Booking> getAllBookings();
    Booking getBookingById(long bookingId) throws ResourceNotFoundException;
    void createOrUpdateBooking(Booking booking);
    void deleteBooking(long bookingId) throws ResourceNotFoundException;
    List<Hotel> getHotelsByBookingSurname(String surname);
    List<Booking> getBookingsByHotels(List<Long> ids);
}
