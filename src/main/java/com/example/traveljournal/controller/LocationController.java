package com.example.traveljournal.controller;

import com.example.traveljournal.dto.LocationDto;
import com.example.traveljournal.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        LocationDto savedLocation = locationService.createLocation(locationDto);

        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations(@RequestParam(required = false) String name, @RequestParam(required = false) List<Integer> ratings) {
        List<LocationDto> locations = locationService.getAllLocations(name, ratings);

        return ResponseEntity.ok(locations);
    }

    @GetMapping("{id}") // Add this endpoint
    public ResponseEntity<LocationDto> getLocationById(@PathVariable("id") Long locationId) {
        LocationDto location = locationService.getLocationById(locationId);
        return ResponseEntity.ok(location);
    }

    @PutMapping("{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable("id") Long locationId, @RequestBody LocationDto updatedLocation) {
        LocationDto savedLocation = locationService.updateLocation(locationId, updatedLocation);

        return ResponseEntity.ok(savedLocation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable("id") Long locationId) {
        LocationDto deletedLocation = locationService.deleteLocation(locationId);

        return ResponseEntity.ok(deletedLocation);
    }
}
