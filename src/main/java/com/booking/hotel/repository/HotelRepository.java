package com.booking.hotel.repository;

import com.booking.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Hotel findHotelByName(String name);
    List<Hotel> findBookingsByName(String name);

}
