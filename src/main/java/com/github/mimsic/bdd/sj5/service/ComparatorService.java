package com.github.mimsic.bdd.sj5.service;

import org.springframework.stereotype.Component;

@Component
public class ComparatorService {

    public int compare(int low, int high) {

        if (low >= high) {
            return -100;
        }
        if (low >= 0) {
            return 1;
        }
        if (high <= 0) {
            return -1;
        }
        return 0;
    }
}
