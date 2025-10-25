package com.aau.p3;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class YearInFiveYearsTest {

    @Test
    void calcYear() {
        // Arrange
        FetchAPI mockAPI = mock(FetchAPI.class);

        when(mockAPI.fetchYear()).thenReturn(2050);

        YearInFiveYears yearInFiveYears = new YearInFiveYears(mockAPI);

        // Act
        int result = yearInFiveYears.CalcYear();

        // Assert
        assertEquals(2055, result);
    }
}