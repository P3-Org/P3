package com.aau.p3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    @DisplayName("Add two integers")
    void add() {
        assertEquals(4,Calculator.add(2,2));
    }

    @Test
    @DisplayName("Multiply tow integers")
    void multiply() {
        assertAll(
            () -> assertEquals(10,Calculator.multiply(5,2)),
            () -> assertEquals(100,Calculator.multiply(10,10)));
    }
    @Test
    @DisplayName("Divide tow integers")
    void divide() {
        assertAll(
                () -> assertEquals(4,Calculator.divide(6,2)),
                () -> assertEquals(1,Calculator.divide(10,10)));
    }
}