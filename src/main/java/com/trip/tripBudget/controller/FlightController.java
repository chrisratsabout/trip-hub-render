package com.trip.tripBudget.controller;

import com.trip.tripBudget.model.Flight;
import com.trip.tripBudget.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @GetMapping("")
    public List<Flight> getAllFlights(){
        return flightService.getAll();
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable int id){
        return flightService.getFlightById(id);
    }

    @PostMapping("/add/{tripId}")
    public Flight addFlight(@RequestBody Flight flight, @PathVariable int tripId){
        return flightService.addFlight(flight, tripId);
    }

    @PutMapping("/update/{flightId}")
    public Flight updateFlight(@RequestBody Flight flight,@PathVariable int flightId ){
        return flightService.updateFlight(flight, flightId);
    }

    @DeleteMapping("/{id}")
    public void deleteFlightById(@PathVariable int id){
        flightService.deleteFlightById(id);
    }
}
