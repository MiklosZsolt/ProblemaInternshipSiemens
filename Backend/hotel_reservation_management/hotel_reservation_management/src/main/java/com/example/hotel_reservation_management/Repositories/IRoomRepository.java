package com.example.hotel_reservation_management.Repositories;

import com.example.hotel_reservation_management.Entities.Hotel;
import com.example.hotel_reservation_management.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findAllByHotelId(int hotelId);

    List<Room> findByHotelIdAndIsAvailable(Integer hotelId, boolean isAvailable);



}
