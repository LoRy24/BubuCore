package org.bubumc.bubucore.core.games;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class Game {
    public final List<Player> players;

    protected Game(List<Player> players) {
        this.players = players;
    }

    public abstract void startRound();
    public abstract void endRound();
    public abstract void giveKits();
    public abstract boolean isInGame(Player player);
    public abstract void removePlayerFromGame(Player player);
}
