package com.example.hotel_reservation_management.Controllers;

import com.example.hotel_reservation_management.Entities.FeedBack;
import com.example.hotel_reservation_management.Services.ServiceFeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    @Autowired
    ServiceFeedBack serviceFeedback;

    @PostMapping("/addFeedback")
    public ResponseEntity<?> saveFeedback(@RequestBody FeedBack feedBack) {
        FeedBack addedFeedback = serviceFeedback.addFeedBack(feedBack);
        return ResponseEntity.ok(addedFeedback);
    }
}
