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
    @DisplayName("Add two numbers")
    void add() {
        assertEquals(4, Calculator.add(2,2));
    }

    @Test
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
    void divide() {
        assertEquals(4, Calculator.divide(8,2));
    }
}