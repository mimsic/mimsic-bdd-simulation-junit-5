package com.github.mimsic.bdd.sj5.generator;

import com.github.mimsic.bdd.sj5.annotation.Story;
import org.junit.jupiter.api.DisplayNameGenerator;

public class SimulatorDisplayNameGenerator extends DisplayNameGenerator.Standard {

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {

        Story storyAnnotation = testClass.getAnnotation(Story.class);
        if (storyAnnotation != null && storyAnnotation.description().length() > 0) {
            return storyAnnotation.description();
        }
        return super.generateDisplayNameForClass(testClass);
    }
}
