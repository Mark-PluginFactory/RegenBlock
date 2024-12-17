package org.mark.regenBlock.entity;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserRegenBlockStatus {

    private String regenBlockName;
    private Pair<Coordinate, Coordinate> coordinates;

    public UserRegenBlockStatus(String regenBlockName) {
        this.regenBlockName = regenBlockName;
    }

    public void changeCoordinates(Pair<Coordinate, Coordinate> newCoordinates) {
        coordinates = newCoordinates;
    }

    // 객체를 Map 형태로 직렬화
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("regenBlockName", regenBlockName);
        if (coordinates != null) {
            map.put("firstCoordinate", coordinates.getLeft().serialize());
            map.put("secondCoordinate", coordinates.getRight().serialize());
        }
        return map;
    }

    // Map 데이터를 객체로 역직렬화
    public static UserRegenBlockStatus deserialize(Map<String, Object> map) {
        UserRegenBlockStatus status = new UserRegenBlockStatus((String) map.get("regenBlockName"));
        if (map.containsKey("firstCoordinate") && map.containsKey("secondCoordinate")) {
            Coordinate first = Coordinate.deserialize((Map<String, Object>) map.get("firstCoordinate"));
            Coordinate second = Coordinate.deserialize((Map<String, Object>) map.get("secondCoordinate"));
            status.changeCoordinates(Pair.of(first, second));
        }
        return status;
    }
}
