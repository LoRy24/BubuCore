package org.bubumc.bubucore.core.games;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class GameSpawnLocation {
    @Getter private final double x;
    @Getter private final double y;
    @Getter private final double z;
    @Getter private final float yaw;
    @Getter private final float pitch;

    public GameSpawnLocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location toLocation(String world) {
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
}
