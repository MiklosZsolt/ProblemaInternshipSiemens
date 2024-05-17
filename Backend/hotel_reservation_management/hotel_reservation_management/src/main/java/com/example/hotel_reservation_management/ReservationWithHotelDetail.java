package com.example.hotel_reservation_management;

import java.time.LocalDateTime;

public interface ReservationWithHotelDetail {
    int getId();
    LocalDateTime getDate();
    String getHotelName();
    int getRoomNumber();
    int getRoomType();
    boolean isRoomAvailable(); // Adăugarea unei metode pentru a verifica disponibilitatea camerei
}
