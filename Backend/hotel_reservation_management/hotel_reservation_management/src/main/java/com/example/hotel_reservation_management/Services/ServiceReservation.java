package com.example.hotel_reservation_management.Services;

import com.example.hotel_reservation_management.Entities.Reservation;
import com.example.hotel_reservation_management.Repositories.IHotelRepository;
import com.example.hotel_reservation_management.Repositories.IReservationRepository;
import com.example.hotel_reservation_management.ReservationWithHotelDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceReservation {

    @Autowired
    IReservationRepository reservationRepository;

    @Autowired
    private IHotelRepository hotelRepository;

    public List<ReservationWithHotelDetail> getUserReservations(int userId) {
        List<ReservationWithHotelDetail> reservations = reservationRepository.findUserReservationsWithHotelAndRoomDetails(userId);
        return reservations;
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public ResponseEntity<String> deleteReservation(Integer id) {
        if (reservationRepository.existsById(Math.toIntExact(id))) {
            reservationRepository.deleteById(Math.toIntExact(id));
            return ResponseEntity.status(HttpStatus.OK).body("Reservation deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }
    }
    public void updateReservationRoom(Integer reservationId, Integer roomId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setRoomId(roomId); // Actualizăm id-ul camerei
            reservationRepository.save(reservation); // Salvăm modificările
        } else {
            throw new RuntimeException("Reservation not found with id: " + reservationId);
        }
    }


}
