package org.bubumc.bubucore.addons;

import com.github.lory24.mcuitils.api.GUICustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    public static ItemStack duelsItem = new GUICustomItem(Material.DIAMOND_SWORD)
            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true)
            .setFlag(ItemFlag.HIDE_ENCHANTS)
            .setName("§bDuels")
            .setLore("§7Open the duels viewer")
            .buildToItemStack();

    public static void setHubInventory(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        // Set items
        player.getInventory().setItem(4, duelsItem);
    }
}
