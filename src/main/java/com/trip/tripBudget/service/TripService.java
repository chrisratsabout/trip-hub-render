package com.trip.tripBudget.service;

import com.trip.tripBudget.dao.JdbcTripDao;
import com.trip.tripBudget.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {
    @Autowired
    JdbcTripDao jdbcTripDao;

    public List<Trip> getAll() {
        return jdbcTripDao.getAll();
    }

    public Trip addTrip(Trip trip) {
        return jdbcTripDao.addTrip(trip);
    }

    public Trip getTripById(int id) {
        return jdbcTripDao.getTripById(id);
    }

    public void deleteTripById(int id) {
        jdbcTripDao.deleteTripById(id);
    }
}
