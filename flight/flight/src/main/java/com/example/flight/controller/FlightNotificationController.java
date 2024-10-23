package com.example.flight.controller;

import com.example.flight.dto.FlightNotificationDto;
import com.example.flight.entity.FlightNotification;
import com.example.flight.mapper.FlightNotificationMapper;
import com.example.flight.repository.FlightNotificationRepository;
import com.example.flight.service.FlightNotificationService;
import freemarker.template.TemplateException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/flight")
public class FlightNotificationController {

    @Autowired
    private FlightNotificationService flightNotificationService;

    @Autowired
    private FlightNotificationMapper flightNotificationMapper;

    @Autowired
    private FlightNotificationRepository flightNotificationRepository; // repository for database operations

    @PostMapping("/send-notification")
    public ResponseEntity<String> sendNotification(@Valid @RequestBody FlightNotificationDto flightNotificationDto) {
        try {
            // Map DTO to entity
            FlightNotification flightNotification = flightNotificationMapper.toEntity(flightNotificationDto);

            // Save flightNotification to the database
            flightNotificationRepository.save(flightNotification);

            // Send the email
            flightNotificationService.sendFlightNotificationEmail(flightNotificationDto, flightNotificationDto.getTo());

            return ResponseEntity.ok("Flight notification sent successfully!");
        } catch (IOException | TemplateException | MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send flight notification: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}

//Postman JSON Body
//http://localhost:8080/api/flight/send-notification
//{
//        "from": "pallunimbal009@gmail.com",
//        "date": "2024-10-24T10:30:00",
//        "subject": "Flight Booking Confirmation",
//        "to": "pallunimbal009@gmail.com",
//        "passengerName": "Pallavi",
//        "bookingReference": "ABC123",
//        "ticketNumber": "98765",
//        "issuingOffice": "Fastays Office",
//        "issueDate": "2024-10-23T12:00:00",
//        "flightDate": "2024-10-24T09:00:00",
//        "departureCity": "Bangalore",
//        "arrivalCity": "Singapore",
//        "flightClass": "Economy",
//        "baggage": "1 bag (23kg)",
//        "fareBasis": "Non-refundable",
//        "operatedBy": "Fastays Airline",
//        "marketedBy": "Fastays",
//        "departureAirport":"Kempegowda International Airport (BLR) – Bengaluru",
//        "arrivalAirport":"Singapore Changi Airport (SIN) – Singapore",
//        "terminal":"Terminal 1",
//        "nvbDate": "2024-09-30T10:00:00",
//        "flightDuration": "2h",
//        "bookingStatus1": "Confirmed",
//        "nvaDate": "2024-10-24"
//        }



