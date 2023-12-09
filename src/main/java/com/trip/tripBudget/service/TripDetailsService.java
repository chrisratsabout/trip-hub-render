package com.trip.tripBudget.service;

import com.trip.tripBudget.dao.JdbcTripDetailsDao;
import com.trip.tripBudget.model.TripDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripDetailsService {

    @Autowired
    JdbcTripDetailsDao jdbcTripDetailsDao;
    public TripDetails getTripDetailsById(int id) {
        return jdbcTripDetailsDao.getTripDetailsById(id);
    }

    public void deleteTripById(int id) {
        jdbcTripDetailsDao.deleteTripById(id);
    }
}
