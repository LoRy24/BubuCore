package org.bubumc.bubucore.core.games;

import org.bukkit.entity.Player;

public abstract class QueueLobby {
    public abstract boolean isInQueue(Player player);
    public abstract void removePlayerFromQueue(Player player);
    public abstract void addPlayerToQueue(Player player);
    public abstract void startGame();
}
