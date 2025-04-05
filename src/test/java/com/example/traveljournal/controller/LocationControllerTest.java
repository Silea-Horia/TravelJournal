package com.example.traveljournal.controller;

import com.example.traveljournal.dto.LocationDto;
import com.example.traveljournal.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private LocationDto locationDto;

    @BeforeEach
    void setUp() {
        // Initialize MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
        objectMapper = new ObjectMapper();
        locationDto = new LocationDto(1L, "Paris", "2023-05-10", 4, "A beautiful city");
    }

    @Test
    void createLocation_Success() throws Exception {
        when(locationService.createLocation(any(LocationDto.class))).thenReturn(locationDto);

        String result = mockMvc.perform(post("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        LocationDto responseDto = objectMapper.readValue(result, LocationDto.class);
        assertNotNull(responseDto);
        assertEquals("Paris", responseDto.getName());
        assertEquals(4, responseDto.getRating());
        verify(locationService, times(1)).createLocation(any(LocationDto.class));
    }

    @Test
    void getAllLocations_NoFilters_Success() throws Exception {
        List<LocationDto> locations = Arrays.asList(
                new LocationDto(2L, "London", "2023-06-15", 5, "Great city"),
                new LocationDto(1L, "Paris", "2023-05-10", 4, "A beautiful city")
        );
        when(locationService.getAllLocations(null, null)).thenReturn(locations);

        String result = mockMvc.perform(get("/api/locations"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<LocationDto> responseList = Arrays.asList(objectMapper.readValue(result, LocationDto[].class));
        assertEquals(2, responseList.size());
        assertEquals(5, responseList.get(0).getRating());
        assertEquals("London", responseList.get(0).getName());
        assertEquals(4, responseList.get(1).getRating());
        assertEquals("Paris", responseList.get(1).getName());
        verify(locationService, times(1)).getAllLocations(null, null);
    }

    @Test
    void getAllLocations_WithNameFilter_Success() throws Exception {
        List<LocationDto> locations = Arrays.asList(
                new LocationDto(1L, "Paris", "2023-05-10", 4, "A beautiful city")
        );
        when(locationService.getAllLocations("par", null)).thenReturn(locations);

        String result = mockMvc.perform(get("/api/locations")
                        .param("name", "par"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<LocationDto> responseList = Arrays.asList(objectMapper.readValue(result, LocationDto[].class));
        assertEquals(1, responseList.size());
        assertEquals("Paris", responseList.get(0).getName());
        verify(locationService, times(1)).getAllLocations("par", null);
    }

    @Test
    void getAllLocations_WithRatingsFilter_Success() throws Exception {
        List<LocationDto> locations = Arrays.asList(
                new LocationDto(1L, "Paris", "2023-05-10", 4, "A beautiful city")
        );
        when(locationService.getAllLocations(null, List.of(4))).thenReturn(locations);

        String result = mockMvc.perform(get("/api/locations")
                        .param("ratings", "4"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<LocationDto> responseList = Arrays.asList(objectMapper.readValue(result, LocationDto[].class));
        assertEquals(1, responseList.size());
        assertEquals(4, responseList.get(0).getRating());
        assertEquals("Paris", responseList.get(0).getName());
        verify(locationService, times(1)).getAllLocations(null, List.of(4));
    }

    @Test
    void getLocationById_Success() throws Exception {
        when(locationService.getLocationById(1L)).thenReturn(locationDto);

        String result = mockMvc.perform(get("/api/locations/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        LocationDto responseDto = objectMapper.readValue(result, LocationDto.class);
        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getId());
        assertEquals("Paris", responseDto.getName());
        verify(locationService, times(1)).getLocationById(1L);
    }

    @Test
    void updateLocation_Success() throws Exception {
        when(locationService.updateLocation(eq(1L), any(LocationDto.class))).thenReturn(locationDto);

        String result = mockMvc.perform(put("/api/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        LocationDto responseDto = objectMapper.readValue(result, LocationDto.class);
        assertNotNull(responseDto);
        assertEquals("Paris", responseDto.getName());
        assertEquals(4, responseDto.getRating());
        verify(locationService, times(1)).updateLocation(eq(1L), any(LocationDto.class));
    }

    @Test
    void deleteLocation_Success() throws Exception {
        when(locationService.deleteLocation(1L)).thenReturn(locationDto);

        String result = mockMvc.perform(delete("/api/locations/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        LocationDto responseDto = objectMapper.readValue(result, LocationDto.class);
        assertNotNull(responseDto);
        assertEquals("Paris", responseDto.getName());
        verify(locationService, times(1)).deleteLocation(1L);
    }
}