package me.nubdotdev.celestia.utils;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class CelestiaLogger {

    private Logger logger = Bukkit.getLogger();

    private final String PREFIX = "[CelestiaCore] ";

    public void info(String msg) {
        logger.info(PREFIX + msg);
    }

    public void warning(String msg) {
        logger.warning(PREFIX + msg);
    }

    public void severe(String msg) {
        logger.severe(PREFIX + msg);
    }

}
