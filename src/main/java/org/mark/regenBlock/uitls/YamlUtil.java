package org.mark.regenBlock.uitls;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static io.vavr.API.None;
import static io.vavr.API.Try;

public class YamlUtil {
    public static String serializeToYaml(Map<String, Object> map) {
        YamlConfiguration section = new YamlConfiguration();
        map.forEach(section::set);
        return section.saveToString();
    }

    public static Option<Exception> saveToYaml(File file, Map<String, Object> map) {
        if (map.isEmpty()) {
            return None();
        }
        String serialized = serializeToYaml(map);
        try {
            FileUtils.write(file, serialized, StandardCharsets.UTF_8);
            return Option.none();
        } catch (IOException e) {
            return Option.of(e);
        }
    }

    public static Try<Map<String, Object>> loadFromYaml(File file) {
        return Try(() -> {
            // 파일 내용을 문자열로 읽음
            String contents = FileUtils.readFileToString(file, StandardCharsets.UTF_8)
                    .replace("\t", "  ")
                    .replaceAll("(?m)^\\s*$", "");

            // YAMLConfiguration에 수정된 내용을 로드
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration.loadFromString(contents);

            // 최상위 섹션을 Map으로 변환
            return sectionToMap(yamlConfiguration);
        });
    }

    // MemorySection 데이터를 Map으로 변환하는 메서드
    private static Map<String, Object> sectionToMap(ConfigurationSection section) {
        Map<String, Object> map = new HashMap<>();
        for (String key : section.getKeys(false)) {
            Object value = section.get(key);
            if (value instanceof ConfigurationSection) {
                // 하위 ConfigurationSection도 Map으로 변환
                map.put(key, sectionToMap((ConfigurationSection) value));
            } else {
                map.put(key, value);
            }
        }
        return map;
    }
}
