package me.nubdotdev.celestia.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventManager {

    private int channel;
    private EventExecutor executor;
    private Plugin plugin;

    private Set<CelestiaListener> listeners;

    public EventManager(int channel, Plugin plugin) {
        this.channel = channel;
        this.executor = (listener, event) -> callEvent(event);
        this.listeners = new HashSet<>();
        this.plugin = plugin;
    }

    @SuppressWarnings("unchecked")
    public void registerEvents(Listener listener) {
        CelestiaListener gameListener = new CelestiaListener(listener);
        for (Method m : listener.getClass().getMethods()) {
            CelestiaEventHandler handler = m.getAnnotation(CelestiaEventHandler.class);
            if (handler == null || handler.channel() != channel)
                continue;
            Parameter[] params = m.getParameters();
            if (params.length != 1 || !Event.class.isAssignableFrom(params[0].getType()))
                continue;
            Class<? extends Event> event = (Class<? extends Event>) params[0].getType();
            EventPriority priority;
            boolean ignoreCancelled;
            try {
                priority = handler.priority();
                ignoreCancelled = handler.ignoreCancelled();
            } catch (Exception e) {
                priority = EventPriority.NORMAL;
                ignoreCancelled = false;
                e.printStackTrace();
            }
            Bukkit.getPluginManager().registerEvent(
                    event, listener, priority, executor, plugin, ignoreCancelled
            );
            gameListener.getHandlers().put(m, event);
        }
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        listeners.add(gameListener);
    }

    public void unregisterEvents(Listener listener) {
        HandlerList.unregisterAll(listener);
        for (CelestiaListener gameListener : listeners) {
            if (gameListener.getListener() == listener) {
                listeners.remove(gameListener);
                break;
            }
        }
    }

    public <T extends Event> void callEvent(T event) {
        Class<? extends Event> eventClazz = event.getClass();
        for (CelestiaListener listener : getListeners()) {
            for (Map.Entry<Method, Class<? extends Event>> entry : listener.getHandlers().entrySet()) {
                if (entry.getValue() == eventClazz) {
                    Method method = entry.getKey();
                    try {
                        method.invoke(listener.getListener());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Set<CelestiaListener> getListeners() {
        return listeners;
    }

}
