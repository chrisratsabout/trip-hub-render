package com.trip.tripBudget.dao;

import com.trip.tripBudget.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFlightDao implements FlightDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Flight> getAll() {
        List<Flight> flightList = new ArrayList<>();

        String sql = "SELECT * FROM flights ORDER BY flight_id;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                flightList.add(mapRowToFlight(results));
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Cannot connect", e);
        }
        return flightList;
    }

    @Override
    public Flight getFlightById(int id) {
        Flight flight = null;

        String sql = "SELECT * FROM flights WHERE flight_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                flight = mapRowToFlight(results);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Cannot connect", e);
        }

        return flight;
    }

    @Override
    public Flight addFlight(Flight flight, int tripId) {
        Flight newFlight = null;

        String sql = "INSERT INTO flights (flight_price, arrival_to, departing_from, departure_date, return_date) VALUES (?, ?, ?, ?, ?) RETURNING flight_id;";
        String sql2 = "INSERT INTO trip_flight (trip_id, flight_id) VALUES (?, ?);";
        try {
            int newFlightId = jdbcTemplate.queryForObject(sql, int.class, flight.getFlightPrice(), flight.getArrivalTo(), flight.getDepartingFrom(), flight.getDepartureDate(), flight.getReturnDate());
            newFlight = getFlightById(newFlightId);
            jdbcTemplate.update(sql2, tripId, newFlightId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("cannot connect", e);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity", e);
        }
        return newFlight;
    }

    @Override
    public Flight updateFlight(Flight flight, int flightId) {
        Flight updatedFlight = null;

        String sql = "UPDATE flights SET flight_price = ?, departure_date = ?, return_date = ?, departing_from = ?, arrival_to = ? " +
                "WHERE flight_id = ?;";

        try {
            int numberOfRows = jdbcTemplate.update(sql, flight.getFlightPrice(), flight.getDepartureDate(), flight.getReturnDate(), flight.getDepartingFrom(), flight.getArrivalTo(), flight.getFlightId());

            if (numberOfRows == 0) {
                throw new RuntimeException("Zero row affected");
            } else {
                updatedFlight = getFlightById(flight.getFlightId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("cannot connect", e);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity", e);
        }

        return updatedFlight;

    }

    @Override
    public void deleteFlightById(int id) {
        String deleteFromTripFlight = "DELETE FROM trip_flight WHERE flight_id = ?;";
        String sql = "DELETE FROM flights WHERE flight_id = ?;";
        jdbcTemplate.update(deleteFromTripFlight, id);
        jdbcTemplate.update(sql, id);
    }

    private Flight mapRowToFlight(SqlRowSet results) {
        Flight flight = new Flight();
        flight.setFlightId(results.getInt("flight_id"));
        flight.setFlightPrice(results.getBigDecimal("flight_price"));
        flight.setArrivalTo(results.getString("arrival_to"));
        flight.setDepartingFrom(results.getString("departing_from"));
        flight.setDepartureDate(results.getDate("departure_date").toLocalDate());
        flight.setReturnDate(results.getDate("return_date").toLocalDate());
        return flight;
    }
}
