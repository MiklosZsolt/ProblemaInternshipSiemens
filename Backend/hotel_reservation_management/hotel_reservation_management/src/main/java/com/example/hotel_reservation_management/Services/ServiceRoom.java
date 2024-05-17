package com.example.hotel_reservation_management.Services;

import com.example.hotel_reservation_management.Entities.Hotel;
import com.example.hotel_reservation_management.Entities.Reservation;
import com.example.hotel_reservation_management.Entities.Room;
import com.example.hotel_reservation_management.Repositories.IHotelRepository;
import com.example.hotel_reservation_management.Repositories.IReservationRepository;
import com.example.hotel_reservation_management.Repositories.IRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class ServiceRoom {


    @Autowired
    IRoomRepository iRoomRepository;

    @Autowired
    IHotelRepository iHotelRepository;
    @Autowired
    IReservationRepository iReservationRepository;
    public List<Room> getRoomsForHotel(int hotelId) {
        try {
            List<Room> rooms = iRoomRepository.findAllByHotelId(hotelId);
            rooms.forEach(room -> {
                System.out.println("Room Number: " + room.getRoomNumber());
                System.out.println("Type: " + room.getType());
                System.out.println("Price: " + room.getPrice());
                System.out.println("Is Available: " + room.getAvailable());
            });
            return rooms;
        } catch (Exception e) {
            // Gestionați eroarea aici
            e.printStackTrace();
            return null; // sau aruncați o altă excepție sau returnați o valoare prestabilită
        }
    }

    @Transactional
    public void setRoomAvailableByReservationId(Integer reservationId) {
        Reservation reservation = iReservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Rezervarea nu a fost găsită"));

        Integer roomId = reservation.getRoomId();
        Room room = iRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Camera nu a fost găsită"));

        room.setAvailable(true);
        iRoomRepository.save(room);
    }

    @Transactional
    public void setRoomUnAvailableByReservationId(Integer reservationId) {
        Reservation reservation = iReservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Rezervarea nu a fost găsită"));

        Integer roomId = reservation.getRoomId();
        Room room = iRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Camera nu a fost găsită"));

        room.setAvailable(false);
        iRoomRepository.save(room);
    }

    public List<Room> getAvailableRoomsByHotelName(String hotelName) {
        Hotel hotel = iHotelRepository.findByName(hotelName);
        if (hotel == null) {
            throw new EntityNotFoundException("Hotel not found");
        }

        return iRoomRepository.findByHotelIdAndIsAvailable(hotel.getId(), true);
    }

    public List<Room> getAllRoom() {
        return iRoomRepository.findAll();
    }

    public List<Room> getAllRooms() {
        return iRoomRepository.findAll();
    }

    public void reserveRoom(int roomId) {
        Room room = iRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
        room.setAvailable(false);
        iRoomRepository.save(room);
    }
}
