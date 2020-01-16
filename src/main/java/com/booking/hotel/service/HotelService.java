package com.booking.hotel.service;

import com.booking.hotel.entity.Booking;
import com.booking.hotel.entity.Hotel;
import com.booking.hotel.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface HotelService {
    List<Hotel> getAllHotels();
    Hotel getHotelById(Long hotelId) throws ResourceNotFoundException;
    Hotel getHotelByName(String name);
    void createOrUpdateHotel(Hotel hotel);
    void deleteHotel(Long hotelId) throws ResourceNotFoundException;
    List<Booking> getBookingsByHotel(String name);
    BigDecimal getTotalBookingAmountByHotel(String name);
}
