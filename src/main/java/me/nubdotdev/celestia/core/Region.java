package me.nubdotdev.celestia.core;

import me.nubdotdev.celestia.data.yaml.YamlSerializable;
import me.nubdotdev.celestia.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class Region implements YamlSerializable {

    private World world;
    private Location pos1, pos2, min, max;

    /**
     * Creates a new region in a specified world
     *
     * @param world  world in which the region is contained
     */
    public Region(World world) {
        this.world = world;
    }

    /**
     * Creates a new region in a specified world between two locations
     *
     * @param world  world in which the region is contained
     * @param pos1   first bound of the region
     * @param pos2   second bound of the region
     */
    public Region(World world, Location pos1, Location pos2) {
        this.world = world;
        this.pos1 = pos1.getWorld() == world ? pos1 : null;
        this.pos2 = pos2.getWorld() == world ? pos2 : null;
        updateExtremes();
    }

    /**
     * Used for YAML serialization
     */
    public Region(Map<String, Object> serialized) {
        this.world = Bukkit.getWorld((String) serialized.get("world"));
        this.pos1 = LocationUtils.deserializeLocation((String) serialized.get("position-1"));
        this.pos2 = LocationUtils.deserializeLocation((String) serialized.get("position-2"));
        updateExtremes();
    }

    /**
     * Used for YAML serialization
     */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("world", world.getName());
        serialized.put("position-1", LocationUtils.serializeLocation(pos1));
        serialized.put("position-2", LocationUtils.serializeLocation(pos2));
        return serialized;
    }

    /**
     * Checks if a location is inside of the region
     *
     * @param location  location to check if its inside the region
     * @return          true if the location is inside the region
     */
    public boolean isInside(Location location) {
        if (location.getWorld() == world) {
            return min == null || max == null || (
                    location.getX() > min.getX() && location.getX() < max.getX() &&
                    location.getY() > min.getY() && location.getY() < max.getY() &&
                    location.getZ() > min.getZ() && location.getZ() < max.getZ()
            );
        }
        return false;
    }

    private void updateExtremes() {
        min = new Location(
                pos1.getWorld(), Math.min(pos1.getBlockX(), pos2.getBlockX()),
                Math.min(pos1.getBlockY(), pos2.getBlockY()),
                Math.min(pos1.getBlockZ(), pos2.getBlockZ())
        );
        max = new Location(
                pos1.getWorld(), Math.max(pos1.getBlockX(), pos2.getBlockX()),
                Math.max(pos1.getBlockY(), pos2.getBlockY()),
                Math.max(pos1.getBlockZ(), pos2.getBlockZ())
        );
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
        if (pos1.getWorld() != world || pos2.getWorld() != world) {
            pos1 = null;
            pos2 = null;
            min = null;
            max = null;
        }
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location loc) {
        if (loc.getWorld() == world) {
            pos1 = loc;
            updateExtremes();
        }
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location loc) {
        if (loc.getWorld() == world){
            pos2 = loc;
            updateExtremes();
        }
    }

    public Location getMin() {
        return min;
    }

    public Location getMax() {
        return max;
    }

}
