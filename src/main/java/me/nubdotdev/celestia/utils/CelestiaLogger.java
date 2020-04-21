package me.nubdotdev.celestia.utils;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class CelestiaLogger {

    private static Logger logger = Bukkit.getLogger();

    private static final String PREFIX = "[CelestiaCore] ";

    public static void info(String msg) {
        logger.info(PREFIX + msg);
    }

    public static void warning(String msg) {
        logger.warning(PREFIX + msg);
    }

    public static void severe(String msg) {
        logger.severe(PREFIX + msg);
    }

}
