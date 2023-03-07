package com.github.mimsic.bdd.sj5.orderer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodDescriptor;

import java.util.List;

public class SimulatorMethodOrdererUtil {

    public static void methodAnnotation(
            List<? extends MethodDescriptor> unorderedDescriptors,
            List<MethodDescriptor> orderedDescriptors,
            String line) {

        unorderedDescriptors.forEach(methodDescriptor -> {

            DisplayName annotation = methodDescriptor.getMethod().getAnnotation(DisplayName.class);
            if (annotation != null && annotation.value().equals(line)) {
                orderedDescriptors.add(methodDescriptor);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends MethodDescriptor> T convert(MethodDescriptor descriptor) {
        return (T) descriptor;
    }

    @SuppressWarnings("unchecked")
    public static <T> void add(List<T> descriptorList, MethodDescriptor descriptor) {
        descriptorList.add((T) descriptor);
    }
}
