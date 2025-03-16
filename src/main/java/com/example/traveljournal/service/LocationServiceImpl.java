package com.example.traveljournal.service;

import com.example.traveljournal.domain.Location;
import com.example.traveljournal.dto.LocationDto;
import com.example.traveljournal.mapper.LocationMapper;
import com.example.traveljournal.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = LocationMapper.mapToLocation(locationDto);

        Location savedLocation = locationRepository.save(location);

        return LocationMapper.mapToLocationDto(savedLocation);
    }
}
