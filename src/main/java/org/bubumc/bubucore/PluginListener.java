package org.bubumc.bubucore;

import com.github.lory24.mcuitils.McGUI;
import com.github.lory24.mcuitils.api.GUIButtonEvents;
import com.github.lory24.mcuitils.api.GUICustomItem;
import com.github.lory24.mcuitils.api.GUItem;
import com.github.lory24.mcuitils.utils.GuiLines;
import org.bubumc.bubucore.addons.InventoryUtils;
import org.bubumc.bubucore.addons.ServerTablist;
import org.bubumc.bubucore.core.games.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("SpellCheckingInspection")
public class PluginListener implements Listener {

    private final BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().clear();
        plugin.getRanksManager().loadRank(event.getPlayer());
        event.setJoinMessage("§7[§a+§7] §a" + event.getPlayer().getName());
        if (plugin.getConfigValues().getSpawnLocation() != null) event.getPlayer().teleport(plugin.getConfigValues().getSpawnLocation());
        plugin.getPermissionsManager().setPermissionsToPlayer(event.getPlayer(), plugin.getRanksManager().getRanks()
                .get(event.getPlayer()).getRankPermissions().getPermissions());
        ServerTablist.setTablist(event.getPlayer());
        InventoryUtils.setHubInventory(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("commandblocker.override")) return;
        if (!plugin.getConfigValues().getBlockedCommands().contains(event.getMessage().split(" ")[0].replace("/", "")
                .toLowerCase(Locale.ROOT))) return;
        event.getPlayer().sendMessage("§cNon puoi usare questo comando!");
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Bukkit.broadcastMessage(String.format("%s%s§8:§7 %s", plugin.getRanksManager().getRanks().get(event.getPlayer())
                .getType().getS(), event.getPlayer().getName(), ChatColor.translateAlternateColorCodes('&',
                event.getMessage())));
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage("§7[§c-§7] §c" + event.getPlayer().getName());
        // Games system
        plugin.getGamesManager()
                .removePlayerFromHisQueue(event.getPlayer());
        plugin.getGamesManager().removePlayerFromHisGame(
                event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.getGamesManager().getPlayerInGameGame(event.getPlayer()) == null) return;
        if (event.getBlock().getType().equals(Material.WOOD)) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (plugin.getGamesManager().getPlayerInGameGame((Player) event.getEntity()) != null) return;
        ((Player) event.getEntity()).setFoodLevel(20);
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (plugin.getGamesManager().getPlayerInGameGame(player) == null) return;
        if (player.getHealth() - event.getFinalDamage() > 0) return;
        Game game = plugin.getGamesManager().getPlayerInGameGame(player);
        game.setLoser(player);
        List<Player> players = new ArrayList<>(plugin.getGamesManager().getPlayerInGameGame(player).players);
        players.remove(player);
        game.setWinner(players.get(0));
        game.endRound();
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryInteract(InventoryInteractEvent event) {
        if (!event.getWhoClicked().getWorld().getName().equals("world")) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (plugin.getGamesManager().getPlayerInGameGame(event.getPlayer()) != null) return;
        try {
            if (event.getItem() == null) return;
            if (event.getItem().equals(InventoryUtils.duelsItem)) {
                McGUI gui = new McGUI("§bDuels Viewer", GuiLines.ONE_LINE_FIVE_SLOTS, plugin);
                GUItem uhcButton = new GUICustomItem(Material.IRON_SWORD)
                        .setName("§aUHC Duel")
                        .setLore("§7Items:", "§8Iron Sword x1", "§8Fishing Rood x1", "§8Bow x1", "§8Iron Axe x1",
                                "§8Golden Apples x8", "§8Oak Planks x64", "§8Lava Buket x1", "§8Water Buket x1",
                                "§8Arrows x16")
                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true)
                        .setFlag(ItemFlag.HIDE_ENCHANTS);
                gui.createButton(uhcButton, 2, new GUIButtonEvents(() -> {
                    event.getPlayer().performCommand("playuhc");
                    event.getPlayer().closeInventory();
                }));
                gui.fillBlanksWith(Material.STAINED_GLASS_PANE, "§f");
                gui.openInventoryTo(event.getPlayer());
            }
        } catch (NullPointerException e) { e.printStackTrace(); }
    }
}
