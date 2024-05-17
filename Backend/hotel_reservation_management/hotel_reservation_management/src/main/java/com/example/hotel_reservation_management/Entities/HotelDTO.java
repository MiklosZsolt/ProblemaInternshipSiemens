package com.example.hotel_reservation_management.Entities;

public class HotelDTO {
    private int id;
    private String name;
    private double latitudeInMeters;
    private double longitudeInMeters;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitudeInMeters() {
        return latitudeInMeters;
    }

    public void setLatitudeInMeters(double latitudeInMeters) {
        this.latitudeInMeters = latitudeInMeters;
    }

    public double getLongitudeInMeters() {
        return longitudeInMeters;
    }

    public void setLongitudeInMeters(double longitudeInMeters) {
        this.longitudeInMeters = longitudeInMeters;
    }

    public HotelDTO(int id, String name, double latitudeInMeters, double longitudeInMeters) {
        this.id = id;
        this.name = name;
        this.latitudeInMeters = latitudeInMeters;
        this.longitudeInMeters = longitudeInMeters;
    }

    public HotelDTO(String name, double latitudeInMeters, double longitudeInMeters) {
        this.name = name;
        this.latitudeInMeters = latitudeInMeters;
        this.longitudeInMeters = longitudeInMeters;
    }

    public HotelDTO() {
    }
}
