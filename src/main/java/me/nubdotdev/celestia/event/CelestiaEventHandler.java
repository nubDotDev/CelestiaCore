package me.nubdotdev.celestia.event;

import org.bukkit.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CelestiaEventHandler {

    int channel();
    boolean ignoreCancelled() default false;
    EventPriority priority() default EventPriority.NORMAL;

}
