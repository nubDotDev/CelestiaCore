package me.nubdotdev.celestia.utils;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Random;

public class NumberUtils {

    private final static List<String> abbreviations = ImmutableList.of(
            "K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No",
            "Dc", "Ud", "Dd", "Td", "Qad", "Qid", "Sxd", "Spd", "Ocd", "Nod",
            "Vg", "Uvg", "Dvg", "Tvg", "Qavg", "Qivg", "Sxvg", "Spvg", "Ocvg", "Novg",
            "Tg", "Utg", "Dtg"
    );

    /**
     * Converts a double whose absolute value is greater than or equal to 1,000 into a more readable String<br>
     * Works for any double but there are only suffixes for the first 101 powers of 10<br>
     * Ex: format(1234567890, 3) will return "1.235B"
     *
     * @param number                     double to format
     * @param decimalPlaces              number of decimal places to which to round
     * @return                           formatted number
     * @throws IllegalArgumentException  if the number of decimal places is less than zero
     */
    public static String format(double number, int decimalPlaces) throws IllegalArgumentException {
        if (decimalPlaces < 0)
            throw new IllegalArgumentException("Decimal places must be at least zero");
        if (number < 0)
            return "-" + format(-number, decimalPlaces);
        String format = "%." + decimalPlaces + "f";
        if (number < 1000)
            return String.format(format, number);
        int zeroSets = (int) Math.floor(Math.log10(number) / 3);
        if (zeroSets > abbreviations.size())
            return String.format(format, number / Math.pow(10, abbreviations.size())) + abbreviations.get(abbreviations.size() - 1);
        return String.format(format, number / Math.pow(10, zeroSets * 3)) + abbreviations.get(zeroSets - 1);
    }

    /**
     * Returns a pseudo-random, uniformly distributed double within two bounds<br>
     * Return value will be greater than or equal to the lower bound, but less than the upper bound
     *
     * @param lowerInclusive             lower inclusive bound
     * @param upperExclusive             upper exclusive bound
     * @return                           pseudo-random, uniformly distributed double within two bounds
     * @throws IllegalArgumentException  if the lower bound is greater than the upper
     */
    public static double randomInRange(double lowerInclusive, double upperExclusive) throws IllegalArgumentException {
        if (lowerInclusive >= upperExclusive)
            throw new IllegalArgumentException("Lower bound must be less than upper");
        return (new Random()).nextDouble() * (upperExclusive - lowerInclusive) + lowerInclusive;
    }

}
