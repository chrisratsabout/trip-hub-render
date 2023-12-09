package com.trip.tripBudget.dao;

import com.trip.tripBudget.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightDao {
    public List<Flight> getAll();

    public Flight getFlightById(int id);

    public Flight addFlight(Flight flight, int id);

    public Flight updateFlight(Flight flight, int flightId);

    public void deleteFlightById(int id);

}
