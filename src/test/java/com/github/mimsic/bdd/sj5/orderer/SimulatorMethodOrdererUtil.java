package com.github.mimsic.bdd.sj5.orderer;

import com.github.mimsic.bdd.sj5.annotation.Given;
import com.github.mimsic.bdd.sj5.annotation.Then;
import com.github.mimsic.bdd.sj5.annotation.When;
import org.junit.jupiter.api.MethodDescriptor;

import java.util.List;

public class SimulatorMethodOrdererUtil {

    public static void methodOrderer(
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
    public static <T extends MethodDescriptor> T convert(MethodDescriptor descriptor) {
        return (T) descriptor;
    }

    @SuppressWarnings("unchecked")
    public static <T extends MethodDescriptor> void add(List<T> descriptors, MethodDescriptor descriptor) {
        descriptors.add((T) descriptor);
    }
}
