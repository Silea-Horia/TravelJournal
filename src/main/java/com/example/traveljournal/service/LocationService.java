package com.example.traveljournal.service;

import com.example.traveljournal.dto.LocationDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LocationService {
    LocationDto createLocation(LocationDto locationDto);
    Page<LocationDto> getPage(Integer page, Integer size, String name, List<Integer> ratings);
    LocationDto getLocationById(Long locationId);
    LocationDto updateLocation(Long locationId, LocationDto updatedLocation);
    LocationDto deleteLocation(Long locationId);
}
