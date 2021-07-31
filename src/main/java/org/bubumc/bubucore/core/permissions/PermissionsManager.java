package org.bubumc.bubucore.core.permissions;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("SpellCheckingInspection")
public class PermissionsManager {

    @Getter private final HashMap<UUID, PermissionAttachment> permissions;

    public PermissionsManager() {
        permissions = new HashMap<>();
    }

    private void initAttachment(Player player) {
        PermissionAttachment attachment = player.addAttachment(Bukkit.getPluginManager().getPlugin("BubuCore"));
        this.permissions.put(player.getUniqueId(), attachment);
    }

    public void setPermissionsToPlayer(Player player, ArrayList<String> permissions) {
        if (!this.permissions.containsKey(player.getUniqueId()))
            initAttachment(player);
        PermissionAttachment a =
                this.permissions.get(player.getUniqueId());
        a.getPermissions().forEach((key, value) -> a.unsetPermission(key));
        permissions.forEach(s -> a.setPermission(s, true));
    }
}
