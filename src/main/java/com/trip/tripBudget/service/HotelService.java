package com.trip.tripBudget.service;

import com.trip.tripBudget.dao.JdbcHotelDao;
import com.trip.tripBudget.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HotelService {

    @Autowired
    JdbcHotelDao jdbcHotelDao;


    public List<Hotel> getAll() {
        return jdbcHotelDao.getAll();
    }

    public Hotel getHotelById(int id) {
        return jdbcHotelDao.getHotelById(id);
    }

    public Hotel addHotel(Hotel hotel, int tripId){
        return jdbcHotelDao.addHotel(hotel, tripId);
    }

    public void deleteHotelById(int id) {
        jdbcHotelDao.deleteHotelById(id);
    }

    public Hotel updateHotel(Hotel hotel, int hotelId) {
        return jdbcHotelDao.updateHotel(hotel, hotelId);
    }
}
