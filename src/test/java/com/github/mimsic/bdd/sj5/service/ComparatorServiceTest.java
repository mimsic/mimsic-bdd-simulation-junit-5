package com.github.mimsic.bdd.sj5.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComparatorServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparatorServiceTest.class);

    @Autowired
    private ComparatorService comparatorService;

    @ParameterizedTest
    @CsvSource({
            "1, 2, 1",
            "-2, -1, -1",
            "-1, 1, 0",
            "2, 1, -100",
            "0, 0, -100"
    })
    public void whenWithAnnotationProvidedParams_thenCompare(int low, int high, int expected) {

        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ComparatorServiceTestParameters.csv", numLinesToSkip = 1)
    public void whenWithCsvFileProvidedParams_thenCompare(int low, int high, int expected) {

        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

}