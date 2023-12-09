package com.trip.tripBudget.dao;


import com.trip.tripBudget.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcHotelDao implements HotelDao{
    @Autowired
    JdbcTemplate jdbcTemplate;



    @Override
    public List<Hotel> getAll() {
        List<Hotel> hotelList = new ArrayList<>();

        String sql = "SELECT * FROM hotels ORDER BY hotel_id;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                hotelList.add(mapRowToHotel(results));
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Cannot connect", e);
        }
        return hotelList;
    }

    @Override
    public Hotel getHotelById(int id) {
        Hotel hotel = null;

        String sql = "SELECT * FROM hotels WHERE hotel_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                hotel = mapRowToHotel(results);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Cannot connect", e);
        }

        return hotel;
    }

    @Override
    public Hotel addHotel(Hotel hotel, int tripId) {
        Hotel newHotel = null;

        String sql = "INSERT INTO hotels (hotel_price, hotel_name, number_of_nights, check_in_date, checkout_date) VALUES (?, ?, ?, ?, ?) RETURNING hotel_id;";

        try {
            int newHotelId = jdbcTemplate.queryForObject(sql, int.class, hotel.getHotelPrice(), hotel.getHotelName(), hotel.getNumberOfNights(), hotel.getCheckInDate(), hotel.getCheckOutDate());
            newHotel = getHotelById(newHotelId);
            String sql2 = "INSERT INTO trip_hotel (trip_id, hotel_id) VALUES (?, ?);";
            jdbcTemplate.update(sql2, tripId, newHotelId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("cannot connect", e);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity", e);
        }
        return newHotel;
    }

    @Override
    public Hotel updateHotel(Hotel hotel, int hotelId) {
        Hotel updatedHotel = null;

        String sql = "UPDATE hotels SET hotel_price = ?, check_in_date = ?, checkout_date = ?, hotel_name = ?, number_of_nights = ? " +
                "WHERE hotel_id = ?;";

        try {
            int numberOfRows = jdbcTemplate.update(sql, hotel.getHotelPrice(), hotel.getCheckInDate(), hotel.getCheckOutDate(), hotel.getHotelName(), hotel.getNumberOfNights(), hotel.getHotelId());

            if (numberOfRows == 0) {
                throw new RuntimeException("Zero rows affected");
            } else {
                updatedHotel = getHotelById(hotel.getHotelId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("cannot connect", e);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity", e);
        }

        return updatedHotel;
    }

    @Override
    public void deleteHotelById(int id) {
        String deleteFromTripHotel = "DELETE FROM trip_hotel WHERE hotel_id = ?;";
        String sql = "DELETE FROM hotels WHERE hotel_id = ?;";
        jdbcTemplate.update(deleteFromTripHotel, id);
        jdbcTemplate.update(sql, id);
    }

    private Hotel mapRowToHotel(SqlRowSet results) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(results.getInt("hotel_id"));
        hotel.setHotelName(results.getString("hotel_name"));
        hotel.setHotelPrice(results.getBigDecimal("hotel_price"));
        hotel.setNumberOfNights(results.getInt("number_of_nights"));
        hotel.setCheckInDate(results.getDate("check_in_date").toLocalDate());
        hotel.setCheckOutDate(results.getDate("checkout_date").toLocalDate());
        return hotel;
    }
}
