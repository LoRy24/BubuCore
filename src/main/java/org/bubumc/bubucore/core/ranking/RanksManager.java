package org.bubumc.bubucore.core.ranking;

import lombok.Getter;
import org.bubumc.bubucore.BubuCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RanksManager {
    @Getter public final HashMap<Player, Rank> ranks;
    public final BubuCore plugin;

    @SuppressWarnings("SpellCheckingInspection")
    public RanksManager() {
        ranks = new HashMap<>();
        plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");
    }

    public void loadRank(Player player) {
        if (ranks.containsKey(player)) return;
        if (!isSaved(player.getName())) { registerRank(player, new Rank(RankType.USER));
        } else {
            RankType type = RankType.valueOf(plugin.database.getSqlManager().getString("SELECT RankTYPE FROM saved_ranks " +
                    "WHERE PlayerNAME='" + player.getName() + "'", "RankTYPE"));
            this.ranks.put(player, new Rank(type));
        }
    }

    public boolean isSaved(String player) {
        return plugin.database.getSqlManager().getStringList("SELECT PlayerNAME FROM saved_ranks",
                "PlayerNAME").contains(player);
    }

    public void saveRank(Player player) {
        if (isSaved(player.getName())) { updateRank(player); }
        else { initPlayerRank(player); }
    }

    public void saveRankFromName(String player, Rank rank) {
        if (!isSaved(player)) return;
        plugin.database.getSqlManager().executeUpdate("UPDATE saved_ranks SET RankTYPE='" + rank.getType() + "' WHERE PlayerNAME='" + player + "'");
    }

    private void initPlayerRank(Player player) {
        plugin.database.getSqlManager().executeUpdate("INSERT INTO saved_ranks(PlayerNAME, RankTYPE) VALUES('" +
                player.getName() + "', '" + this.ranks.get(player).getType() + "')");
    }

    private void updateRank(Player player) {
        plugin.database.getSqlManager().executeUpdate("UPDATE saved_ranks SET RankTYPE='" + this.ranks.get(player).getType() +
                "' WHERE PlayerNAME='" + player.getName() + "'");
    }

    public void registerRank(Player player, Rank rank) {
        this.ranks.put(player, rank);
        saveRank(player);
    }

    @SuppressWarnings("unused")
    public void unregisterRank(Player player) {
        this.ranks.remove(player);
        plugin.database.getSqlManager().executeUpdate("DELETE FROM saved_ranks WHERE PlayerNAME='" +
                player.getName() + "'");
    }

    public RankType loadRankType(String playerName) {
        return RankType.valueOf(plugin.database.getSqlManager().getString("SELECT RankTYPE FROM saved_ranks WHERE PlayerNAME='" + playerName +
                "'", "RankTYPE"));
    }


    public void updateRankType(Player player, Rank rank) {
        this.ranks.replace(player, rank);
        saveRank(player);
    }
}
