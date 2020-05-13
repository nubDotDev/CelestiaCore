package me.nubdotdev.celestia.core;

import me.nubdotdev.celestia.CelestiaCore;
import me.nubdotdev.celestia.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ActionBar {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();
    private static Method chatSerializerAMethod;
    private static Object chatMessageType;
    private static Constructor<?> packetPlayOutChatConstructor;

    static {
        Class<?> iChatBaseComponentClass = ReflectionUtils.getNmsClass("IChatBaseComponent");
        boolean usingChatMessageTypeClass = Integer.parseInt(ReflectionUtils.getVersion().split("_")[1]) >= 12;
        try {
            chatSerializerAMethod = iChatBaseComponentClass.getClasses()[0].getMethod("a", String.class);
            if (usingChatMessageTypeClass) {
                Class<?> chatMessageTypeClass = ReflectionUtils.getNmsClass("ChatMessageType");
                packetPlayOutChatConstructor = ReflectionUtils.getNmsClass("PacketPlayOutChat")
                        .getConstructor(iChatBaseComponentClass, chatMessageTypeClass);
                chatMessageType = chatMessageTypeClass.getEnumConstants()[2];
            } else {
                packetPlayOutChatConstructor = ReflectionUtils.getNmsClass("PacketPlayOutChat")
                        .getConstructor(iChatBaseComponentClass, byte.class);
                chatMessageType = (byte) 2;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void send(Player player, String text) {
        scheduler.runTaskAsynchronously(CelestiaCore.getInst(), () -> {
            try {
                ReflectionUtils.sendPacket(player, packetPlayOutChatConstructor.newInstance(
                        chatSerializerAMethod.invoke(null, "{\"text\":\"" + text + "\"}"),
                        chatMessageType
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void send(Player player, String text, long duration) {
        (new BukkitRunnable() {
            long remaining = duration;

            @Override
            public void run() {
                send(player, text);
                remaining -= 40;
                if (remaining <= 0) {
                    scheduler.runTaskLaterAsynchronously(CelestiaCore.getInst(), () -> send(player, ""), 40 + remaining);
                    cancel();
                }
            }
        }).runTaskTimerAsynchronously(CelestiaCore.getInst(), 0, 40);
    }

}
