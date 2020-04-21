package me.nubdotdev.celestia.event;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CelestiaListener {

    private Listener listener;
    private Map<Method, Class<? extends Event>> handlers;

    public CelestiaListener(Listener listener) {
        this.listener = listener;
        this.handlers = new HashMap<>();
    }

    public Listener getListener() {
        return listener;
    }

    public Map<Method, Class<? extends Event>> getHandlers() {
        return handlers;
    }

}
