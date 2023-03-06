package com.github.mimsic.bdd.sj5.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Regular Comparator Service Test")
public class RegularComparatorServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegularComparatorServiceTest.class);

    private final ComparatorService comparatorService;

    @Autowired
    public RegularComparatorServiceTest(ComparatorService comparatorService) {
        this.comparatorService = comparatorService;
    }

    @DisplayName("Compare with Annotation Provided Params")
    @ParameterizedTest(name = "-> index: [{index}], arguments: {argumentsWithNames}")
    @CsvSource({
            "1, 2, 1",
            "-2, -1, -1",
            "-1, 1, 0",
            "2, 1, -100",
            "0, 0, -100"
    })
    public void compareWithAnnotationProvidedParams(int low, int high, int expected) {

        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

    @DisplayName("Compare with CSV File Provided Params")
    @ParameterizedTest(name = "-> index: [{index}], arguments: {argumentsWithNames}")
    @CsvFileSource(resources = "/RegularComparatorServiceTest.param", numLinesToSkip = 1)
    public void compareWithCsvFileProvidedParams(int low, int high, int expected) {

        Assertions.assertEquals(expected, comparatorService.compare(low, high));
    }

}