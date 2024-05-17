package com.example.hotel_reservation_management.Repositories;

import com.example.hotel_reservation_management.Entities.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeedBackRepository extends JpaRepository<FeedBack, Integer> {
}
