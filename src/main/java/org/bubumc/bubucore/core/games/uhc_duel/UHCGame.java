package org.bubumc.bubucore.core.games.uhc_duel;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.bubumc.bubucore.BubuCore;
import org.bubumc.bubucore.addons.InventoryUtils;
import org.bubumc.bubucore.core.games.Game;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.Random;

public class UHCGame extends Game {
    private final List<Player> players;
    private final BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");
    @Getter private World roundWorld;
    private final File roundWorldFile;
    private final UHCWorlds uhcWorlds;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    public UHCGame(List<Player> players, int id) {
        super(players);
        this.players = players;
        Random random = new Random();
        uhcWorlds = UHCWorlds.valueOf("ARENA_" + (random.nextInt(2) + 1));
        roundWorldFile = new File(uhcWorlds.getWorld().getWorldFile().getAbsolutePath() + id + "_fix1");
        roundWorldFile.mkdir();
        FileUtils.copyDirectory(uhcWorlds.getWorld().getWorldFile(), roundWorldFile);
    }

    @Override
    public void startRound() {
        // World Creation
        WorldCreator worldCreator = new WorldCreator(roundWorldFile.getName());
        roundWorld = worldCreator.createWorld();
        Bukkit.getServer().createWorld(worldCreator);
        plugin.worldsFiles.add(roundWorldFile);

        //Bukkit.getWorld(roundWorldFile.getName()).setStorm(false);
        for (int i = 0; i < players.size(); i++) players.get(i).teleport(uhcWorlds.getWorld().getSpawnLocations()[i]
                .toLocation(roundWorld.getName()));
        for (Player p : players) {
            p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage("§aRound iniziato! Buona fortuna!");
        }
        giveKits();
    }

    @SneakyThrows
    @Override
    public void endRound() {
        getWinner().sendTitle("§aWow, hai Vinto..", "§aMo ti senti realizzato?");
        getLoser().sendTitle("§cHai perso...", "§cBro sei proprio scarso");
        getWinner().sendMessage("§aHai vinto... E mo ti senti nu' poco realizzato?");
        getLoser().sendMessage("§cDa quanto sei scarso ti sei fatto pure battere :b");
        getWinner().playSound(getWinner().getLocation(), Sound.CAT_MEOW, 5, 8);
        getLoser().playSound(getLoser().getLocation(), Sound.CAT_MEOW, 1, 8);
        plugin.getGamesManager().uhcGames.remove(this);
        for (Player p: players) {
            if (Bukkit.getPlayerExact(p.getName()) == null) continue;
            p.setFireTicks(0);
            p.getInventory().clear();
            InventoryUtils.setHubInventory(p);
            p.teleport(plugin.getConfigValues().getSpawnLocation());
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);
            p.setFoodLevel(20);
            p.setHealth(20);
        }

        for (Player p: Bukkit.getOnlinePlayers()) {
            if (p.getWorld().getName().equals(roundWorld.getName())) p.teleport(plugin.getConfigValues().getSpawnLocation());
        }

        Bukkit.getServer().unloadWorld(roundWorld.getName(), true);
        FileUtils.deleteDirectory(roundWorldFile);
        plugin.worldsFiles.remove(roundWorldFile);
    }

    @Override
    public void giveKits() {
        for (Player p: players) {
            p.getInventory().clear();
            p.getInventory().addItem(
                    new ItemStack(Material.IRON_SWORD, 1),
                    new ItemStack(Material.FISHING_ROD),
                    new ItemStack(Material.BOW),
                    new ItemStack(Material.IRON_AXE),
                    new ItemStack(Material.GOLDEN_APPLE, 8),
                    new ItemStack(Material.WOOD, 64),
                    new ItemStack(Material.LAVA_BUCKET),
                    new ItemStack(Material.WATER_BUCKET),
                    new ItemStack(Material.ARROW, 16));
            p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            p.setFoodLevel(20);
            p.setHealth(20);
        }
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
