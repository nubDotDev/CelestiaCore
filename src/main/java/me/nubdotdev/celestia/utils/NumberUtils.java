package me.nubdotdev.celestia.utils;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Random;

public class NumberUtils {

    // First 99 powers of 10
    private static List<String> abbreviations = ImmutableList.of(
            "K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No",
            "Dc", "Ud", "Dd", "Td", "Qad", "Qid", "Sxd", "Spd", "Ocd", "Nod",
            "Vg", "Uvg", "Dvg", "Tvg", "Qavg", "Qivg", "Sxvg", "Spvg", "Ocvg", "Novg",
            "Tg", "Utg", "Dtg"
    );

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

    public static double randomInRange(double lowerInclusive, double upperExclusive) throws IllegalArgumentException {
        if (lowerInclusive >= upperExclusive)
            throw new IllegalArgumentException("Lower bound must be less than upper");
        return (new Random()).nextDouble() * (upperExclusive - lowerInclusive) + lowerInclusive;
    }

}
