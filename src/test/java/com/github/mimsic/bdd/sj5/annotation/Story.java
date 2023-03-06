package com.github.mimsic.bdd.sj5.annotation;

import org.junit.jupiter.params.ParameterizedTest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ParameterizedTest
public @interface Story {

    String description() default "";

    String path();
}


