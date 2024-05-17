package com.example.hotel_reservation_management.Controllers;

import com.example.hotel_reservation_management.Entities.Hotel;
import com.example.hotel_reservation_management.Entities.HotelDTO;
import com.example.hotel_reservation_management.Services.ServiceHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/hotel")
@RestController
public class HotelController {

    @Autowired
    private ServiceHotel hotelService;

    @GetMapping("/api/getAllHotelsWithCoordinatesInMeters")
    public List<HotelDTO> getAllHotelsWithCoordinatesInMeters() {
        return hotelService.getAllHotelsWithCoordinatesInMeters();
    }
    @GetMapping("/api/getHotels")
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }



}
