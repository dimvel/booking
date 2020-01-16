package com.booking.hotel.service;

import com.booking.hotel.entity.Booking;
import com.booking.hotel.entity.Hotel;
import com.booking.hotel.exception.ResourceNotFoundException;
import com.booking.hotel.repository.HotelRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;
    private BookingService bookingService;

    public HotelServiceImpl(HotelRepository hotelRepository, BookingService bookingService) {
        this.hotelRepository = hotelRepository;
        this.bookingService = bookingService;
    }

    /**
     * Retrieve a hotel by the provided id.
     *
     * @param hotelId the hotel id
     * @return hotel
     */
    @Override
    public Hotel getHotelById(Long hotelId) throws ResourceNotFoundException {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with ID=" + hotelId +" not found"));
    }

    /**
     * Retrieve a hotel by the provided name.
     *
     * @param name the hotel name
     * @return hotel
     */
    @Override
    public Hotel getHotelByName(String name) {
        return hotelRepository.findHotelByName(name);
    }

    /**
     * Retrieve all the hotels.
     *
     * @return a list with all the hotels
     */
    @Override
    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    /**
     * Create/Update a hotel.
     *
     * @param hotel the hotel
     */
    @Override
    public void createOrUpdateHotel(Hotel hotel){
        hotelRepository.save(hotel);
    }

    /**
     * Delete a hotel.
     *
     * @param hotelId the hotel id
     */
    @Override
    public void deleteHotel(Long hotelId) throws ResourceNotFoundException {
        hotelRepository.delete(getHotelById(hotelId));
    }

    /**
     * Retrieve all the bookings that are associated to the specified hotel name.
     *
     * @param name the hotel name
     * @return the list of bookings
     */
    @Override
    public List<Booking> getBookingsByHotel(String name) {
        List<Hotel> hotels = hotelRepository.findBookingsByName(name);
        List<Long> hotelIdList = hotels.stream()
                .map(Hotel::getId)
                .collect(Collectors.toList());
        return bookingService.getBookingsByHotels(hotelIdList);
    }

    /**
     * Retrieve the sum of all booking amounts that are associated to the specified hotel name.
     *
     * @param name the hotel name
     * @return the sum of all booking amounts
     */
    @Override
    public BigDecimal getTotalBookingAmountByHotel(String name) {
        List<Booking> bookings = this.getBookingsByHotel(name);
        return bookings.stream()
                .map(Booking::getPriceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
