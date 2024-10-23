package com.example.flight.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FlightNotificationDto {
    private Long id;

    @NotNull(message = "From cannot be null")
    private String from;

    private LocalDateTime date;
    @NotNull(message = "Subject cannot be null")
    private String subject;
    private String to;  // This will be used as passengerEmail
    private String passengerName;
    private String bookingReference;
    private String ticketNumber;
    private String issuingOffice;

    @NotNull(message = "Issue date cannot be null") // Correct usage of @NotNull
    private LocalDateTime issueDate;

    private LocalDateTime flightDate;
    private String departureCity;
    private String arrivalCity;

    private String departureAirport;   // New field
    private String arrivalAirport;     // New field
    private String terminal;           // New field

    private String flightClass;
    private String baggage;
    private String fareBasis;

    private LocalDate nvbDate;          // New field
    private String flightDuration;     // New field

    private String operatedBy;
    private String marketedBy;

    private String bookingStatus1;     // New field
    private String nvaDate;            // New field
}

