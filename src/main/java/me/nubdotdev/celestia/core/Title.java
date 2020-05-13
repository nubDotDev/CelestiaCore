package me.nubdotdev.celestia.core;

import me.nubdotdev.celestia.CelestiaCore;
import me.nubdotdev.celestia.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Title {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();
    private static Method chatSerializerAMethod;
    private static Object[] titleActions;
    private static Constructor<?> packetPlayOutTitleConstructor;

    static {
        Class<?> iChatBaseComponentClass = ReflectionUtils.getNmsClass("IChatBaseComponent");
        Class<?> packetPlayOutTitleClass = ReflectionUtils.getNmsClass("PacketPlayOutTitle");
        try {
            chatSerializerAMethod = iChatBaseComponentClass.getClasses()[0].getMethod("a", String.class);
            Class<?> enumTitleActionClass = packetPlayOutTitleClass.getClasses()[0];
            titleActions = enumTitleActionClass.getEnumConstants();
            packetPlayOutTitleConstructor = ReflectionUtils.getNmsClass("PacketPlayOutTitle")
                    .getConstructor(enumTitleActionClass, iChatBaseComponentClass, int.class, int.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void send(Player player, String title, String subtitle, int fadeIn, int show, int fadeOut) {
        scheduler.runTaskAsynchronously(CelestiaCore.getInst(), () -> {
            try {
                if (title != null) {
                    ReflectionUtils.sendPacket(player, packetPlayOutTitleConstructor.newInstance(
                            titleActions[0],
                            chatSerializerAMethod.invoke(null, "{\"text\":\"" + title + "\"}"),
                            fadeIn,
                            show,
                            fadeOut
                    ));
                }
                if (subtitle != null) {
                    ReflectionUtils.sendPacket(player, packetPlayOutTitleConstructor.newInstance(
                            titleActions[1],
                            chatSerializerAMethod.invoke(null, "{\"text\":\"" + subtitle + "\"}"),
                            fadeIn,
                            show,
                            fadeOut
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void send(Player player, String title, String subtitle, int show) {
        send(player, title, subtitle, 0, show, 0);
    }

}
