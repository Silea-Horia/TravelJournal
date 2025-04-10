package com.example.traveljournal.service;

import com.example.traveljournal.dto.LocationDto;

import java.util.List;

public interface LocationService {
    LocationDto createLocation(LocationDto locationDto);
    List<LocationDto> getAllLocations(String name, List<Integer> ratings);
    LocationDto getLocationById(Long locationId);
    LocationDto updateLocation(Long locationId, LocationDto updatedLocation);
    LocationDto deleteLocation(Long locationId);
}
