package org.bubumc.bubucore.core.games.uhc_duel;

import lombok.Getter;
import org.bubumc.bubucore.BubuCore;
import org.bubumc.bubucore.core.games.QueueLobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("SpellCheckingInspection")
public class UHCQueueLobby extends QueueLobby {

    @Getter private final List<Player> inQueuePlayers;
    @Getter private final int maxPlayers = 2;
    @Getter private final UUID queueID;
    private final BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

    public UHCQueueLobby(Player starterPlayer) {
        inQueuePlayers = new ArrayList<>();
        inQueuePlayers.add(starterPlayer);
        queueID = UUID.randomUUID();
    }

    @Override
    public boolean isInQueue(Player player) {
        return inQueuePlayers.contains(player);
    }

    @Override
    public void removePlayerFromQueue(Player player) {
        if (!this.inQueuePlayers.contains(player)) return;
        if (this.inQueuePlayers.size() - 1 == 1) {
            plugin.getGamesManager().uhcQueues.remove(this);
            this.inQueuePlayers.clear();
        }
        this.inQueuePlayers.remove(player);
    }

    @Override
    public void addPlayerToQueue(Player player) {
        inQueuePlayers.add(player);
        for (Player p: inQueuePlayers) p.sendMessage("§aGiocatori in queue: " + inQueuePlayers.size() + "/" + maxPlayers);
        if (inQueuePlayers.size() == maxPlayers) startGame();
    }

    @Override
    public void startGame() {
        plugin.getGamesManager().uhcQueues.remove(this);
        UHCGame uhcGame = new UHCGame(this.inQueuePlayers);
        uhcGame.startRound();
        plugin.getGamesManager().uhcGames.add(uhcGame);
    }
}
