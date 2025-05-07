package com.example.traveljournal.mapper;

import com.example.traveljournal.domain.Country;
import com.example.traveljournal.domain.Location;
import com.example.traveljournal.dto.CountryDto;
import com.example.traveljournal.dto.LocationDto;

public class CountryMapper {
    public static Country mapToCountry(CountryDto countryDto) {
        return new Country(
                countryDto.getCountryId(),
                countryDto.getName()
        );
    }

    public static CountryDto mapToCountryDto(Country country) {
        return new CountryDto(
                country.getCountryId(),
                country.getName()
        );
    }
}
