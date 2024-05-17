package com.example.hotel_reservation_management.Services;

import com.example.hotel_reservation_management.Entities.FeedBack;
import com.example.hotel_reservation_management.Repositories.IFeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFeedBack {
    @Autowired
    IFeedBackRepository iFeedBackRepository;

    public FeedBack addFeedBack(FeedBack feedBack) {
        return iFeedBackRepository.save(feedBack);

    }
}
