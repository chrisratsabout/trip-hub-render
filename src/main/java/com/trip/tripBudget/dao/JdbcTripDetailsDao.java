package com.trip.tripBudget.dao;


import com.trip.tripBudget.model.TripDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTripDetailsDao implements TripDetailsDao{

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public TripDetails getTripDetailsById(int id) {
        TripDetails tripDetails = null;

       String sql = "SELECT * FROM trips " +
               "JOIN trip_flight ON trips.trip_id = trip_flight.trip_id " +
               "JOIN trip_hotel ON trips.trip_id = trip_hotel.trip_id " +
               "JOIN hotels ON trip_hotel.hotel_id = hotels.hotel_id " +
               "JOIN flights ON trip_flight.flight_id = flights.flight_id " +
               "WHERE trips.trip_id= ? " +
               "ORDER BY flights.flight_id, hotels.hotel_id DESC LIMIT 1;";

       try {

           SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
           if(results.next()){
               tripDetails = mapRowToTripDetails(results);
           }

       } catch (CannotGetJdbcConnectionException e){
           throw new RuntimeException("Cannot connect", e);
        }

       return tripDetails;
    }

    @Override
    public void deleteTripById(int id) {
        String deleteFromTripFlight = "DELETE FROM trip_flight WHERE trip_id = ?;";
        String deleteFromTripHotel = "DELETE FROM trip_hotel WHERE trip_id = ?;";
        String deleteFromTrips = "DELETE FROM trips WHERE trip_id = ?;";
        jdbcTemplate.update(deleteFromTripFlight, id);
        jdbcTemplate.update(deleteFromTripHotel, id);
        jdbcTemplate.update(deleteFromTrips, id);
    }

    private TripDetails mapRowToTripDetails(SqlRowSet results){
        TripDetails tripDetails = new TripDetails();
        tripDetails.setTripId(results.getInt("trip_id"));
        tripDetails.setTripName(results.getString("trip_name"));
        tripDetails.setStartDate(results.getDate("start_date").toLocalDate());
        tripDetails.setEndDate(results.getDate("end_date").toLocalDate());
        tripDetails.setHotelId(results.getInt("hotel_id"));
        tripDetails.setHotelName(results.getString("hotel_name"));
        tripDetails.setCheckInDate(results.getDate("check_in_date").toLocalDate());
        tripDetails.setCheckOutDate(results.getDate("checkout_date").toLocalDate());
        tripDetails.setNumberOfNights(results.getInt("number_of_nights"));
        tripDetails.setHotelPrice(results.getBigDecimal("hotel_price"));
        tripDetails.setFlightId(results.getInt("flight_id"));
        tripDetails.setDepartingFrom(results.getString("departing_from"));
        tripDetails.setArrivalTo(results.getString("arrival_to"));
        tripDetails.setDepartureDate(results.getDate("departure_date").toLocalDate());
        tripDetails.setReturnDate(results.getDate("return_date").toLocalDate());
        tripDetails.setFlightPrice(results.getBigDecimal("flight_price"));
        return tripDetails;


    }

}
