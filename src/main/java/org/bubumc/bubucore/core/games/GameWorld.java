package org.bubumc.bubucore.core.games;

import lombok.Getter;
import org.bukkit.Location;

import java.io.File;

public class GameWorld {
    @Getter private final File worldFile;
    @Getter private final GameSpawnLocation[] spawnLocations;

    public GameWorld(File worldFile, GameSpawnLocation... spawnLocations) {
        this.worldFile = worldFile;
        this.spawnLocations = spawnLocations;
    }
}
