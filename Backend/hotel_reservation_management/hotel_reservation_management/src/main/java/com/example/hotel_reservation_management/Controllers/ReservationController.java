package com.example.hotel_reservation_management.Controllers;

import com.example.hotel_reservation_management.Entities.Reservation;
import com.example.hotel_reservation_management.ReservationWithHotelDetail;
import com.example.hotel_reservation_management.Services.ServiceReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ServiceReservation serviceReservation;

    @PostMapping("/add")
    public ResponseEntity<?> addReservation(@RequestBody Reservation reservation) {
        try {
            // Adaugă rezervarea folosind serviciul
            Reservation savedReservation = serviceReservation.addReservation(reservation);
            return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add reservation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/reservation/getUserReservations")
    public List<ReservationWithHotelDetail> getUserReservations(@RequestParam int userId) {
        return serviceReservation.getUserReservations(userId);
    }

    @DeleteMapping("/deleteReservation/{id}")
    public ResponseEntity<String> deleteTreatment(@PathVariable Integer id) {
        return serviceReservation.deleteReservation(id);
    }


    @PutMapping("/updateReservationRoom/{reservationId}")
    public void updateReservationRoom(@PathVariable Integer reservationId, @RequestParam Integer roomId) {
        serviceReservation.updateReservationRoom(reservationId, roomId);
    }

}

