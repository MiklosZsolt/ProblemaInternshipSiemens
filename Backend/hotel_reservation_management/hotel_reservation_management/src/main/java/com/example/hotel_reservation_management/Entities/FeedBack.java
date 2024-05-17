package com.example.hotel_reservation_management.Entities;

import javax.persistence.*;

@Entity
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private int services;
    @Column

    private int cleanliness;
    @Column

    private int food;
    @Column
    private String comment;

    @Column
    private int hotelId;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServices() {
        return services;
    }

    public void setServices(int services) {
        this.services = services;
    }

    public int getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(int cleanliness) {
        this.cleanliness = cleanliness;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public FeedBack(int id, int services, int cleanliness, int food, String comment) {
        this.id = id;
        this.services = services;
        this.cleanliness = cleanliness;
        this.food = food;
        this.comment = comment;
    }

    public FeedBack(int services, int cleanliness, int food, String comment) {
        this.services = services;
        this.cleanliness = cleanliness;
        this.food = food;
        this.comment = comment;
    }

    public FeedBack(int id, int services, int cleanliness, int food, String comment, int hotelId) {
        this.id = id;
        this.services = services;
        this.cleanliness = cleanliness;
        this.food = food;
        this.comment = comment;
        this.hotelId = hotelId;
    }

    public FeedBack() {
    }
}
