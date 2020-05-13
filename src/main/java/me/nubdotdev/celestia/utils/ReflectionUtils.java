package me.nubdotdev.celestia.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {

    private static final String version;
    private static Method getHandleMethod;
    private static Field playerConnectionField;
    private static Method sendPacketMethod;

    static {
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            getHandleMethod = getCraftBukkitClass("entity.CraftPlayer").getMethod("getHandle");
            playerConnectionField = getHandleMethod.getReturnType().getField("playerConnection");
            sendPacketMethod = playerConnectionField.getType().getMethod("sendPacket", getNmsClass("Packet"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            sendPacketMethod.invoke(playerConnectionField.get(getHandleMethod.invoke(player)), packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNmsClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getCraftBukkitClass(String className) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersion() {
        return version;
    }

}
