package com.github.mimsic.bdd.sj5.orderer;

import com.github.mimsic.bdd.sj5.annotation.Given;
import com.github.mimsic.bdd.sj5.annotation.Story;
import com.github.mimsic.bdd.sj5.annotation.Then;
import com.github.mimsic.bdd.sj5.annotation.When;
import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimulatorMethodOrderer implements MethodOrderer {

    private static final String DEFAULT_PATH = "";

    @Override
    public void orderMethods(MethodOrdererContext methodOrdererContext) {

        Class<?> testClass = methodOrdererContext.getTestClass();
        String path = Optional.ofNullable(testClass.getAnnotation(Story.class))
                .map(Story::path)
                .orElse(DEFAULT_PATH);

        try (InputStream inputStream = testClass.getClassLoader().getResourceAsStream(path)) {
            assert inputStream != null;
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                List<? extends MethodDescriptor> unorderedDescriptors = methodOrdererContext.getMethodDescriptors();
                List<MethodDescriptor> orderedDescriptors = new ArrayList<>();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    methodOrderer(unorderedDescriptors, orderedDescriptors, line);
                }
                unorderedDescriptors.clear();
                orderedDescriptors.forEach(methodDescriptor -> {
                    unorderedDescriptors.add(convert(methodDescriptor));
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void methodOrderer(
            List<? extends MethodDescriptor> unorderedDescriptors,
            List<MethodDescriptor> orderedDescriptors,
            String line) {

        unorderedDescriptors.stream()
                .filter((MethodDescriptor methodDescriptor) ->
                        isGiven(methodDescriptor, line)
                                || isWhen(methodDescriptor, line)
                                || isThen(methodDescriptor, line))
                .findFirst()
                .ifPresent((MethodDescriptor methodDescriptor) -> {
                    orderedDescriptors.add(methodDescriptor);
                    unorderedDescriptors.remove(methodDescriptor);
                });
    }

    private static boolean isGiven(MethodDescriptor methodDescriptor, String line) {
        Given givenAnnotation = methodDescriptor.getMethod().getAnnotation(Given.class);
        return givenAnnotation != null && givenAnnotation.description().equals(line);
    }

    private static boolean isWhen(MethodDescriptor methodDescriptor, String line) {
        When whenAnnotation = methodDescriptor.getMethod().getAnnotation(When.class);
        return whenAnnotation != null && whenAnnotation.description().equals(line);
    }

    private static boolean isThen(MethodDescriptor methodDescriptor, String line) {
        Then thenAnnotation = methodDescriptor.getMethod().getAnnotation(Then.class);
        return thenAnnotation != null && thenAnnotation.description().equals(line);
    }

    @SuppressWarnings("unchecked")
    private static <T extends MethodDescriptor> T convert(MethodDescriptor descriptor) {
        return (T) descriptor;
    }

    @SuppressWarnings("unchecked")
    private static <T extends MethodDescriptor> void add(List<T> descriptors, MethodDescriptor descriptor) {
        descriptors.add((T) descriptor);
    }
}
