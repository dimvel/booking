package com.booking.hotel.service;

import com.booking.hotel.entity.Booking;
import com.booking.hotel.entity.Hotel;
import com.booking.hotel.exception.ResourceNotFoundException;
import com.booking.hotel.repository.HotelRepository;
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
class HotelServiceTest {

    @InjectMocks
    private HotelServiceImpl hotelService;
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private BookingService bookingService;

    @Test
    public void whenFindAll_thenReturnHotels() {
        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("h1")
                .address("kifisias")
                .starRating(5)
                .build();
        List<Hotel> expectedHotels = Collections.singletonList(hotel);
        doReturn(expectedHotels).when(hotelRepository).findAll();

        List<Hotel> actualHotels = hotelService.getAllHotels();

        assertThat(actualHotels).isEqualTo(expectedHotels);
    }

    @Test
    void whenFindId_thenReturnHotel() throws ResourceNotFoundException {
        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("h1")
                .address("kifisias")
                .starRating(5)
                .build();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Hotel actualHotel = hotelService.getHotelById(hotel.getId());

        assertThat(actualHotel).isEqualTo(hotel);
    }

    @Test
    void testHotelResourceNotFoundExpectedException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            hotelService.getHotelById(1L);
        });
    }

    @Test
    void whenFindName_thenReturnHotel() {
        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("h1")
                .address("kifisias")
                .starRating(5)
                .build();
        doReturn(hotel).when(hotelRepository).findHotelByName(hotel.getName());

        Hotel actualHotel = hotelService.getHotelByName(hotel.getName());

        assertThat(actualHotel).isEqualTo(hotel);
    }

    @Test
    void testSaveHotel() {
        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("h1")
                .address("kifisias")
                .starRating(5)
                .build();

        hotelService.createOrUpdateHotel(hotel);

        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void testDeleteHotel() throws ResourceNotFoundException {
        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("h1")
                .address("kifisias")
                .starRating(5)
                .build();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        hotel = hotelService.getHotelById(hotel.getId());
        hotelService.deleteHotel(hotel.getId());

        verify(hotelRepository, times(1)).delete(hotel);
    }

    @Test
    void whenFindBookingName_thenReturnHotel() {
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
        List<Booking> expectedBookings = Collections.singletonList(booking);
        doReturn(hotels).when(hotelRepository).findBookingsByName(booking.getCustomerSurname());
        List<Long> ids = Collections.singletonList(hotel.getId());
        doReturn(expectedBookings).when(bookingService).getBookingsByHotels(ids);

        List<Booking> actualBookings = hotelService.getBookingsByHotel(booking.getCustomerSurname());

        assertThat(actualBookings).isEqualTo(expectedBookings);
    }

    @Test
    void getTotalBookingAmountByHotel() {
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
                .priceAmount(BigDecimal.valueOf(100))
                .currency("EUR")
                .hotel(hotel)
                .build();
        List<Booking> expectedBookings = Collections.singletonList(booking);
        doReturn(hotels).when(hotelRepository).findBookingsByName(booking.getCustomerSurname());
        List<Long> ids = Collections.singletonList(hotel.getId());
        doReturn(expectedBookings).when(bookingService).getBookingsByHotels(ids);

        BigDecimal totalAmount = hotelService.getTotalBookingAmountByHotel(booking.getCustomerSurname());

        assertThat(totalAmount).isEqualTo((booking.getPriceAmount()));
    }
}