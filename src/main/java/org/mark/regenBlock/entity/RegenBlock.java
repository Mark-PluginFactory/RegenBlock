package org.mark.regenBlock.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RegenBlock {

    private String regenBlockName;
    private Pair<Coordinate, Coordinate> coordinates;
    private List<Material> blocks;

    public RegenBlock(String regenBlockName) {
        this.regenBlockName = regenBlockName;
    }

    public static RegenBlock create(String regenBlockName, Pair<Coordinate, Coordinate> coordinates) {
        return new RegenBlock(
                regenBlockName,
                coordinates,
                new ArrayList<>(
                        Collections.singleton(Material.STONE)
                )
        );
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
        if (blocks != null) {
            map.put("blocks", blocks.stream()
                    .map(Material::name) // 예: 상태 데이터 추가
                    .collect(Collectors.toList()));
        }
        return map;
    }

    // Map 데이터를 객체로 역직렬화
    public static RegenBlock deserialize(Map<String, Object> map) {
        RegenBlock status = new RegenBlock((String) map.get("regenBlockName"));
        if (map.containsKey("firstCoordinate") && map.containsKey("secondCoordinate")) {
            Coordinate first = Coordinate.deserialize((Map<String, Object>) map.get("firstCoordinate"));
            Coordinate second = Coordinate.deserialize((Map<String, Object>) map.get("secondCoordinate"));
            status.changeCoordinates(Pair.of(first, second));
        }

        if (map.containsKey("blocks")) {
            List<String> blockList = (List<String>) map.get("blocks");
            List<Material> blocks = blockList.stream()
                    .map(name -> {
                        Material material = Material.getMaterial(name);
                        if (material == null) {
                            throw new IllegalArgumentException("Invalid material key: " + name);
                        }
                        return material;
                    })
                    .collect(Collectors.toList());
            status.blocks = blocks;
        }

        return status;
    }
}
