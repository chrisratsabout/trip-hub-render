package com.trip.tripBudget.controller;

import com.trip.tripBudget.model.TripDetails;
import com.trip.tripBudget.service.TripDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/trip-details")
public class TripDetailsController {

    @Autowired
    TripDetailsService tripDetailsService;

    @GetMapping("/{id}")
    public TripDetails getTripDetailsById(@PathVariable int id){
        return tripDetailsService.getTripDetailsById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTripById(@PathVariable int id) {
        tripDetailsService.deleteTripById(id);
    }
}
