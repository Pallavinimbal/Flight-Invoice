package com.example.flight.repository;

import com.example.flight.entity.FlightNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightNotificationRepository extends JpaRepository<FlightNotification, Long>{
}
