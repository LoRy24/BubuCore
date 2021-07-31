package org.bubumc.bubucore.commands;

import com.github.lory24.mcuitils.McGUI;
import com.github.lory24.mcuitils.api.*;
import com.github.lory24.mcuitils.api.animating.ItemAnimation;
import com.github.lory24.mcuitils.utils.GuiLines;
import org.bubumc.bubucore.BubuCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;


public class SetRankGUI implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

        if (!(sender instanceof Player)) return false;

        if (!sender.hasPermission("rank.set")) {
            sender.sendMessage("§cNon hai il permesso per usare questo comando!");
            return false;
        }

        if (args.length < 1) return false;

        if (!plugin.getRanksManager().isSaved(args[0])) {
            sender.sendMessage("§cGiocatore non trovato!");
            return false;
        }

        McGUI setRankGUI = new McGUI("SetRank: " + args[0], GuiLines.SIX_LINES, plugin);
        setRankGUI.fillBlanksWith(Material.STAINED_GLASS_PANE, "§f");

        GUItem targetHead = new GUIHead().setSkullOwner(args[0]).setName("§aRank di §f" + args[0]).setLore("§7Rank: " +
                plugin.getRanksManager().loadRankType(args[0]).getS());
        setRankGUI.createButton(targetHead, 13, new GUIButtonEvents(() -> {}));

        GUItem setRoleButton = new GUICustomItem(Material.STAINED_GLASS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,
                1, true).setFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Imposta questo rank al giocatore "
                + args[0]);

        String[] names = {"§4Owner", "§cAdmin", "§2Mod",
                "§9Developer", "§6Builder", "§dBubu", "§bMVP", "§aVIP", "§7Utente"},
                roles = {"OWNER", "ADMIN", "MOD", "DEVELOPER", "BUILDER", "BUBU", "MVP", "VIP", "USER"};
        int[] indexes = {29, 30, 31, 32, 33, 38, 39, 41, 42}, colors =
                {14, 14, 13, 11, 1, 6, 3, 5, 7};

        for (int i = 0; i < 9; i++) {
            int finalI = i;
            setRankGUI.createButton(setRoleButton.setName("§fImposta ruolo: " + names[i]).setType(colors[i]), indexes[i],
                    new GUIButtonEvents(() -> {
                        ((Player) sender).performCommand("setrank " + args[0]
                                + " " + roles[finalI]);
                        setRankGUI.createButton(targetHead.setLore("§7Rank: " + plugin.getRanksManager().loadRankType(
                                args[0]).getS()), 13, new GUIButtonEvents(() -> {}));
                    }));
        }

        setRankGUI.openInventoryTo((Player) sender);
        return true;
    }
}
