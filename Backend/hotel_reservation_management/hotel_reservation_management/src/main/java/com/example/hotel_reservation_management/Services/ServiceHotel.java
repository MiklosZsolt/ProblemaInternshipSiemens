package com.example.hotel_reservation_management.Services;

import com.example.hotel_reservation_management.Controllers.HotelController;
import com.example.hotel_reservation_management.Entities.Hotel;
import com.example.hotel_reservation_management.Entities.HotelDTO;
import com.example.hotel_reservation_management.Repositories.IHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceHotel {

    @Autowired
    private IHotelRepository hotelRepository;

    private double[] toMeters(double latitude, double longitude) {
        final double METERS_PER_DEGREE_LATITUDE = 111139;
        final double METERS_PER_DEGREE_LONGITUDE = 111139;

        double latInMeters = latitude * METERS_PER_DEGREE_LATITUDE;
        double lonInMeters = longitude * METERS_PER_DEGREE_LONGITUDE;

        return new double[]{latInMeters, lonInMeters};
    }


    public List<HotelDTO> getAllHotelsWithCoordinatesInMeters() {
        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelDTO> hotelDTOs = new ArrayList<>();
        for (Hotel hotel : hotels) {
            double[] meters = toMeters(hotel.getLatitude(), hotel.getLongitude());
            HotelDTO hotelDTO = new HotelDTO();
            hotelDTO.setId(hotel.getId());
            hotelDTO.setName(hotel.getName());
            hotelDTO.setLatitudeInMeters(meters[0]);
            hotelDTO.setLongitudeInMeters(meters[1]);
            hotelDTOs.add(hotelDTO);
        }
        return hotelDTOs;
    }
    public List<Hotel> getAllHotels()
    {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels;


    }

}
