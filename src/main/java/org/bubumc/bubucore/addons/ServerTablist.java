package org.bubumc.bubucore.addons;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bubumc.bubucore.BubuCore;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;

@SuppressWarnings("SpellCheckingInspection")
public class ServerTablist {
    private static final BubuCore plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");

    public static void setTablist(final Player player) {
        PacketPlayOutPlayerListHeaderFooter tablistPacket = new PacketPlayOutPlayerListHeaderFooter();
        Object header = new ChatComponentText("\n§d§lBubu§f§lMC §d§lNetwork\n");
        Object footer = new ChatComponentText("\n                 §fFree §dCandies                 \n");
        try {
            Field a = tablistPacket.getClass().getDeclaredField("a");
            a.setAccessible(true);
            a.set(tablistPacket, header);

            Field b = tablistPacket.getClass().getDeclaredField("b");
            b.setAccessible(true);
            b.set(tablistPacket, footer);

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(tablistPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
