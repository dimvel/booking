package com.booking.hotel.service;

import com.booking.hotel.entity.Booking;
import com.booking.hotel.entity.Hotel;
import com.booking.hotel.exception.ResourceNotFoundException;
import com.booking.hotel.repository.BookingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Retrieve a booking by the provided id.
     *
     * @param bookingId the booking id
     * @return booking
     */
    @Override
    public Booking getBookingById(long bookingId) throws ResourceNotFoundException  {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with ID=" + bookingId +" not found"));
    }

    /**
     * Retrieve all the bookings.
     *
     * @return a list with all the bookings
     */
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Create/Update a booking.
     *
     * @param booking the booking
     */
    @Override
    public void createOrUpdateBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    /**
     * Delete a booking.
     *
     * @param bookingId the booking id
     */
    @Override
    public void deleteBooking(long bookingId) throws ResourceNotFoundException {
        bookingRepository.delete(getBookingById(bookingId));
    }

    /**
     * Retrieve all the hotels that are associated to the specified booking surname.
     *
     * @param surname the booking customer surname
     * @return the list of hotels
     */
    @Override
    public List<Hotel> getHotelsByBookingSurname(String surname) {
        List<Booking> bookings = bookingRepository.findBookingsByCustomerSurname(surname);
        return bookings.stream()
                .map(Booking::getHotel)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all the bookings that are associated to the specified list of hotel ids.
     *
     * @param ids the list of hotel ids
     * @return a list of bookings
     */
    @Override
    public List<Booking> getBookingsByHotels(List<Long> ids) {
        return bookingRepository.findBookingsByHotelIds(ids);
    }
}
