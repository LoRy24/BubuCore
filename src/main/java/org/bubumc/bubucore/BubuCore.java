package org.bubumc.bubucore;

import com.github.lory24.commandapi.CommandManager;
import com.github.lory24.commandapi.api.CommandListener;
import lombok.Getter;
import org.bubumc.bubucore.addons.ServerTablist;
import org.bubumc.bubucore.commands.*;
import org.bubumc.bubucore.core.games.GamesManager;
import org.bubumc.bubucore.core.permissions.PermissionsManager;
import org.bubumc.bubucore.data.SqliteDatabase;
import org.bubumc.bubucore.core.ranking.RanksManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings({"SpellCheckingInspection"})
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

        final CommandManager commandManager = new CommandManager(this);
        CommandListener[] listeners = {new FlyCommand(), new RankCommand(), new SetRankGUI(), new SetSpawn(), new PlayUHCCommand(),
            new LeaveQueueCommand()};
        for (CommandListener cl : listeners) commandManager.registerCommand(cl);

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
