package org.bubumc.bubucore.commands;

import com.github.lory24.commandapi.api.CommandListener;
import com.github.lory24.commandapi.api.annotations.CommandDescription;
import com.github.lory24.commandapi.api.annotations.CommandName;
import com.github.lory24.commandapi.api.annotations.CommandPermission;
import com.github.lory24.commandapi.api.annotations.CommandUsage;
import org.bubumc.bubucore.BubuCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandName(name = "setspawn")
@CommandDescription(description = "Set the spawn location")
@CommandPermission(permission = "spawn.set", noPermissionMessage = "§cNon hai il permesso per usare questo comando!")
@CommandUsage(usage = "/setspawn")
public class SetSpawn extends CommandListener {

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        BubuCore bubuCore = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;

        bubuCore.getConfig().set("Settings.SpawnLocation.World", player.getLocation().getWorld().getName());
        bubuCore.getConfig().set("Settings.SpawnLocation.X",     player.getLocation().getX());
        bubuCore.getConfig().set("Settings.SpawnLocation.Y",     player.getLocation().getY());
        bubuCore.getConfig().set("Settings.SpawnLocation.Z",     player.getLocation().getZ());
        bubuCore.getConfig().set("Settings.SpawnLocation.Pitch", player.getLocation().getPitch());
        bubuCore.getConfig().set("Settings.SpawnLocation.Yaw",   player.getLocation().getYaw());
        bubuCore.saveConfig();

        sender.sendMessage("§aSpawn Impostato!");
    }

    @Override
    public List<String> tabComplete(String[] strings) {
        return new ArrayList<>();
    }
}
