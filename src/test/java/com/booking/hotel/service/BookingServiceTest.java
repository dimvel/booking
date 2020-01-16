package com.booking.hotel.service;

import com.booking.hotel.entity.Booking;
import com.booking.hotel.entity.Hotel;
import com.booking.hotel.exception.ResourceNotFoundException;
import com.booking.hotel.repository.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    private BookingServiceImpl bookingService;
    @Mock
    private BookingRepository bookingRepository;

    @Test
    public void whenFindAll_thenReturnBookings() {
        Booking booking = Booking.builder()
                .id(1L)
                .customerName("Petros")
                .customerSurname("Petridis")
                .priceAmount(BigDecimal.valueOf(100))
                .currency("EUR")
                .hotel(new Hotel())
                .build();
        List<Booking> expectedBookings = Collections.singletonList(booking);
        doReturn(expectedBookings).when(bookingRepository).findAll();

        List<Booking> actualBookings = bookingService.getAllBookings();

        assertThat(actualBookings).isEqualTo(expectedBookings);
    }

    @Test
    void whenFindId_thenReturnBooking() throws ResourceNotFoundException {
        Booking booking = Booking.builder()
                .id(1L)
                .customerName("Petros")
                .customerSurname("Petridis")
                .priceAmount(BigDecimal.valueOf(100))
                .currency("EUR")
                .hotel(new Hotel())
                .build();
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking actualBooking = bookingService.getBookingById(booking.getId());

        assertThat(actualBooking).isEqualTo(booking);
    }

    @Test
    void testBookingResourceNotFoundExpectedException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.getBookingById(1L);
        });
    }

    @Test
    void testSaveBooking() {
        Booking booking = Booking.builder()
                .id(1L)
                .customerName("Petros")
                .customerSurname("Petridis")
                .priceAmount(BigDecimal.valueOf(100))
                .currency("EUR")
                .hotel(new Hotel())
                .build();

        bookingService.createOrUpdateBooking(booking);

        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testDeleteHotel() throws ResourceNotFoundException {
        Booking booking = Booking.builder()
                .id(1L)
                .customerName("Petros")
                .customerSurname("Petridis")
                .priceAmount(BigDecimal.valueOf(100))
                .currency("EUR")
                .hotel(new Hotel())
                .build();
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        booking = bookingService.getBookingById(1L);
        bookingService.deleteBooking(booking.getId());

        verify(bookingRepository, times(1)).delete(booking);
    }

    @Test
    void whenFindSurName_thenReturnBooking() {
        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("h1")
                .address("kifisias")
                .starRating(5)
                .build();
        List<Hotel> hotels = Collections.singletonList(hotel);
        Booking booking = Booking.builder()
                .id(1L)
                .customerName("Petros")
                .customerSurname("Petridis")
                .hotel(hotel)
                .build();
        List<Booking> bookings = Collections.singletonList(booking);
        doReturn(bookings).when(bookingRepository).findBookingsByCustomerSurname(booking.getCustomerSurname());

        List<Hotel> actualHotels = bookingService.getHotelsByBookingSurname(booking.getCustomerSurname());

        assertThat(actualHotels).isEqualTo(hotels);
    }

    @Test
    void whenFindHotelId_thenReturnBookings() {
        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("h1")
                .address("kifisias")
                .starRating(5)
                .build();
        Booking booking = Booking.builder()
                .id(1L)
                .customerName("Petros")
                .customerSurname("Petridis")
                .hotel(hotel)
                .build();
        List<Long> ids = Collections.singletonList(hotel.getId());
        List<Booking> expectedBookings = Collections.singletonList(booking);
        doReturn(expectedBookings).when(bookingRepository).findBookingsByHotelIds(ids);

        List<Booking> actualBookings = bookingService.getBookingsByHotels(ids);

        assertThat(actualBookings).isEqualTo(expectedBookings);
    }
}