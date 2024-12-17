package org.mark.regenBlock.uitls;


import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class HashMapUtil {

    public static <K, V> K findKeyByValue(HashMap<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
