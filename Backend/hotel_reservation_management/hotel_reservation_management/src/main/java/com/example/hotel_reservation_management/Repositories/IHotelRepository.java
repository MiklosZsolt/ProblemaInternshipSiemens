package com.example.hotel_reservation_management.Repositories;

import com.example.hotel_reservation_management.Entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHotelRepository extends JpaRepository<Hotel,Integer> {

    Hotel findByName(String name);

}
