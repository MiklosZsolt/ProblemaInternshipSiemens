package com.example.hotel_reservation_management.Controllers;

import com.example.hotel_reservation_management.Entities.Room;
import com.example.hotel_reservation_management.Services.ServiceRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    ServiceRoom roomService;

    @GetMapping("/getAllRooms")
    @ResponseBody
    public List<Room> getAllRooms() {
        return roomService.getAllRoom();
    }


    @PutMapping("/reserve/{roomId}")
    public ResponseEntity<?> reserveRoom(@PathVariable int roomId) {
        try {
            roomService.reserveRoom(roomId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reserving room: " + e.getMessage());
        }
    }

    @PostMapping("/setRoomAvailable/{reservationId}")
    public ResponseEntity<?> setRoomAvailableByReservationId(@PathVariable("reservationId") Integer reservationId) {
        try {
            roomService.setRoomAvailableByReservationId(reservationId);
            return ResponseEntity.ok().body("Disponibilitatea camerei a fost actualizată cu succes");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("A apărut o eroare în timpul actualizării disponibilității camerei");
        }
    }
    @PostMapping("/setRoomUnAvailable/{reservationId}")
    public ResponseEntity<?> setRoomUnAvailableByReservationId(@PathVariable("reservationId") Integer reservationId) {
        try {
            roomService.setRoomUnAvailableByReservationId(reservationId);
            return ResponseEntity.ok().body("Disponibilitatea camerei a fost actualizată cu succes");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("A apărut o eroare în timpul actualizării disponibilității camerei");
        }
    }

    @GetMapping("/room/getAvailableRoomsByHotelName/{hotelName}")
    public List<Room> getAvailableRoomsByHotelName(@PathVariable String hotelName) {
        return roomService.getAvailableRoomsByHotelName(hotelName);
    }




    @PostMapping("/getAllbyHotel")
    @ResponseBody
    public List<Room> getRoomsForHotel(@RequestParam int idHotel) {
        System.out.println(idHotel);
        return roomService.getRoomsForHotel(idHotel);
    }

}
