package com.trip.tripBudget.controller;

import com.trip.tripBudget.model.Hotel;
import com.trip.tripBudget.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    HotelService hotelService;

    @GetMapping("")
    public List<Hotel> getAllHotels(){
        return hotelService.getAll();
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable int id){
        return hotelService.getHotelById(id);
    }

    @PostMapping("/add/{tripId}")
    public Hotel addHotel(@RequestBody Hotel hotel, @PathVariable int tripId){
        return hotelService.addHotel(hotel, tripId);
    }

    @PutMapping("/update/{hotelId}")
    public Hotel updateHotel(@RequestBody Hotel hotel, @PathVariable int hotelId){
        return hotelService.updateHotel(hotel, hotelId);
    }

    @DeleteMapping("/{id}")
    public void deleteHotelById(@PathVariable int id){
        hotelService.deleteHotelById(id);
    }
}
