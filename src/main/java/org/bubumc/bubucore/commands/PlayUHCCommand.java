package org.bubumc.bubucore.commands;

import com.github.lory24.commandapi.api.CommandListener;
import com.github.lory24.commandapi.api.annotations.CommandDescription;
import com.github.lory24.commandapi.api.annotations.CommandName;
import com.github.lory24.commandapi.api.annotations.CommandUsage;
import org.bubumc.bubucore.BubuCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandName(name = "playuhc")
@CommandDescription(description = "Start a new UHC queue")
@CommandUsage(usage = "/playuhc")
public class PlayUHCCommand extends CommandListener {

    private final BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;

        if (plugin.getGamesManager().isInActivity((Player) sender)) {
            sender.sendMessage("§cDevi uscire dalla queue attuale per giocare a questa modalità!");
            return;
        }

        plugin.getGamesManager().playUHC((Player) sender);
    }

    @Override
    public List<String> tabComplete(String[] strings) {
        return new ArrayList<>();
    }
}
