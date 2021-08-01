package org.bubumc.bubucore.commands;

import com.github.lory24.commandapi.api.CommandListener;
import com.github.lory24.commandapi.api.annotations.CommandDescription;
import com.github.lory24.commandapi.api.annotations.CommandName;
import com.github.lory24.commandapi.api.annotations.CommandPermission;
import com.github.lory24.commandapi.api.annotations.CommandUsage;
import org.bubumc.bubucore.BubuCore;
import org.bubumc.bubucore.core.ranking.Rank;
import org.bubumc.bubucore.core.ranking.RankType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CommandName(name = "setrank")
@CommandDescription(description = "Set the rank")
@CommandPermission(permission = "rank.set", noPermissionMessage = "§cNon hai il permesso per usare questo comando!")
@CommandUsage(usage = "/setrank <player> <rank>")
public class RankCommand extends CommandListener {

    @Override
    public List<String> tabComplete(String[] args) {
        List<String> onlinePlayers = new ArrayList<>();
        for (Player p: Bukkit.getOnlinePlayers()) onlinePlayers.add(p.getName());
        return args.length == 1 ? onlinePlayers : args.length == 2 ? new ArrayList<>(Arrays.asList("OWNER", "ADMIN",
                "MOD", "DEVELOPER", "BUILDER", "BUBU", "MVP", "VIP", "USER")) :
                new ArrayList<>();
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {

        BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

        if (!(args.length == 2)) {
            if (!plugin.getRanksManager().isSaved(args[0])) {
                sender.sendMessage("§cGiocatore non trovato!");
                return;
            }

            if (Arrays.stream(RankType.values()).noneMatch(s -> s.toString()
                    .equals(args[1]))) {
                sender.sendMessage("§cRank non trovato!");
                return;
            }

            Player player = Bukkit.getPlayerExact(args[0]);

            sender.sendMessage("§aIl rank del giocatore " + args[0] + " è stato impostato a: " + args[1]);
            if (player != null) {
                plugin.getRanksManager().updateRankType(player, new Rank(RankType.valueOf(args[1])));
                plugin.getPermissionsManager().setPermissionsToPlayer(player, plugin.getRanksManager().getRanks()
                        .get(player).getRankPermissions().getPermissions());
                return;
            }
            plugin.getRanksManager().saveRankFromName(args[0], new Rank(
                    RankType.valueOf(args[1])));
            return;
        }

        sender.sendMessage("§cSintassi del comando errata!");
    }
}
