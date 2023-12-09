package com.trip.tripBudget.dao;

import com.trip.tripBudget.model.Hotel;

import java.util.List;

public interface HotelDao {
    public List<Hotel> getAll();

    public Hotel getHotelById(int id);

    public Hotel addHotel(Hotel hotel, int tripId);

    public Hotel updateHotel(Hotel hotel, int hotelId);

    public void deleteHotelById(int id);
}
