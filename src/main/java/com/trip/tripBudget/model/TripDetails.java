package com.trip.tripBudget.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table(name = "trips")
public class TripDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripId;
    private String tripName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer hotelId;
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfNights;
    private BigDecimal hotelPrice;
    private Integer flightId;
    private String departingFrom;
    private String arrivalTo;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private BigDecimal flightPrice;
}
