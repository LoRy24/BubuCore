package org.bubumc.bubucore.core.games.uhc_duel;

import lombok.Setter;
import org.bubumc.bubucore.BubuCore;
import org.bubumc.bubucore.core.games.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class UHCGame extends Game {
    private final List<Player> players;
    @Setter private Player winner;
    @Setter private Player loser;
    private final BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

    protected UHCGame(List<Player> players) {
        super(players);
        this.players = players;
    }

    @Override
    public void startRound() {
        // TODO TELEPORT PLAYERS TO ARENA
        for (Player p : players) p.sendMessage("§aRound iniziato! Buona fortuna!");
        giveKits();
    }

    @Override
    public void endRound() {
        winner.sendMessage("§aHai vinto!");
        loser.sendMessage("§cHai perso! Sei scarso bro ;b");
        plugin.getGamesManager().uhcGames.remove(this);
        // TODO TELEPORT PLAYERS TO SPAWN
    }

    @Override
    public void giveKits() {

    }

    @Override
    public boolean isInGame(Player player) {
        return players.contains(player);
    }

    @Override
    public void removePlayerFromGame(Player player) {
        this.players.remove(player);
        if (players.size() == 1) {
            setLoser(player);
            setWinner(players.get(0));
            endRound();
        }
    }
}
