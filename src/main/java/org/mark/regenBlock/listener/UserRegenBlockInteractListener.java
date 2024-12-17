package org.mark.regenBlock.listener;

import com.google.inject.Inject;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.mark.regenBlock.Plugin;
import org.mark.regenBlock.entity.UserRegenBlockStatus;
import org.mark.regenBlock.service.RegenBlockService;
import org.mark.regenBlock.service.UserRegenBlockCoordinateService;

import java.util.Objects;

import static org.bukkit.inventory.EquipmentSlot.HAND;
import static org.mark.regenBlock.constant.Lang.*;

public class UserRegenBlockInteractListener implements Listener {

    @Inject
    private UserRegenBlockCoordinateService userRegenBlockCoordinateService;

    @Inject
    private RegenBlockService regenBlockStatusService;

    public UserRegenBlockInteractListener() {
        Plugin.setListener(this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (commandValidation(event)) return;

        Player player = event.getPlayer();

        event.setCancelled(true);

        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();  // 클릭한 블럭
        Location location = Objects.requireNonNull(clickedBlock).getLocation();

        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();

        // 좌클릭일 경우
        if (action.isLeftClick()) {
            sendLocationListMessage(player, action, blockX, blockY, blockZ);
            userRegenBlockCoordinateService.isFirstCoordinateSet(player, location);
        }

        // 우클릭일 경우
        if (action.isRightClick()) {
            sendLocationListMessage(player, action, blockX, blockY, blockZ);
            userRegenBlockCoordinateService.isSecondCoordinateSet(player, location);
        }

        boolean isConfirm = userRegenBlockCoordinateService.checkConfirm(player);

        if (isConfirm) {
            UserRegenBlockStatus regenBlockStatus = userRegenBlockCoordinateService.confirm(player);
            player.sendMessage(SET_COORDINATES_COMPETED.formatWithPrefix(regenBlockStatus.getRegenBlockName()));
            regenBlockStatusService.create(regenBlockStatus.getRegenBlockName(), regenBlockStatus.getCoordinates());
        }
    }

    private boolean commandValidation(PlayerInteractEvent event) {
        if (!userRegenBlockCoordinateService.hasSetting(event.getPlayer())) return true;
        if (!(event.getAction().isLeftClick() || event.getAction().isRightClick())) return true;
        if (!event.hasBlock()) return true;
        if (!(Objects.requireNonNull(event.getHand()) == HAND)) return true;

        return false;
    }

    private void sendLocationListMessage(Player player, Action action, int x, int y, int z) {
        String message = action == Action.LEFT_CLICK_BLOCK
                ? FIRST.format()
                : SECOND.format();

        player.sendMessage(SET_COORDINATES.formatWithPrefix(message));
        player.sendMessage(COORDINATES.formatWithPrefix(x, y, z));
    }
}
