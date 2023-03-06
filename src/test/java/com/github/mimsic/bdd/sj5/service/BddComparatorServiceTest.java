package com.github.mimsic.bdd.sj5.service;

import com.github.mimsic.bdd.sj5.annotation.Given;
import com.github.mimsic.bdd.sj5.annotation.Story;
import com.github.mimsic.bdd.sj5.annotation.Then;
import com.github.mimsic.bdd.sj5.annotation.When;
import com.github.mimsic.bdd.sj5.generator.SimulatorDisplayNameGenerator;
import com.github.mimsic.bdd.sj5.orderer.SimulatorOrderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Story(description = "Bdd Comparator Service Test", path = "BddComparatorServiceTest.story")
@TestMethodOrder(SimulatorOrderer.class)
@DisplayNameGeneration(SimulatorDisplayNameGenerator.class)
public class BddComparatorServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BddComparatorServiceTest.class);
    private final ComparatorService comparatorService;

    @Autowired
    public BddComparatorServiceTest(ComparatorService comparatorService) {
        this.comparatorService = comparatorService;
    }

    @Given(description = "Given a comparator that accepts 2 values low and high")
    @ValueSource(strings = {""})
    public void given_a_comparator_that_accepts_two_values_low_and_high(String value) {
        Assertions.assertNotNull(comparatorService);
    }

    @When(description = "When low greater than or equal to high")
    @CsvSource({"2, 1", "2, 2"})
    public void when_low_greater_than_or_equal_to_high(int low, int high) {
        Assertions.assertTrue(low >= high);
    }

    @Then(description = "Then comparator returns -100")
    @CsvSource({"2, 1, -100", "2, 2, -100"})
    public void then_comparator_returns_negative_100(int low, int high, int expected) {
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @When(description = "When low greater than or equal to zero")
    @CsvSource({"1, 2", "0, 2"})
    public void when_low_greater_than_or_equal_to_zero(int low, int high) {
        Assertions.assertTrue(low >= 0);
    }

    @Then(description = "Then comparator returns 1")
    @CsvSource({"1, 2, 1", "0, 2, 1"})
    public void then_comparator_returns_1(int low, int high, int expected) {
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @When(description = "When high less than or equal to zero")
    @CsvSource({"-3, -2", "-1, 0"})
    public void when_high_less_than_or_equal_to_zero(int low, int high) {
        Assertions.assertTrue(high <= 0);
    }

    @Then(description = "Then comparator returns -1")
    @CsvSource({"-3, -2, -1", "-1, 0, -1"})
    public void then_comparator_returns_negative_1(int low, int high, int expected) {
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @When(description = "When none of the conditions match")
    @CsvSource({"-3, 2"})
    public void when_none_of_the_conditions_match(int low, int high) {
        Assertions.assertTrue(low < 0);
        Assertions.assertTrue(high > 0);
    }

    @Then(description = "Then comparator returns 0")
    @CsvSource({"-3, 2, 0"})
    public void then_comparator_returns_0(int low, int high, int expected) {
        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }
}