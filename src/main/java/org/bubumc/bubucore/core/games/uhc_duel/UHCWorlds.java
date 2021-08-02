package org.bubumc.bubucore.core.games.uhc_duel;

import lombok.Getter;
import org.bubumc.bubucore.core.games.GameSpawnLocation;
import org.bubumc.bubucore.core.games.GameWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.util.Arrays;

public enum UHCWorlds {
    ARENA_1(new GameWorld(new File("uhc_world_1_duplicate"), new GameSpawnLocation(0.5d, 66d, -14.5d, 0f, 0f),
            new GameSpawnLocation(0.5d, 66d, 15.5d, -180f, 0f))),
    ARENA_2(new GameWorld(new File("uhc_world_2_duplicate"), new GameSpawnLocation(0.5d, 66d, -14.5d, 0f, 0f),
            new GameSpawnLocation(0.5d, 66d, 15.5d, -180f, 0f))),
    ;

    @Getter private final GameWorld world;

    UHCWorlds(GameWorld world) {
        this.world = world;
    }
}
