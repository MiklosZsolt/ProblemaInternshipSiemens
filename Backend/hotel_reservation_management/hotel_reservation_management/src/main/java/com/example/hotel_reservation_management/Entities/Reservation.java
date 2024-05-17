package com.example.hotel_reservation_management.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private int roomId;
    @Column
    private int userId;
    @Column
    private LocalDateTime date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Reservation(int id, int roomId, int userId, LocalDateTime date) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.date = date;
    }

    public Reservation( int roomId, int userId, LocalDateTime date) {
        this.roomId = roomId;
        this.userId = userId;
        this.date = date;
    }
    public Reservation() {
    }
}
