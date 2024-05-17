package com.example.hotel_reservation_management;

import com.example.hotel_reservation_management.Entities.Hotel;
import com.example.hotel_reservation_management.Entities.Room;
import com.example.hotel_reservation_management.Repositories.IHotelRepository;
import com.example.hotel_reservation_management.Repositories.IRoomRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class DataLoader {

    @Autowired
    private IHotelRepository hotelRepository;

    @Autowired
    private IRoomRepository roomRepository;

    @PostConstruct
    public void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File("C:\\Users\\User\\Desktop\\file.json"));

            for (JsonNode hotelNode : rootNode) {
                int hotelId = hotelNode.get("id").asInt();
                String hotelName = hotelNode.get("name").asText();
                double latitude = hotelNode.get("latitude").asDouble();
                double longitude = hotelNode.get("longitude").asDouble();

                Hotel hotel = new Hotel();
                hotel.setId(hotelId);
                hotel.setName(hotelName);
                hotel.setLatitude(latitude);
                hotel.setLongitude(longitude);
                hotelRepository.save(hotel);

                JsonNode roomsNode = hotelNode.get("rooms");
                for (JsonNode roomNode : roomsNode) {
                    int roomNumber = roomNode.get("roomNumber").asInt();
                    Integer type = roomNode.get("type").asInt();
                    int price = roomNode.get("price").asInt();
                    boolean isAvailable = roomNode.get("isAvailable").asBoolean();

                    Room room = new Room();
                    room.setRoomNumber(roomNumber);
                    room.setType(type);
                    room.setPrice(price);
                    room.setAvailable(isAvailable);

                    room.setHotelId(hotelId);

                    roomRepository.save(room);
                }
            }

            System.out.println("Datele au fost salvate cu succes în baza de date!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
