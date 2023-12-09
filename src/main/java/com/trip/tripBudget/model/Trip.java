package com.trip.tripBudget.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer tripId;
    private String tripName;
    private LocalDate startDate;
    private LocalDate endDate;
}
