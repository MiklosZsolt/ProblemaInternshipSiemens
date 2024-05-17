package com.example.hotel_reservation_management.Entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer roomNumber;

    @Column(nullable = true)
    private Integer type;

    @Column(nullable = true)
    private Integer price;

    @Column(nullable = true)

    private Boolean isAvailable;


    private int hotelId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Room(Integer id, Integer roomNumber, Integer type, Integer price, Boolean isAvailable, int hotelId) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
        this.hotelId = hotelId;
    }

    public Room(Integer roomNumber, Integer type, Integer price, Boolean isAvailable, int hotelId) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
        this.hotelId = hotelId;
    }

    public Room() {
    }
}
