package org.bubumc.bubucore.core.ranking;

import lombok.Getter;
import org.bubumc.bubucore.BubuCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RanksManager {
    @Getter public final HashMap<Player, Rank> ranks;
    public final BubuCore plugin;

    @SuppressWarnings("SpellCheckingInspection")
    public RanksManager() {
        ranks = new HashMap<>();
        plugin = (BubuCore) Bukkit.getPluginManager().getPlugin("BubuCore");
    }

    @SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
    public void loadRank(Player player) {
        if (ranks.containsKey(player)) return;
        if (!isSaved(player.getName())) { registerRank(player, new Rank(RankType.USER));
        } else {
            try {
                Statement st = plugin.database.getConnection().createStatement();
                ResultSet query = st.executeQuery("SELECT RankTYPE FROM saved_ranks WHERE PlayerNAME='" + player.getName() + "'");
                RankType type = null;
                while (query.next()) type = RankType.valueOf(query.getString("RankTYPE"));
                this.ranks.put(player, new Rank(type));
                query.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
    public boolean isSaved(String player) {
        boolean b = false;
        try {
            Statement st = plugin.database.getConnection().createStatement();
            ResultSet query = st.executeQuery("SELECT PlayerNAME FROM saved_ranks");
            List<String> savedPlayers = new ArrayList<>();
            while (query.next()) { savedPlayers.add(
                    query.getString("PlayerNAME")); }
            b = savedPlayers.contains(player);
            query.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    @SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
    public void saveRank(Player player) {
        if (isSaved(player.getName())) { updateRank(player); }
        else { initPlayerRank(player); }
    }

    @SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
    public void saveRankFromName(String player, Rank rank) {
        if (!isSaved(player)) return;
        try {
            Statement st = plugin.database.getConnection().createStatement();
            st.executeUpdate("UPDATE saved_ranks SET RankTYPE='" + rank.getType() + "' WHERE PlayerNAME='" + player + "'");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
    private void initPlayerRank(Player player) {
        try {
            Statement st = plugin.database.getConnection().createStatement();
            st.executeUpdate("INSERT INTO saved_ranks(PlayerNAME, RankTYPE) VALUES('" + player.getName() + "', '" +
                    this.ranks.get(player).getType() + "')");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
    private void updateRank(Player player) {
        try {
            Statement st = plugin.database.getConnection().createStatement();
            st.executeUpdate("UPDATE saved_ranks SET RankTYPE='" + this.ranks.get(player).getType() + "' WHERE PlayerNAME='" +
                    player.getName() + "'");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerRank(Player player, Rank rank) {
        this.ranks.put(player, rank);
        saveRank(player);
    }

    @SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
    public void unregisterRank(Player player) {
        this.ranks.remove(player);
        try {
            Statement st = plugin.database.getConnection().createStatement();
            st.executeUpdate("DELETE FROM saved_ranks WHERE PlayerNAME='" + player.getName() + "'");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
    public RankType loadRankType(String playerName) {
        try {
            Statement st = plugin.database.getConnection().createStatement();
            ResultSet query = st.executeQuery("SELECT RankTYPE FROM saved_ranks WHERE PlayerNAME='" + playerName + "'");
            RankType type = null;
            while (query.next()) type = RankType.valueOf(query.getString("RankTYPE"));
            return type;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateRankType(Player player, Rank rank) {
        this.ranks.replace(player, rank);
        saveRank(player);
    }
}
