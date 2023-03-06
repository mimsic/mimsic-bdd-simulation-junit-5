package com.github.mimsic.bdd.sj5.generator;

import com.github.mimsic.bdd.sj5.annotation.Given;
import com.github.mimsic.bdd.sj5.annotation.Story;
import com.github.mimsic.bdd.sj5.annotation.Then;
import com.github.mimsic.bdd.sj5.annotation.When;
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

        Given givenAnnotation = testMethod.getAnnotation(Given.class);
        if (givenAnnotation != null && givenAnnotation.description().length() > 0) {
            return givenAnnotation.description();
        }
        When whenAnnotation = testMethod.getAnnotation(When.class);
        if (whenAnnotation != null && whenAnnotation.description().length() > 0) {
            return whenAnnotation.description();
        }
        Then thenAnnotation = testMethod.getAnnotation(Then.class);
        if (thenAnnotation != null && thenAnnotation.description().length() > 0) {
            return thenAnnotation.description();
        }
        return super.generateDisplayNameForMethod(testClass, testMethod);
    }
}
