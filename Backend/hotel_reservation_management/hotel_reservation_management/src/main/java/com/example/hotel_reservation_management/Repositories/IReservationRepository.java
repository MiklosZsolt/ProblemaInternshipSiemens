package com.example.hotel_reservation_management.Repositories;

import com.example.hotel_reservation_management.Entities.Reservation;
import com.example.hotel_reservation_management.ReservationWithHotelDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface IReservationRepository extends JpaRepository<Reservation, Integer> {


        @Query("SELECT r.id AS id, r.date AS date, h.name AS hotelName, c.roomNumber AS roomNumber, c.type AS roomType, c.isAvailable AS roomAvailable " +
                "FROM Reservation r " +
                "JOIN Room c ON r.roomId = c.id " +
                "JOIN Hotel h ON c.hotelId = h.id " +
                "WHERE r.userId = :userId")
        List<ReservationWithHotelDetail> findUserReservationsWithHotelAndRoomDetails(@Param("userId") int userId);



        List<Reservation> findByUserId(Integer userId);


}
