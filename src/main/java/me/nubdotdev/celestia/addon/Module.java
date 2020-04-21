package me.nubdotdev.celestia.addon;

import me.nubdotdev.celestia.command.CelestiaCommand;

import java.util.Collection;

public interface Module {

    String getModuleName();

    Collection<CelestiaCommand> getCommands();

}
