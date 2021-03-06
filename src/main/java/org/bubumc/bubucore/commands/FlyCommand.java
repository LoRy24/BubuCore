package org.bubumc.bubucore.commands;

import com.github.lory24.commandapi.api.CommandListener;
import com.github.lory24.commandapi.api.annotations.CommandDescription;
import com.github.lory24.commandapi.api.annotations.CommandName;
import com.github.lory24.commandapi.api.annotations.CommandPermission;
import com.github.lory24.commandapi.api.annotations.CommandUsage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandName(name = "fly")
@CommandDescription(description = "Toggle the fly mode")
@CommandPermission(permission = "fly.use", noPermissionMessage = "§cNon puoi usare questo comando!")
@CommandUsage(usage = "/fly")
public class FlyCommand extends CommandListener {

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        player.sendMessage(!player.getAllowFlight() ? "§7[§a+§7] §aFly Attivata!" : "§7[§c-§7] §cFly Disattivata!");
        player.setAllowFlight(!player.getAllowFlight());
    }

    @Override
    public List<String> tabComplete(String[] strings) {
        return new ArrayList<>();
    }
}
