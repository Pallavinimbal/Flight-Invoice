package com.example.flight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flightdb1")
public class FlightNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "sender")
    private String from;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, name = "passenger_email")
    private String to;  // passengerEmail

    @Column(nullable = false)
    private String passengerName;

    @Column(nullable = false)
    private String bookingReference;

    @Column(nullable = false)
    private String ticketNumber;

    @Column(nullable = false)
    private String issuingOffice;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    @Column(nullable = false)
    private LocalDateTime flightDate;

    @Column(nullable = false)
    private String departureCity;

    @Column(nullable = false)
    private String arrivalCity;

    @Column(nullable = false)
    private String flightClass;

    @Column(nullable = false)
    private String baggage;

    @Column(nullable = false)
    private String fareBasis;

    @Column(nullable = false)
    private String operatedBy;

    @Column(nullable = false)
    private String marketedBy;

    @Column(nullable = true)
    private String departureAirport;

    @Column(nullable = true)
    private String arrivalAirport;

    @Column(nullable = true)
    private String terminal;

    @Column(nullable = true)
    private String nvbDate;

    @Column(nullable = true)
    private String flightDuration;

    @Column(nullable = true)
    private String bookingStatus1;

    @Column(nullable = true)
    private String nvaDate;
}
