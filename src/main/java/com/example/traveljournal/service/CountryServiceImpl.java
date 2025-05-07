package com.example.traveljournal.service;

import com.example.traveljournal.domain.Country;
import com.example.traveljournal.dto.CountryDto;
import com.example.traveljournal.exception.ResourceNotFoundException;
import com.example.traveljournal.mapper.CountryMapper;
import com.example.traveljournal.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public CountryDto createCountry(CountryDto countryDto) {
        Country country = CountryMapper.mapToCountry(countryDto);

        Country savedCountry = countryRepository.save(country);

        return CountryMapper.mapToCountryDto(savedCountry);
    }

    @Override
    public List<CountryDto> getAllCountries() {
        return countryRepository
                .findAll()
                .stream()
                .map(CountryMapper::mapToCountryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDto getCountryById(int id) {
        return CountryMapper.mapToCountryDto(countryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The country with the id " + id + " doesn't exist!")
        ));
    }

    @Override
    public CountryDto updateCountry(CountryDto countryDto) {
        Country country = CountryMapper.mapToCountry(countryDto);

        country.setName(countryDto.getName());

        return CountryMapper.mapToCountryDto(countryRepository.save(country));
    }

    @Override
    public CountryDto deleteCountry(int id) {
        Country country = CountryMapper.mapToCountry(getCountryById(id));

        countryRepository.delete(country);

        return CountryMapper.mapToCountryDto(country);
    }
}
