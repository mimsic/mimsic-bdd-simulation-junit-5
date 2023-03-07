package com.github.mimsic.bdd.sj5.orderer;

import com.github.mimsic.bdd.sj5.annotation.Given;
import com.github.mimsic.bdd.sj5.annotation.Then;
import com.github.mimsic.bdd.sj5.annotation.When;
import org.junit.jupiter.api.MethodDescriptor;

import java.util.List;

public class SimulatorMethodOrdererUtil {

    public static void methodAnnotation(
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

    @SuppressWarnings("unchecked")
    public static <T extends MethodDescriptor> T convert(MethodDescriptor descriptor) {
        return (T) descriptor;
    }

    @SuppressWarnings("unchecked")
    public static <T extends MethodDescriptor> void add(List<T> descriptors, MethodDescriptor descriptor) {
        descriptors.add((T) descriptor);
    }
}
