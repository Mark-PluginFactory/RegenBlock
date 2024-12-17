package org.mark.regenBlock.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class Coordinate {
    private Double x;
    private Double y;
    private Double z;

    public Coordinate(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        return map;
    }

    public static Coordinate deserialize(Map<String, Object> map) {
        try {
            double x = ((Number) map.get("x")).doubleValue();
            double y = ((Number) map.get("y")).doubleValue();
            double z = ((Number) map.get("z")).doubleValue();
            return new Coordinate(x, y, z);
        } catch (ClassCastException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid coordinate data: " + map, e);
        }
    }

}
