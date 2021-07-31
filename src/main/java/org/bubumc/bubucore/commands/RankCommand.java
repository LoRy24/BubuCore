package org.bubumc.bubucore.commands;

import org.bubumc.bubucore.BubuCore;
import org.bubumc.bubucore.core.ranking.Rank;
import org.bubumc.bubucore.core.ranking.RankType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class RankCommand implements CommandExecutor, TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> onlinePlayers = new ArrayList<>();
        for (Player p: Bukkit.getOnlinePlayers()) onlinePlayers.add(p.getName());
        return args.length == 1 ? onlinePlayers : args.length == 2 ? new ArrayList<>(Arrays.asList("OWNER", "ADMIN", "MOD", "DEVELOPER",
                "BUILDER", "BUBU", "MVP", "VIP", "USER")) : new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

        if (!sender.hasPermission("rank.set")) {
            sender.sendMessage("§cNon hai il permesso per usare questo comando!");
            return false;
        }

        if (!(args.length == 2)) { sender.sendMessage("§cSintassi del comando errata!"); return false; }

        if (!plugin.getRanksManager().isSaved(args[0])) {
            sender.sendMessage("§cGiocatore non trovato!");
            return false;
        }

        if (Arrays.stream(RankType.values()).noneMatch(s -> s.toString()
                .equals(args[1]))) {
            sender.sendMessage("§cRank non trovato!");
            return false;
        }

        Player player = Bukkit.getPlayerExact(args[0]);

        sender.sendMessage("§aIl rank del giocatore " + args[0] + " è stato impostato a: " + args[1]);
        if (player != null) {
            plugin.getRanksManager().updateRankType(player, new Rank(RankType.valueOf(args[1])));
            plugin.getPermissionsManager().setPermissionsToPlayer(player, plugin.getRanksManager().getRanks()
                    .get(player).getRankPermissions().getPermissions());
            return true;
        }
        plugin.getRanksManager().saveRankFromName(args[0], new Rank(
                RankType.valueOf(args[1])));
        return true;
    }
}
