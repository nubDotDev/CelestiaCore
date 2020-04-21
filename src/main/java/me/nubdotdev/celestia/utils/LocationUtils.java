package me.nubdotdev.celestia.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

    public static String serializeLocation(Location location) {
        return location.getWorld().getName() + "_" +
                location.getX() + "_" +
                location.getY() + "_" +
                location.getZ() + "_" +
                location.getPitch() + "_" +
                location.getYaw();
    }

    public static Location deserializeLocation(String serialized) throws NumberFormatException {
        String[] args = serialized.split("_");
        World world = Bukkit.getWorld(args[0]);
        if (world == null)
            return null;
        return new Location(
                world,
                Double.parseDouble(args[1]),
                Double.parseDouble(args[2]),
                Double.parseDouble(args[3]),
                Float.parseFloat(args[4]),
                Float.parseFloat(args[5])
        );
    }

}
