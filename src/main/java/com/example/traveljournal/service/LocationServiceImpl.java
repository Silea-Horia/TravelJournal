package com.example.traveljournal.service;

import com.example.traveljournal.domain.Location;
import com.example.traveljournal.dto.LocationDto;
import com.example.traveljournal.exception.ResourceNotFoundException;
import com.example.traveljournal.mapper.LocationMapper;
import com.example.traveljournal.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<LocationDto> getAllLocations(String name, List<Integer> ratings) {
        Sort sort = Sort.by(Sort.Direction.DESC, "rating");

        List<Location> locations = locationRepository.findAll(sort);

        if (name != null && !name.isEmpty()) {
            locations = locations.stream()
                    .filter(location -> location.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        }

        if (ratings != null && !ratings.isEmpty()) {
            locations = locations.stream()
                    .filter(location -> ratings.contains(location.getRating()))
                    .toList();
        }

        return locations.stream()
                .map(LocationMapper::mapToLocationDto)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDto updateLocation(Long locationId, LocationDto updatedLocation) {
        Location location = LocationMapper.mapToLocation(getLocationById(locationId));

        location.setName(updatedLocation.getName());
        location.setDescription(updatedLocation.getDescription());
        location.setRating(updatedLocation.getRating());
        location.setDateVisited(updatedLocation.getDateVisited());

        return LocationMapper.mapToLocationDto(locationRepository.save(location));
    }

    @Override
    public LocationDto deleteLocation(Long locationId) {
        Location location = LocationMapper.mapToLocation(getLocationById(locationId));

        locationRepository.delete(location);

        return LocationMapper.mapToLocationDto(location);
    }

    @Override
    public LocationDto getLocationById(Long locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(
                () -> new ResourceNotFoundException("The location with the id " + locationId + " doesn't exist")
        );
        return LocationMapper.mapToLocationDto(location);
    }
}
