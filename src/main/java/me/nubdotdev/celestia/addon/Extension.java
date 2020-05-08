package me.nubdotdev.celestia.addon;

import me.nubdotdev.celestia.command.CommandHandler;

import java.util.Collection;

public interface Extension {

    String getExtensionName();

    Collection<CommandHandler> getCommands();

    String getPath();

    void setPath(String path);

}
