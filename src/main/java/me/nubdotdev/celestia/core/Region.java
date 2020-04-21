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
    private Location pos1, pos2;

    public Region(World world) {
        this.world = world;
    }

    public Region(World world, Location pos1, Location pos2) {
        this.world = world;
        this.pos1 = pos1;
        this.pos2 = pos2;
        order();
    }

    public Region(Map<String, Object> serialized) {
        this.world = Bukkit.getWorld((String) serialized.get("world"));
        this.pos1 = LocationUtils.deserializeLocation((String) serialized.get("position-1"));
        this.pos2 = LocationUtils.deserializeLocation((String) serialized.get("position-2"));
        order();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("world", world.getName());
        serialized.put("position-1", LocationUtils.serializeLocation(pos1));
        serialized.put("position-2", LocationUtils.serializeLocation(pos2));
        return serialized;
    }
    
    public boolean isInside(Location location) {
        if (location.getWorld() == world) {
            return pos1 == null || pos2 == null || (
                    location.getX() > pos1.getX() && location.getX() < pos2.getX() &&
                    location.getY() > pos1.getY() && location.getY() < pos2.getY() &&
                    location.getZ() > pos1.getZ() && location.getZ() < pos2.getZ()
            );
        }
        return false;
    }

    private void order() {
        Location min = new Location(
                pos1.getWorld(), Math.min(pos1.getBlockX(), pos2.getBlockX()),
                Math.min(pos1.getBlockY(), pos2.getBlockY()),
                Math.min(pos1.getBlockZ(), pos2.getBlockZ())
        );
        Location max = new Location(
                pos1.getWorld(), Math.max(pos1.getBlockX(), pos2.getBlockX()),
                Math.max(pos1.getBlockY(), pos2.getBlockY()),
                Math.max(pos1.getBlockZ(), pos2.getBlockZ())
        );
        pos1 = min;
        pos2 = max;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location loc) {
        if (loc.getWorld() == pos1.getWorld())
            pos1 = loc;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location loc) {
        if (loc.getWorld() == pos2.getWorld())
            pos2 = loc;
    }

}
