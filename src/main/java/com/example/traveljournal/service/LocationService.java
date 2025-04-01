package com.example.traveljournal.service;

import com.example.traveljournal.dto.LocationDto;

import java.util.List;

public interface LocationService {
    LocationDto createLocation(LocationDto locationDto);
    List<LocationDto> getAllLocations();
    LocationDto updateLocation(Long locationId, LocationDto updatedLocation);
    LocationDto deleteLocation(Long locationId);
}
