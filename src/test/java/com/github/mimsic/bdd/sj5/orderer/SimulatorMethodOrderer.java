package com.github.mimsic.bdd.sj5.orderer;

import com.github.mimsic.bdd.sj5.annotation.Story;
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
                    SimulatorMethodOrdererUtil.methodOrderer(unorderedDescriptors, orderedDescriptors, line);
                }
                unorderedDescriptors.clear();
                orderedDescriptors.forEach(methodDescriptor -> {
                    unorderedDescriptors.add(SimulatorMethodOrdererUtil.convert(methodDescriptor));
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
