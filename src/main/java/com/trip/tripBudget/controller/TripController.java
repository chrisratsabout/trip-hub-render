package com.trip.tripBudget.controller;

import com.trip.tripBudget.model.Trip;
import com.trip.tripBudget.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    TripService tripService;

    @GetMapping("")
    public List<Trip> getAllTrips(){
        return tripService.getAll();
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable int id){
        return tripService.getTripById(id);
    }

    @PostMapping("/addTrip")
    public Trip addTrip(@RequestBody Trip trip){
        return tripService.addTrip(trip);
    }

    @DeleteMapping("/{id}")
    public void deleteTripById(@PathVariable int id){
        tripService.deleteTripById(id);
    }
}
