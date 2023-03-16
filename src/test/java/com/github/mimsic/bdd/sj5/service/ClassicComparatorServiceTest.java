package com.github.mimsic.bdd.sj5.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Classic Comparator Service Test")
public class ClassicComparatorServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassicComparatorServiceTest.class);

    private final ComparatorService comparatorService;

    @Autowired
    public ClassicComparatorServiceTest(ComparatorService comparatorService) {
        this.comparatorService = comparatorService;
    }

    @DisplayName("when low greater than high then comparator returns -100")
    @Test
    public void when_low_greater_than_high_then_comparator_returns_negative_100() {

        int low = 2;
        int high = 1;
        int expected = -100;
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @DisplayName("when low equal to high then comparator returns -100")
    @Test
    public void when_low_equal_to_high_then_comparator_returns_negative_100() {

        int low = 2;
        int high = 2;
        int expected = -100;
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @DisplayName("when low greater than zero then comparator returns 1")
    @Test
    public void when_low_greater_than_zero_then_comparator_returns_1() {

        int low = 2;
        int high = 3;
        int expected = 1;
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @DisplayName("when low equal to zero then comparator returns 1")
    @Test
    public void when_low_equal_to_zero_then_comparator_returns_1() {

        int low = 0;
        int high = 1;
        int expected = 1;
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @DisplayName("When high less than zero then comparator returns -1")
    @Test
    public void when_high_less_than_zero_then_comparator_returns_negative_1() {

        int low = -2;
        int high = -1;
        int expected = -1;
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @DisplayName("When high equal to zero then comparator returns -1")
    @Test
    public void when_high_equal_to_zero_then_comparator_returns_negative_1() {

        int low = -1;
        int high = 0;
        int expected = -1;
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @DisplayName("When none of the conditions match then comparator returns 0")
    @Test
    public void when_none_of_the_conditions_match_then_comparator_returns_0() {

        int low = -1;
        int high = 1;
        int expected = 0;
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }
}
