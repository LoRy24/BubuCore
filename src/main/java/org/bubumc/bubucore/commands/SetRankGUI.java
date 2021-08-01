package org.bubumc.bubucore.commands;

import com.github.lory24.commandapi.api.CommandListener;
import com.github.lory24.commandapi.api.annotations.CommandDescription;
import com.github.lory24.commandapi.api.annotations.CommandName;
import com.github.lory24.commandapi.api.annotations.CommandPermission;
import com.github.lory24.commandapi.api.annotations.CommandUsage;
import com.github.lory24.mcuitils.McGUI;
import com.github.lory24.mcuitils.api.*;
import com.github.lory24.mcuitils.utils.GuiLines;
import org.bubumc.bubucore.BubuCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

@CommandName(name = "setrankgui")
@CommandDescription(description = "The gui used to set a rank to a player")
@CommandPermission(permission = "rank.set", noPermissionMessage = "§cNon hai il permesso per usare questo comando!")
@CommandUsage(usage = "/setrankgui <player>")
public class SetRankGUI extends CommandListener {

    @Override
    public void executeCommand(CommandSender sender, String[] args) {

        BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

        if (!(sender instanceof Player)) return;

        if (args.length < 1) return;

        if (!plugin.getRanksManager().isSaved(args[0])) {
            sender.sendMessage("§cGiocatore non trovato!");
            return;
        }

        McGUI setRankGUI = new McGUI("SetRank: " + args[0], GuiLines.SIX_LINES, plugin);
        setRankGUI.fillBlanksWith(Material.STAINED_GLASS_PANE, "§f");

        GUItem targetHead = new GUIHead().setSkullOwner(args[0]).setName("§aRank di §f" + args[0]).setLore("§7Rank: " +
                plugin.getRanksManager().loadRankType(args[0]).getS());
        setRankGUI.createButton(targetHead, 13, new GUIButtonEvents(() -> {}));

        GUItem setRoleButton = new GUICustomItem(Material.STAINED_GLASS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true)
                .setFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Imposta questo rank al giocatore "
                        + args[0]);

        String[] names = {"§4Owner", "§cAdmin", "§2Mod",
                "§9Developer", "§6Builder", "§dBubu", "§bMVP", "§aVIP", "§7Utente"},
                roles = {"OWNER", "ADMIN", "MOD", "DEVELOPER", "BUILDER", "BUBU", "MVP", "VIP", "USER"};
        int[] indexes = {29, 30, 31, 32, 33, 38, 39, 41, 42}, colors =
                {14, 14, 13, 11, 1, 6, 3, 5, 7};

        for (int i = 0; i < 9; i++) {
            int finalI = i;
            setRankGUI.createButton(setRoleButton.setName("§fImposta ruolo: " +
                            names[i]).setType(colors[i]), indexes[i],
                    new GUIButtonEvents(() -> {
                        ((Player) sender).performCommand("setrank " + args[0] + " " + roles[finalI]);
                        setRankGUI.createButton(targetHead.setLore("§7Rank: " + plugin.getRanksManager().loadRankType(args[0])
                                .getS()), 13, new GUIButtonEvents(() -> {}));
                    }));
        }

        setRankGUI.openInventoryTo((Player) sender);
    }

    @Override
    public List<String> tabComplete(String[] strings) {
        List<String> onlinePlayers = new ArrayList<>();
        for (Player p: Bukkit.getOnlinePlayers()) onlinePlayers.add(p.getName());
        return onlinePlayers;
    }
}
