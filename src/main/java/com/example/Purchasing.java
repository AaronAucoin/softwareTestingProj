package com.example;

import java.util.Random;

public class Purchasing {
    private Random random = new Random();
    public double generateRandomBookCost() {
        return (10.0 + random.nextDouble() * (100.0-10.0));
    }
}
