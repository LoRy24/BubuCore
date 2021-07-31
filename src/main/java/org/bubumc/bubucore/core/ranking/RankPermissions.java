package org.bubumc.bubucore.core.ranking;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings({"SpellCheckingInspection"})
public enum RankPermissions {
    OWNER(new ArrayList<>(Collections.singletonList("*.*"))),
    ADMIN(new ArrayList<>(Arrays.asList("fly.use", "command.override", "minecraft.command.ban",
            "minecraft.command.pardon", "minecraft.command.kick"))),
    MOD(new ArrayList<>(Arrays.asList("fly.use", "command.override", "minecraft.command.ban", "minecraft.command.pardon",
            "minecraft.command.kick"))),
    DEVELOPER(new ArrayList<>(Arrays.asList("fly.use", "command.override"))),
    BUILDER(new ArrayList<>(Arrays.asList("fly.use", "worldedit.*"))),
    BUBU(new ArrayList<>(Collections.singletonList("fly.use"))),
    MVP(new ArrayList<>(Collections.singletonList("fly.use"))),
    VIP(new ArrayList<>(Collections.singletonList("fly.use"))),
    USER(new ArrayList<>());

    @Getter private final ArrayList<String> permissions;

    RankPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
    }
}
