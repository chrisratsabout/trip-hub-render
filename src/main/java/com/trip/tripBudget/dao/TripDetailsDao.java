package com.trip.tripBudget.dao;

import com.trip.tripBudget.model.TripDetails;

public interface TripDetailsDao {

    public TripDetails getTripDetailsById(int id);

    void deleteTripById(int id);
}
