package com.trip.tripBudget.service;

import com.trip.tripBudget.dao.FlightDao;
import com.trip.tripBudget.dao.JdbcFlightDao;
import com.trip.tripBudget.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    @Autowired
    JdbcFlightDao jdbcFlightDao;


    public List<Flight> getAll() {
        return jdbcFlightDao.getAll();
    }

    public Flight getFlightById(int id) {
        return jdbcFlightDao.getFlightById(id);
    }

    public Flight addFlight(Flight flight, int tripId){
        return jdbcFlightDao.addFlight(flight, tripId);
    }

    public void deleteFlightById(int id) {
        jdbcFlightDao.deleteFlightById(id);
    }

    public Flight updateFlight(Flight flight, int flightId) {
        return jdbcFlightDao.updateFlight(flight, flightId);
    }
}
