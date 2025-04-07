package com.example.traveljournal.service;

import com.example.traveljournal.domain.Location;
import com.example.traveljournal.dto.LocationDto;
import com.example.traveljournal.exception.ResourceNotFoundException;
import com.example.traveljournal.mapper.LocationMapper;
import com.example.traveljournal.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
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

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    @Override
    public Page<LocationDto> getPage(Integer page, Integer size, String name, List<Integer> ratings) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "rating"));

        Page<Location> locationPage;
        if ((name == null || name.isEmpty()) && (ratings == null || ratings.isEmpty())) {
            // No filters, just paginate all
            locationPage = locationRepository.findAll(pageable);
        } else {
            // Apply filters using repository query methods or Specifications
            if (name != null && !name.isEmpty() && ratings != null && !ratings.isEmpty()) {
                locationPage = locationRepository.findByNameContainingIgnoreCaseAndRatingIn(name, ratings, pageable);
            } else if (name != null && !name.isEmpty()) {
                locationPage = locationRepository.findByNameContainingIgnoreCase(name, pageable);
            } else {
                locationPage = locationRepository.findByRatingIn(ratings, pageable);
            }
        }

        return locationPage.map(LocationMapper::mapToLocationDto);
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
