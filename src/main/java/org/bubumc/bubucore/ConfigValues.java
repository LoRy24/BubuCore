package org.bubumc.bubucore;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigValues {
    @Getter private final List<String> blockedCommands;

    public ConfigValues(FileConfiguration config) {
        this.blockedCommands = config.getStringList("Settings.blocked_commands");
    }
}
