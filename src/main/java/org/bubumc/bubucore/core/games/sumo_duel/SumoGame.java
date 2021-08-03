package org.bubumc.bubucore.core.games.sumo_duel;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.bubumc.bubucore.BubuCore;
import org.bubumc.bubucore.core.games.Game;
import org.bubumc.bubucore.core.games.GameSpawnLocation;
import org.bubumc.bubucore.core.games.GameWorld;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class SumoGame extends Game {
    private final List<Player> players;
    private final BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");
    @Getter private World roundWorld;
    private final GameWorld sumoWorld = new GameWorld(new File("uhc_world_1_duplicate"), new GameSpawnLocation(0.5d, 66d, -14.5d, 0f, 0f),
            new GameSpawnLocation(0.5d, 66d, 15.5d, -180f, 0f));
    private final File roundWorldFile;

    @SneakyThrows
    protected SumoGame(List<Player> players, int id) {
        super(players);
        this.players = players;
        roundWorldFile = new File(sumoWorld.getWorldFile().getAbsolutePath() + id + "_fix1");
        roundWorldFile.mkdir();
        FileUtils.copyDirectory(sumoWorld.getWorldFile(), roundWorldFile);
    }

    @Override
    public void startRound() {
        // Create the world
        WorldCreator worldCreator = new WorldCreator(roundWorld.getName());
        roundWorld = worldCreator.createWorld();
        Bukkit.getServer().createWorld(worldCreator);
        plugin.worldsFiles.add(roundWorldFile);

        for (int i = 0; i < players.size(); i++)
            players.get(i).teleport(sumoWorld.getSpawnLocations()[i].toLocation(roundWorld.getName()));
        for (Player p : players) {
            p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage("§aRound iniziato! Buona fortuna!");
        }
        giveKits();
    }

    @Override
    public void endRound() {
        getWinner().sendTitle("§aWow, hai Vinto..", "§aMo ti senti realizzato?");
        getLoser().sendTitle("§cHai perso...", "§cBro sei proprio scarso");
        getWinner().sendMessage("§aHai vinto... E mo ti senti nu' poco realizzato?");
        getLoser().sendMessage("§cDa quanto sei scarso ti sei fatto pure battere :b");
        getWinner().playSound(getWinner().getLocation(), Sound.CAT_MEOW, 5, 8);
        getLoser().playSound(getLoser().getLocation(), Sound.CAT_MEOW, 1, 8);
    }

    @Override
    public void giveKits() {

    }

    @Override
    public boolean isInGame(Player player) {
        return false;
    }

    @Override
    public void removePlayerFromGame(Player player) {

    }
}
