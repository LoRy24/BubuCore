package org.bubumc.bubucore;

import org.bubumc.bubucore.addons.ServerTablist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Locale;

@SuppressWarnings("SpellCheckingInspection")
public class PluginListener implements Listener {

    private final BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getRanksManager().loadRank(event.getPlayer());
        event.setJoinMessage("§7[§a+§7] §a" + event.getPlayer().getName());
        plugin.getPermissionsManager().setPermissionsToPlayer(event.getPlayer(), plugin.getRanksManager().getRanks()
                .get(event.getPlayer()).getRankPermissions().getPermissions());
        ServerTablist.setTablist(event.getPlayer());
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
        plugin.getGamesManager().removePlayerFromHisQueue(
                event.getPlayer());
    }
}
