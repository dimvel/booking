package com.booking.hotel.controller;

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
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    public HotelService hotelService;
    public BookingService bookingService;

    public HotelController(HotelService hotelService, BookingService bookingService) {
        this.hotelService = hotelService;
        this.bookingService = bookingService;
    }

    /**
     * Retrieve all the hotels.
     *
     * @return a list with all the hotels
     */
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Retrieve a hotel.
     *
     * @param id the hotel id
     * @return the hotel based on the hotel id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Hotel hotel = hotelService.getHotelById(id);

        return new ResponseEntity<>(hotel, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Create a hotel.
     *
     * @param hotel the hotel
     * @return the hotel
     */
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody Hotel hotel) {
        hotelService.createOrUpdateHotel(hotel);
        return ResponseEntity.ok(hotel);
    }

    /**
     * Update a hotel.
     *
     * @param hotelId the hotel id
     * @param hotel the hotel
     * @return the hotel
     */
    @PutMapping
    public ResponseEntity<Hotel> updateHotel(
            @RequestParam(value = "id") Long hotelId, @Valid @RequestBody Hotel hotel) {
        hotel.setId(hotelId);
        hotelService.createOrUpdateHotel(hotel);
        return ResponseEntity.ok(hotel);
    }

    /**
     * Delete a hotel.
     *
     * @param id the hotel id
     * @return the status of deletion
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteHotelById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        hotelService.deleteHotel(id);
        return HttpStatus.OK;
    }

    /**
     * Retrieve all the hotels that are associated to the specified booking surname.
     *
     * @param surname the booking customer surname
     * @return the list of hotels
     */
    @GetMapping("/booking")
    public ResponseEntity<List<Hotel>> getHotelsByBookingSurname(@RequestParam("surname") String surname) {
        List<Hotel> hotels = bookingService.getHotelsByBookingSurname(surname);
        return new ResponseEntity<>(hotels, new HttpHeaders(), HttpStatus.OK);
    }

}