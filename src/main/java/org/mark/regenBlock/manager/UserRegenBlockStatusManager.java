package org.mark.regenBlock.manager;

import org.bukkit.entity.Player;
import org.mark.regenBlock.entity.UserRegenBlockStatus;

import java.util.HashMap;

public class UserRegenBlockStatusManager {
    private final HashMap<Player, UserRegenBlockStatus> playerRegenBlockStatusMap = new HashMap<>();

    public void save(Player player, UserRegenBlockStatus status) {
        playerRegenBlockStatusMap.put(player, status);
    }

    public UserRegenBlockStatus get(Player player) {
        return playerRegenBlockStatusMap.get(player);
    }

    public void remove(Player player) {
        playerRegenBlockStatusMap.remove(player);
    }

    public boolean has(Player player) {
        return playerRegenBlockStatusMap.containsKey(player);
    }
}
