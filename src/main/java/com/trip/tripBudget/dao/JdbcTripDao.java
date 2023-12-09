package com.trip.tripBudget.dao;

import com.trip.tripBudget.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTripDao implements TripDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Trip> getAll() {
        List<Trip> tripList = new ArrayList<>();

        String sql = "SELECT * FROM trips;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                tripList.add(mapRowToTrip(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new RuntimeException("Cannot connect", e);
        }
        return tripList;
    }

    @Override
    public Trip getTripById(int id) {
       Trip trip = null;

       String sql = "SELECT * FROM trips WHERE trip_id = ?;";

       try {
           SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
           if(results.next()){
               trip = mapRowToTrip(results);
           }
       } catch (CannotGetJdbcConnectionException e){
           throw new RuntimeException("Cannot connect", e);
       }
       return trip;
    }

    @Override
    public Trip addTrip(Trip trip) {
        Trip newTrip = null;

        String sql = "INSERT INTO trips (trip_name, start_date, end_date) VALUES (?, ?, ?) RETURNING trip_id;";

        try {
            int newTripId = jdbcTemplate.queryForObject(sql, int.class, trip.getTripName(), trip.getStartDate(), trip.getEndDate());
            newTrip = getTripById(newTripId);
        } catch (CannotGetJdbcConnectionException e){
            throw new RuntimeException("Cannot connect", e);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity", e);
        }
        return newTrip;
    }


    @Override
    public Trip updateTrip(Trip trip) {
        return null;
    }

    @Override
    public void deleteTripById(int id) {
        String deleteFromTripHotel = "DELETE FROM trip_hotel WHERE trip_id = ?;";
        String deleteFromTripFlight = "DELETE FROM trip_flight WHERE trip_id = ?;";
        String sql = "DELETE FROM trips WHERE trip_id = ?;";
        jdbcTemplate.update(deleteFromTripFlight, id);
        jdbcTemplate.update(deleteFromTripHotel, id);
        jdbcTemplate.update(sql, id);

    }

    private Trip mapRowToTrip(SqlRowSet results){
        Trip trip = new Trip();
        trip.setTripId(results.getInt("trip_id"));
        trip.setTripName(results.getString("trip_name"));
        trip.setStartDate(results.getDate("start_date").toLocalDate());
        trip.setEndDate(results.getDate("end_date").toLocalDate());
        return trip;
    }
}
