package org.bubumc.bubucore;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigValues {
    @Getter private final List<String> blockedCommands;
    @Getter private final Location spawnLocation;

    public ConfigValues(FileConfiguration config) {
        spawnLocation = config.getConfigurationSection("Settings.SpawnLocation") == null ? null : new Location(Bukkit.getWorld(
                config.getString("Settings.SpawnLocation.World")), config.getDouble("Settings.SpawnLocation.X"),
                config.getDouble("Settings.SpawnLocation.Y"), config.getDouble("Settings.SpawnLocation.Z"),
                (float) config.getDouble("Settings.SpawnLocation.Yaw"), (float) config.getDouble("Settings.SpawnLocation.Pitch"));
        this.blockedCommands = config.getStringList("Settings.blocked_commands");
    }
}
