package org.bubumc.bubucore.data;

import lombok.Getter;

public enum SqlDefaultStrings {
    CREATE_SAVED_RANKS_TABLE("CREATE TABLE IF NOT EXISTS saved_ranks(PlayerNAME TEXT, RankTYPE TEXT)");

    @Getter private final String sql;

    SqlDefaultStrings(String sql) {
        this.sql = sql;
    }
}
