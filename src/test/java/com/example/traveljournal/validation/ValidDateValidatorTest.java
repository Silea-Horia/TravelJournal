package com.example.traveljournal.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidDateValidatorTest {

    private ValidDateValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new ValidDateValidator();
    }

    @Test
    void testValidDate_ReturnsTrue() {
        // Arrange
        String validDate = "2023-05-10";

        // Act
        boolean result = validator.isValid(validDate, context);

        // Assert
        assertTrue(result);
        verifyNoInteractions(context); // Context not used
    }

    @Test
    void testNullDate_ReturnsFalse() {
        // Arrange
        String nullDate = null;

        // Act
        boolean result = validator.isValid(nullDate, context);

        // Assert
        assertFalse(result);
        verifyNoInteractions(context);
    }

    @Test
    void testInvalidDateFormat_ReturnsFalse() {
        // Arrange
        String invalidFormat = "05/10/2023"; // Wrong format

        // Act
        boolean result = validator.isValid(invalidFormat, context);

        // Assert
        assertFalse(result);
        verifyNoInteractions(context);
    }

    @Test
    void testInvalidMonth_ReturnsFalse() {
        // Arrange
        String invalidMonth = "2023-13-01"; // Month > 12

        // Act
        boolean result = validator.isValid(invalidMonth, context);

        // Assert
        assertFalse(result);
        verifyNoInteractions(context);
    }

    @Test
    void testLeapYearValidDate_ReturnsTrue() {
        // Arrange
        String leapYearDate = "2024-02-29"; // Valid leap year date

        // Act
        boolean result = validator.isValid(leapYearDate, context);

        // Assert
        assertTrue(result);
        verifyNoInteractions(context);
    }

    @Test
    void testEmptyString_ReturnsFalse() {
        // Arrange
        String emptyString = "";

        // Act
        boolean result = validator.isValid(emptyString, context);

        // Assert
        assertFalse(result);
        verifyNoInteractions(context);
    }

    @Test
    void testMalformedString_ReturnsFalse() {
        // Arrange
        String malformed = "2023-abc-01"; // Non-numeric month

        // Act
        boolean result = validator.isValid(malformed, context);

        // Assert
        assertFalse(result);
        verifyNoInteractions(context);
    }
}