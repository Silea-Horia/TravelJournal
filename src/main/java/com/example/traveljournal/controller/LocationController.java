package com.example.traveljournal.controller;

import com.example.traveljournal.dto.LocationDto;
import com.example.traveljournal.service.LocationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private LocationService locationService;

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationDto locationDto) {
        LocationDto savedLocation = locationService.createLocation(locationDto);

        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<LocationDto>> getAllLocations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Integer> ratings,
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "rating"));
        Page<LocationDto> locations = locationService.getAllLocations(name, ratings, pageable);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable("id") Long locationId) {
        LocationDto location = locationService.getLocationById(locationId);
        return ResponseEntity.ok(location);
    }

    @PutMapping("{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable("id") Long locationId, @Valid @RequestBody LocationDto updatedLocation) {
        LocationDto savedLocation = locationService.updateLocation(locationId, updatedLocation);

        return ResponseEntity.ok(savedLocation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable("id") Long locationId) {
        LocationDto deletedLocation = locationService.deleteLocation(locationId);

        return ResponseEntity.ok(deletedLocation);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
