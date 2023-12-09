package com.trip.tripBudget.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name="flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightId;
    private String departingFrom;
    private String arrivalTo;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private BigDecimal flightPrice;
}
