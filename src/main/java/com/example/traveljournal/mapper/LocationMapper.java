package com.example.traveljournal.mapper;

import com.example.traveljournal.domain.Location;
import com.example.traveljournal.dto.LocationDto;

public class LocationMapper {
    public static Location mapToLocation(LocationDto locationDto) {
        return new Location(
                locationDto.getId(),
                locationDto.getName(),
                locationDto.getDateVisited(),
                locationDto.getRating(),
                locationDto.getDescription(),
                locationDto.getCountryId()
        );
    }

    public static LocationDto mapToLocationDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getName(),
                location.getDateVisited(),
                location.getRating(),
                location.getDescription(),
                location.getCountryId()
        );
    }
}
