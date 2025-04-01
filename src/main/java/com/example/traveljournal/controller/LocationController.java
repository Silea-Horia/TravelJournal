package com.example.traveljournal.controller;

import com.example.traveljournal.dto.LocationDto;
import com.example.traveljournal.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @PutMapping("{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable("id") Long locationId, @RequestBody LocationDto updatedLocation) {
        LocationDto savedLocation = locationService.updateLocation(locationId, updatedLocation);
        return ResponseEntity.ok(savedLocation);
    }
}
