package com.example.traveljournal.service;

import com.example.traveljournal.dto.LocationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    LocationDto createLocation(LocationDto locationDto);
    Page<LocationDto> getAllLocations(String name, List<Integer> ratings, Pageable pageable);
    LocationDto getLocationById(Long locationId);
    LocationDto updateLocation(Long locationId, LocationDto updatedLocation);
    LocationDto deleteLocation(Long locationId);
}
