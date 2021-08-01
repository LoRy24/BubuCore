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

@SuppressWarnings("SpellCheckingInspection")
@CommandName(name = "leavequeue")
@CommandDescription(description = "Command to leave the queue")
@CommandUsage(usage = "/leavequeue")
public class LeaveQueueCommand extends CommandListener {

    private final BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;

        if (!plugin.getGamesManager().isInActivity((Player) sender)) {
            sender.sendMessage("§cNon sei in nessuna queue. Pertanto non puoi usare questo comando!");
            return;
        }

        plugin.getGamesManager().removePlayerFromHisQueue((Player) sender);
        sender.sendMessage("§aSei uscito dalla queue attuale!");
    }

    @Override
    public List<String> tabComplete(String[] strings) {
        return new ArrayList<>();
    }
}
