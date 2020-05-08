package me.nubdotdev.celestia.addon;

import me.nubdotdev.celestia.command.CommandHandler;

import java.util.Collection;

public interface Module {

    String getModuleName();

    Collection<CommandHandler> getCommands();

}
