package com.example.traveljournal.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LocationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        Location location = new Location();

        // Assert
        assertNull(location.getId());
        assertNull(location.getName());
        assertNull(location.getDateVisited());
        assertNull(location.getRating());
        assertNull(location.getDescription());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange & Act
        Location location = new Location(1L, "Paris", "2023-05-10", 4, "A beautiful city");

        // Assert
        assertEquals(1L, location.getId());
        assertEquals("Paris", location.getName());
        assertEquals("2023-05-10", location.getDateVisited());
        assertEquals(4, location.getRating());
        assertEquals("A beautiful city", location.getDescription());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Location location = new Location();

        // Act
        location.setId(2L);
        location.setName("Tokyo");
        location.setDateVisited("2023-06-15");
        location.setRating(5);
        location.setDescription("Amazing culture");

        // Assert
        assertEquals(2L, location.getId());
        assertEquals("Tokyo", location.getName());
        assertEquals("2023-06-15", location.getDateVisited());
        assertEquals(5, location.getRating());
        assertEquals("Amazing culture", location.getDescription());
    }

    @Test
    void testEntityAnnotation() {
        // Act & Assert
        assertTrue(Location.class.isAnnotationPresent(Entity.class));
        assertTrue(Location.class.isAnnotationPresent(Table.class));
        Table table = Location.class.getAnnotation(Table.class);
        assertEquals("locations", table.name());
    }

    @Test
    void testJsonIncludeNonDefault() throws Exception {
        // Arrange
        Location location = new Location();
        location.setName("Paris"); // Only set name, others remain default

        // Act
        String json = objectMapper.writeValueAsString(location);

        // Assert
        assertEquals("{\"name\":\"Paris\"}", json); // Only non-default field included
        assertFalse(json.contains("id"));
        assertFalse(json.contains("dateVisited"));
        assertFalse(json.contains("rating"));
        assertFalse(json.contains("description"));
    }
}
