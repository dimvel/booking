package com.booking.hotel.repository;

import com.booking.hotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByCustomerSurname(String customerSurname);

    @Query("select b from Booking b where b.hotel.id in :idsHotel")
    List<Booking> findBookingsByHotelIds(List<Long> idsHotel);
}
