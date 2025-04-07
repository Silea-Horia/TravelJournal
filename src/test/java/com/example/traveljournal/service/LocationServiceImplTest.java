package com.example.traveljournal.service;

import com.example.traveljournal.domain.Location;
import com.example.traveljournal.dto.LocationDto;
import com.example.traveljournal.exception.ResourceNotFoundException;
import com.example.traveljournal.mapper.LocationMapper;
import com.example.traveljournal.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;
    private LocationDto locationDto;

    @BeforeEach
    void setUp() {
        location = new Location(1L, "Paris", "2023-05-10", 4, "A beautiful city");
        locationDto = new LocationDto(1L, "Paris", "2023-05-10", 4, "A beautiful city");
    }

    @Test
    void createLocation_Success() {
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationDto result = locationService.createLocation(locationDto);

        assertNotNull(result);
        assertEquals("Paris", result.getName());
        assertEquals(4, result.getRating());
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void getAllLocations_NoFilters_Success() {
        List<Location> locations = Arrays.asList(
                new Location(2L, "London", "2023-06-15", 5, "Great city"),
                new Location(1L, "Paris", "2023-05-10", 4, "A beautiful city")
        );
        when(locationRepository.findAll(Sort.by(Sort.Direction.DESC, "rating"))).thenReturn(locations);

        List<LocationDto> result = locationService.getAllLocations(null, null);

        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getRating());
        assertEquals("London", result.get(0).getName());
        assertEquals(4, result.get(1).getRating());
        assertEquals("Paris", result.get(1).getName());
        verify(locationRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "rating"));
    }

    @Test
    void getAllLocations_WithNameFilter_Success() {
        List<Location> locations = Arrays.asList(
                new Location(1L, "Paris", "2023-05-10", 4, "A beautiful city"),
                new Location(2L, "London", "2023-06-15", 5, "Great city")
        );
        when(locationRepository.findAll(Sort.by(Sort.Direction.DESC, "rating"))).thenReturn(locations);

        List<LocationDto> result = locationService.getAllLocations("par", null);

        assertEquals(1, result.size());
        assertEquals("Paris", result.getFirst().getName());
        verify(locationRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "rating"));
    }

    @Test
    void getAllLocations_WithRatingsFilter_Success() {
        List<Location> locations = Arrays.asList(
                new Location(1L, "Paris", "2023-05-10", 4, "A beautiful city"),
                new Location(2L, "London", "2023-06-15", 5, "Great city")
        );
        when(locationRepository.findAll(Sort.by(Sort.Direction.DESC, "rating"))).thenReturn(locations);

        List<LocationDto> result = locationService.getAllLocations(null, List.of(4));

        assertEquals(1, result.size());
        assertEquals(4, result.getFirst().getRating());
        assertEquals("Paris", result.getFirst().getName());
        verify(locationRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "rating"));
    }

    @Test
    void getLocationById_Success() {
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));

        LocationDto result = locationService.getLocationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Paris", result.getName());
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    void getLocationById_NotFound_ThrowsException() {
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            locationService.getLocationById(1L);
        });
        assertEquals("The location with the id 1 doesn't exist", exception.getMessage());
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    void updateLocation_Success() {
        Location existingLocation = new Location(1L, "Old Name", "2023-01-01", 3, "Old desc");
        when(locationRepository.findById(1L)).thenReturn(Optional.of(existingLocation));
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationDto result = locationService.updateLocation(1L, locationDto);

        assertNotNull(result);
        assertEquals("Paris", result.getName());
        assertEquals(4, result.getRating());
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void deleteLocation_Success() {
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        doNothing().when(locationRepository).delete(any(Location.class));

        LocationDto result = locationService.deleteLocation(1L);

        assertNotNull(result);
        assertEquals("Paris", result.getName());
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).delete(any(Location.class));
    }
}