package org.bubumc.bubucore.core.games;

import lombok.Getter;
import org.bubumc.bubucore.BubuCore;
import org.bubumc.bubucore.core.games.uhc_duel.UHCQueueLobby;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class GamesManager {
    @Getter private final List<UHCQueueLobby> uhcQueues;
    private final BubuCore bubuCore;

    public GamesManager(BubuCore bubuCore) {
        this.bubuCore = bubuCore;
        uhcQueues = new ArrayList<>();
    }

    public void startNewUHCQueue(Player starterPlayer) {
        UHCQueueLobby queueLobby = new UHCQueueLobby(starterPlayer);
        uhcQueues.add(queueLobby);
        starterPlayer.sendMessage("§aSei entrato nella queue #" + queueLobby.getQueueID());
    }

    public QueueLobby getPlayerInQueueLobby(Player player) {
        for (UHCQueueLobby queueLobby: uhcQueues) if (queueLobby.isInQueue(player)) return queueLobby;
        return null;
    }

    public void removePlayerFromHisQueue(Player player) {
        getPlayerInQueueLobby(player).removePlayerFromQueue(player);
    }
}
