package org.bubumc.bubucore.core.games;

import org.bubumc.bubucore.BubuCore;
import org.bubumc.bubucore.core.games.sumo_duel.SumoGame;
import org.bubumc.bubucore.core.games.sumo_duel.SumoQueueLobby;
import org.bubumc.bubucore.core.games.uhc_duel.UHCGame;
import org.bubumc.bubucore.core.games.uhc_duel.UHCQueueLobby;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class GamesManager {
    // queues
    public final List<UHCQueueLobby> uhcQueues;
    public final List<SumoQueueLobby> sumoQueues;

    // games
    public final List<UHCGame> uhcGames;
    public final List<SumoGame> sumoGames;

    private final BubuCore bubuCore;

    public GamesManager(BubuCore bubuCore) {
        this.bubuCore = bubuCore;
        uhcQueues = new ArrayList<>();
        uhcGames = new ArrayList<>();
        sumoQueues = new ArrayList<>();
        sumoGames = new ArrayList<>();
    }

    public boolean isInActivity(Player player) {
        for (UHCQueueLobby queueLobby: uhcQueues) if (queueLobby.isInQueue(player)) return true;
        for (UHCGame uhcGame: uhcGames) if (uhcGame.isInGame(player)) return true;
        return false;
    }

    // Sumo methods



    // UHC methods

    public void startNewUHCQueue(Player starterPlayer) {
        UHCQueueLobby queueLobby = new UHCQueueLobby(starterPlayer);
        uhcQueues.add(queueLobby);
        starterPlayer.sendMessage("§aSei entrato nella queue #" + queueLobby.getQueueID());
    }

    public void addToAQueueLobby(Player player) {
        uhcQueues.get(0).addPlayerToQueue(player);
    }

    public void playUHC(Player player) {
        if (uhcQueues.size() != 0) {
            addToAQueueLobby(player);
            return;
        }
        startNewUHCQueue(player);
    }

    // General methods

    public QueueLobby getPlayerInQueueLobby(Player player) {
        for (UHCQueueLobby queueLobby: uhcQueues) if (queueLobby.isInQueue(player)) return queueLobby;
        for (SumoQueueLobby queueLobby: sumoQueues)
            if (queueLobby.isInQueue(player)) return queueLobby;
        return null;
    }

    public void removePlayerFromHisQueue(Player player) {
        if (getPlayerInQueueLobby(player) == null) return;
        getPlayerInQueueLobby(player).removePlayerFromQueue(player);
    }

    public Game getPlayerInGameGame(Player player) {
        for (UHCGame uhcGame: uhcGames) if (uhcGame.isInGame(player)) return uhcGame;
        for (SumoGame sumoGame: sumoGames) if (sumoGame.isInGame(player)) return sumoGame;
        return null;
    }

    public void removePlayerFromHisGame(Player player) {
        if (getPlayerInGameGame(player) == null) return;
        getPlayerInGameGame(player).removePlayerFromGame(player);
    }
}
