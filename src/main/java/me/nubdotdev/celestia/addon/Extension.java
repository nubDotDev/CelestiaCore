package me.nubdotdev.celestia.addon;

import me.nubdotdev.celestia.command.CelestiaCommand;

import java.util.Collection;

public interface Extension {

    String getExtensionName();

    Collection<CelestiaCommand> getCommands();

    String getPath();

    void setPath(String path);

}
