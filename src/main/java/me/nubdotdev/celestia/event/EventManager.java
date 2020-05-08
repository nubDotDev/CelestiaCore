package me.nubdotdev.celestia.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventManager {

    private final Map<Listener, Map<Class<? extends Event>, Set<Method>>> listeners = new HashMap<>();
    private final EventExecutor executor = (listener, event) -> callEvent(event);
    private final Plugin plugin;

    public EventManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unchecked")
    public void registerEvents(Listener listener) {
        Map<Class<? extends Event>, Set<Method>> handlers = new HashMap<>();
        for (Method m : listener.getClass().getMethods()) {
            EventHandler handler = m.getAnnotation(EventHandler.class);
            if (handler == null)
                continue;
            Parameter[] params = m.getParameters();
            if (params.length != 1 || !Event.class.isAssignableFrom(params[0].getType()))
                continue;
            Class<? extends Event> event = (Class<? extends Event>) params[0].getType();
            Bukkit.getPluginManager().registerEvent(
                    event, listener, handler.priority(), executor, plugin, handler.ignoreCancelled()
            );
            handlers.putIfAbsent(event, new HashSet<>());
            handlers.get(event).add(m);
        }
        listeners.put(listener, handlers);
    }

    public void unregisterEvents(Listener listener) {
        listeners.remove(listener);
    }

    public <T extends Event> void callEvent(T event) {
        Class<? extends Event> eventClazz = event.getClass();
        for (Map.Entry<Listener, Map<Class<? extends Event>, Set<Method>>> handlers : listeners.entrySet())
            if (handlers.getValue().containsKey(eventClazz))
                for (Method m : handlers.getValue().get(eventClazz))
                    invokeHandlerMethod(m, handlers.getKey(), event);
    }

    public <T extends Event> void invokeHandlerMethod(Method handlerMethod, Listener listener, T event) {
        try {
            handlerMethod.invoke(listener, event);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Map<Listener, Map<Class<? extends Event>, Set<Method>>> getListeners() {
        return listeners;
    }

}
