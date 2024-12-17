package org.mark.regenBlock.service;

import com.google.inject.Inject;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mark.regenBlock.entity.Coordinate;
import org.mark.regenBlock.entity.UserRegenBlockStatus;
import org.mark.regenBlock.manager.UserRegenBlockStatusManager;

public class UserRegenBlockCoordinateService {

    @Inject
    private UserRegenBlockStatusManager userRegenBlockCoordinateManager;

    public void create(Player player, String regenBlockName) {
        UserRegenBlockStatus userRegenBlockStatus = new UserRegenBlockStatus(regenBlockName);

        userRegenBlockCoordinateManager.save(player, userRegenBlockStatus);
    }

    public void isFirstCoordinateSet(Player player, Location location) {
        Coordinate coordinate = new Coordinate(location);

        UserRegenBlockStatus userRegenBlockStatus = userRegenBlockCoordinateManager.get(player);

        Pair<Coordinate, Coordinate> coordinateCoordinatePair =
                userRegenBlockCoordinateManager.has(player) && userRegenBlockStatus.getCoordinates() != null
                ? Pair.of(coordinate, userRegenBlockStatus.getCoordinates().getRight())
                : Pair.of(coordinate, null);


        userRegenBlockStatus.changeCoordinates(coordinateCoordinatePair);

        userRegenBlockCoordinateManager.save(player, userRegenBlockStatus);
    }

    public void isSecondCoordinateSet(Player player, Location location) {
        Coordinate coordinate = new Coordinate(location);

        UserRegenBlockStatus userRegenBlockStatus = userRegenBlockCoordinateManager.get(player);

        Pair<Coordinate, Coordinate> coordinateCoordinatePair =
                userRegenBlockCoordinateManager.has(player) && userRegenBlockStatus.getCoordinates() != null
                ? Pair.of(userRegenBlockStatus.getCoordinates().getLeft(), coordinate)
                : Pair.of(null, coordinate);

        userRegenBlockStatus.changeCoordinates(coordinateCoordinatePair);

        userRegenBlockCoordinateManager.save(player, userRegenBlockStatus);
    }

    public boolean checkConfirm(Player player) {
        Pair<Coordinate, Coordinate> coordinateCoordinatePair = userRegenBlockCoordinateManager.get(player).getCoordinates();

        if (coordinateCoordinatePair != null && coordinateCoordinatePair.getRight() != null && coordinateCoordinatePair.getLeft() != null) {
            return true;
        }

        return false;
    }

    public boolean hasSetting(Player player) {
        return userRegenBlockCoordinateManager.has(player);
    }

    public UserRegenBlockStatus confirm(Player player) {
        UserRegenBlockStatus userRegenBlockStatus = userRegenBlockCoordinateManager.get(player);
        userRegenBlockCoordinateManager.remove(player);
        return userRegenBlockStatus;
    }
}
