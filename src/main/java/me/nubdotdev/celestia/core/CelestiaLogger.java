package me.nubdotdev.celestia.core;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class CelestiaLogger {

    private Logger logger = Bukkit.getLogger();

    public void info(String msg) {
        logger.info(msg);
    }

    public void warning(String msg) {
        logger.warning(msg);
    }

    public void severe(String msg) {
        logger.severe(msg);
    }

}
