package com.aau.p3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @BeforeEach void test(){
        System.out.println("Testing!");
    }

    @Test
    @DisplayName("Add two integers")
    void add() {
        assertEquals(4, Calculator.add(2,2));
    }

    @Test
    @DisplayName("Multiply tow integers")
    void multiply() {
        assertAll(
        () -> assertEquals(-4,Calculator.multiply(2,-2)),
        () -> assertEquals(8, Calculator.multiply(2,4)),
        () -> assertEquals(16, Calculator.multiply(4,4)));
    }

    @Test
    void subtract() {
        assertEquals(4, Calculator.subtract(6,2));
    }

    @Test
    @DisplayName("Divide tow integers")
    void divide() {
        assertAll(
                () -> assertEquals(3,Calculator.divide(6,2)),
                () -> assertEquals(1,Calculator.divide(10,10)));
    }
}
