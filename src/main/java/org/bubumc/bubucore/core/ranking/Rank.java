package org.bubumc.bubucore.core.ranking;

import lombok.Getter;

public class Rank {
    @Getter private final RankType type;
    @Getter private final RankPermissions rankPermissions;

    public Rank(RankType type) {
        this.type = type;
        rankPermissions = RankPermissions.valueOf(type.toString());
    }
}
