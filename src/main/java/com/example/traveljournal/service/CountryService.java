package com.example.traveljournal.service;

import com.example.traveljournal.dto.CountryDto;

import java.util.List;

public interface CountryService {
    CountryDto createCountry(CountryDto countryDto);
    List<CountryDto> getAllCountries();
    CountryDto getCountryById(int id);
    CountryDto updateCountry(CountryDto countryDto);
    CountryDto deleteCountry(int id);
}
