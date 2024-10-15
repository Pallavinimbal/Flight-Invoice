package com.example.flight.controller;

import com.example.flight.dto.FlightNotificationDto;
import com.example.flight.service.FlightNotificationService;
import freemarker.template.TemplateException;
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

    @PostMapping("/send-notification")
    public ResponseEntity<String> sendFlightNotification(@RequestBody FlightNotificationDto notificationDto) {
        try {
            System.out.println("Notification DTO: " + notificationDto); // Debugging
            flightNotificationService.sendFlightNotificationEmail(notificationDto, notificationDto.getTo());
            return ResponseEntity.ok("Flight notification sent successfully!");
        } catch (IOException | TemplateException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send flight notification: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}


//{
//        "from": "pallunimbal009@gmail.com",
//        "date": "2024-10-14T10:30:00",
//        "subject": "Flight Booking Confirmation",
//        "to": "pallunimbal009@gmail.com",
//        "passengerName": "Pallavi",
//        "bookingReference": "ABCD1234",
//        "ticketNumber": "9876543210",
//        "issuingOffice": "Fastays Office",
//        "issueDate": "2024-10-14T12:00:00",
//        "flightDate": "2024-10-14T09:00:00",
//        "departureCity": "Bangalore",
//        "arrivalCity": "Singapore",
//        "flightClass": "Economy",
//        "baggage": "1 bag (23kg)",
//        "fareBasis": "Non-refundable",
//        "operatedBy": "Fastays Airline",
//        "marketedBy": "Fastays"
//        }




