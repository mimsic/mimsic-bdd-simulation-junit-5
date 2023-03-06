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

public class SimulatorOrderer implements MethodOrderer {

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
                    methodAnnotation(unorderedDescriptors, orderedDescriptors, line);
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

    private void methodAnnotation(
            List<? extends MethodDescriptor> unorderedDescriptors,
            List<MethodDescriptor> orderedDescriptors,
            String line) {

        unorderedDescriptors.forEach(methodDescriptor -> {

            Given givenAnnotation = methodDescriptor.getMethod().getAnnotation(Given.class);
            if (givenAnnotation != null && givenAnnotation.description().equals(line)) {
                orderedDescriptors.add(methodDescriptor);
            }
            When whenAnnotation = methodDescriptor.getMethod().getAnnotation(When.class);
            if (whenAnnotation != null && whenAnnotation.description().equals(line)) {
                orderedDescriptors.add(methodDescriptor);
            }
            Then thenAnnotation = methodDescriptor.getMethod().getAnnotation(Then.class);
            if (thenAnnotation != null && thenAnnotation.description().equals(line)) {
                orderedDescriptors.add(methodDescriptor);
            }
        });
    }

    private <T extends MethodDescriptor> T convert(MethodDescriptor descriptor) {
        return (T) descriptor;
    }

    private <T> void add(List<T> descriptorList, MethodDescriptor descriptor) {
        descriptorList.add((T) descriptor);
    }
}
