package com.trip.tripBudget.dao;

import com.trip.tripBudget.model.Trip;

import java.util.List;

public interface TripDao {

    public List<Trip> getAll();

    public Trip getTripById(int id);

    public Trip addTrip(Trip trip);

    public Trip updateTrip(Trip trip);

    public void deleteTripById(int id);
}
