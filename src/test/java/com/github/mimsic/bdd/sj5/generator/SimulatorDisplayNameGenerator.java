package com.github.mimsic.bdd.sj5.generator;

import com.github.mimsic.bdd.sj5.annotation.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public class SimulatorDisplayNameGenerator extends DisplayNameGenerator.Standard {

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {

        Story storyAnnotation = testClass.getAnnotation(Story.class);
        if (storyAnnotation != null && storyAnnotation.description().length() > 0) {
            return storyAnnotation.description();
        }
        return super.generateDisplayNameForClass(testClass);
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {

        DisplayName annotation = testMethod.getAnnotation(DisplayName.class);
        if (annotation != null && annotation.value().length() > 0) {
            return annotation.value();
        }
        return super.generateDisplayNameForMethod(testClass, testMethod);
    }
}
