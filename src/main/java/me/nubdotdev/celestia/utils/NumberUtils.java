package me.nubdotdev.celestia.utils;

import java.util.Random;

public class NumberUtils {

    public static int intInRange(int lowerInclusive, int upperExclusive) {
        if (lowerInclusive >= upperExclusive)
            throw new IllegalArgumentException("The lower bound must be less than the upper bound");
        return (new Random()).nextInt(upperExclusive - lowerInclusive) + lowerInclusive;
    }

    public static double doubleInRange(double lowerInclusive, double upperExclusive) {
        if (lowerInclusive >= upperExclusive)
            throw new IllegalArgumentException("The lower bound must be less than the upper bound");
        return (new Random()).nextDouble() * (upperExclusive - lowerInclusive) + lowerInclusive;
    }

}
