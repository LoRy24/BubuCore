package org.bubumc.bubucore.core.ranking;

import lombok.Getter;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public enum RankType {
    OWNER("§7[§4Owner§7] §4"),
    ADMIN("§7[§cAdmin§7] §c"),
    MOD("§7[§2Mod§7] §2"),
    DEVELOPER("§7[§9Dev§7] §9"),
    BUILDER("§7[§6Builder§7] §6"),
    BUBU("§7[§dBubu§7] §d"),
    MVP("§7[§bMVP§7] §b"),
    VIP("§7[§aVIP§7] §a"),
    USER("§7");

    @Getter private final String s;

    RankType(String s) {
        this.s = s;
    }
}
