package com.example.flight.dto;

import lombok.Data;
//mport org.antlr.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
public class FlightNotificationDto {
    private Long id;
    private String from;
    private LocalDateTime date;
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
    private String flightClass;
    private String baggage;
    private String fareBasis;
    private String operatedBy;
    private String marketedBy;
}

