package org.bubumc.bubucore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("SpellCheckingInspection")
public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return false;
        if (!sender.hasPermission("fly.use")) {
            sender.sendMessage("§cNon hai il permesso per usare questo comando!");
            return false;
        }
        Player player = (Player) sender;
        player.sendMessage(!player.getAllowFlight() ? "§7[§a+§7] §aFly Attivata!" : "§7[§c-§7] §cFly Disattivata!");
        player.setAllowFlight(!player.getAllowFlight());
        return true;
    }
}
