package org.bubumc.bubucore.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlManager {
    private final Connection connection;

    public SqlManager(Connection connection) {
        this.connection = connection;
    }

    public List<String> getStringList(String sqlQuery, String listColumn) {
        final List<String> columnData = new ArrayList<>();
        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) columnData.add(rs.getString(listColumn));
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnData;
    }

    public String getString(String sqlQuery, String column) {
        String value = null;
        try {
            Statement st = this.connection.createStatement();
            ResultSet resSet = st.executeQuery(sqlQuery);
            while (resSet.next()) value = resSet.getString(column);
            st.close();
            resSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void executeUpdate(String update) {
        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(update);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
