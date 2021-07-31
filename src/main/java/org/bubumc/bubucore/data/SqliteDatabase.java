package org.bubumc.bubucore.data;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("SpellCheckingInspection")
public class SqliteDatabase {
    @Getter private final File file;
    private Connection connection;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public SqliteDatabase() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("BubuCore");
        file = new File(plugin.getDataFolder() + File.separator + "data.sqlite");
        if (file.exists()) return;
        try {
            if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
            file.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("SqlNoDataSourceInspection")
    public void setupDatabase() {
        try {
            Statement st = getConnection().createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS saved_ranks(PlayerNAME TEXT, RankTYPE TEXT)");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) return connection;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
