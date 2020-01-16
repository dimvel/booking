package com.booking.hotel.controller;

import com.booking.hotel.entity.Booking;
import com.booking.hotel.entity.Hotel;
import com.booking.hotel.exception.ResourceNotFoundException;
import com.booking.hotel.service.BookingService;
import com.booking.hotel.service.HotelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/bookings")
public class BookingController {

    private HotelService hotelService;
    private BookingService bookingService;

    public BookingController(HotelService hotelService, BookingService bookingService) {
        this.hotelService = hotelService;
        this.bookingService = bookingService;
    }

    /**
     * Retrieve all the bookings.
     *
     * @return a list with all the bookings
     */
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Retrieve a booking.
     *
     * @param id the booking id
     * @return the booking based on the booking id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Booking booking = bookingService.getBookingById(id);
        return new ResponseEntity<>(booking, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Create a booking.
     *
     * @param booking the booking
     * @return the booking
     */
    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        Hotel hotel = hotelService.getHotelByName(booking.getHotel().getName());
        if (hotel == null) {
            hotelService.createOrUpdateHotel(booking.getHotel());
        }
        bookingService.createOrUpdateBooking(booking);
        return ResponseEntity.ok(booking);
    }

    /**
     * Update a booking.
     *
     * @param bookingId the booking id
     * @param booking the booking information details
     * @return the booking
     */
    @PutMapping
    public ResponseEntity<Booking> updateBooking(
            @RequestParam(value = "id") Long bookingId, @Valid @RequestBody Booking booking) {
        Hotel hotel = hotelService.getHotelByName(booking.getHotel().getName());
        if (hotel == null) {
            hotelService.createOrUpdateHotel(booking.getHotel());
        }
        booking.setId(bookingId);
        bookingService.createOrUpdateBooking(booking);
        return ResponseEntity.ok(booking);
    }

    /**
     * Delete a booking.
     *
     * @param id the booking id
     * @return the status of deletion
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteBookingById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        bookingService.deleteBooking(id);
        return HttpStatus.OK;
    }

    /**
     * Retrieve all the bookings that are associated to the specified hotel name.
     *
     * @param name the hotel name
     * @return the list of bookings
     */
    @GetMapping("/hotel")
    public ResponseEntity<List<Booking>> findBookingsByHotelName(@RequestParam("name") String name) {
        List<Booking> bookings = hotelService.getBookingsByHotel(name);
        return new ResponseEntity<>(bookings, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Get the sum of amounts of all the bookings for the specified hotel name.
     *
     * @param name the hotel name
     * @return the sum of amount
     */
    @GetMapping("/amount")
    public ResponseEntity<BigDecimal> findTotalBookingAmount(@RequestParam("name") String name) {
        BigDecimal totalBookingAmount = hotelService.getTotalBookingAmountByHotel(name);
        return new ResponseEntity<>(totalBookingAmount, new HttpHeaders(), HttpStatus.OK);
    }
}



