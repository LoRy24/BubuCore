package org.bubumc.bubucore;

import lombok.Getter;
import org.bubumc.bubucore.addons.ServerTablist;
import org.bubumc.bubucore.commands.FlyCommand;
import org.bubumc.bubucore.commands.RankCommand;
import org.bubumc.bubucore.commands.SetRankGUI;
import org.bubumc.bubucore.core.games.GamesManager;
import org.bubumc.bubucore.core.permissions.PermissionsManager;
import org.bubumc.bubucore.data.SqliteDatabase;
import org.bubumc.bubucore.core.ranking.RanksManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings({"SpellCheckingInspection", "CommentedOutCode"})
public final class BubuCore extends JavaPlugin {

    public SqliteDatabase database;
    @Getter private RanksManager ranksManager;
    @Getter private ConfigValues configValues;
    @Getter private PermissionsManager permissionsManager;
    @Getter private GamesManager gamesManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        configValues = new ConfigValues(this.getConfig());

        database = new SqliteDatabase();
        database.setupDatabase();

        ranksManager = new RanksManager();
        permissionsManager = new PermissionsManager();

        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("setrank").setExecutor(new RankCommand());
        this.getCommand("setrank").setTabCompleter(new RankCommand());
        this.getCommand("setrankgui").setExecutor(new SetRankGUI());

        for (Player p: Bukkit.getOnlinePlayers()) {
            ranksManager.loadRank(p);
            getPermissionsManager().setPermissionsToPlayer(p, getRanksManager().getRanks().get(p)
                    .getRankPermissions().getPermissions());
            ServerTablist.setTablist(p);
        }

        Bukkit.getPluginManager().registerEvents(new PluginListener(), this);

        gamesManager = new GamesManager(this);

        getLogger().info("Plugin Abilitato!");
    }
}
