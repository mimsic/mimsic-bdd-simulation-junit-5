package com.github.mimsic.bdd.sj5.orderer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodDescriptor;

import java.util.List;

public class SimulatorMethodOrdererUtil {

    public static void methodAnnotation(
            List<? extends MethodDescriptor> unorderedDescriptors,
            List<MethodDescriptor> orderedDescriptors,
            String line) {

        unorderedDescriptors.stream()
                .filter((MethodDescriptor methodDescriptor) -> isDisplayName(methodDescriptor, line))
                .findFirst()
                .ifPresent((MethodDescriptor methodDescriptor) -> {
                    orderedDescriptors.add(methodDescriptor);
                    unorderedDescriptors.remove(methodDescriptor);
                });
    }

    private static boolean isDisplayName(MethodDescriptor methodDescriptor, String line) {
        DisplayName annotation = methodDescriptor.getMethod().getAnnotation(DisplayName.class);
        return annotation != null && annotation.value().equals(line);
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
