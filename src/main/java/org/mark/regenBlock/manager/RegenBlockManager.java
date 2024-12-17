package org.mark.regenBlock.manager;

import io.vavr.control.Try;
import org.bukkit.Bukkit;
import org.mark.regenBlock.Plugin;
import org.mark.regenBlock.entity.RegenBlock;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mark.regenBlock.constant.FileName.REGEN_BLOCK_STATUS;
import static org.mark.regenBlock.uitls.YamlUtil.loadFromYaml;
import static org.mark.regenBlock.uitls.YamlUtil.saveToYaml;

public class RegenBlockManager {
    private final Map<String, RegenBlock> regenBlocksMap = new HashMap<>();
    private final File file;

    public RegenBlockManager() {
        this.file = new File(Plugin.INSTANCE.getDataFolder(), REGEN_BLOCK_STATUS.getFileName());
        load();
    }

    // 블록 추가
    public void addBlock(RegenBlock regenBlock) {
        regenBlocksMap.put(regenBlock.getRegenBlockName(), regenBlock);

        Bukkit.getAsyncScheduler().runNow(Plugin.INSTANCE, scheduledTask -> {
            save();
        });
    }

    // 블록 제거
    public void removeBlock(String name) {
        regenBlocksMap.remove(name);
        save();
    }

    // 블록 가져오기
    public Optional<RegenBlock> getBlock(String name) {
        return Optional.ofNullable(regenBlocksMap.get(name));
    }

    // 모든 블록 가져오기
    public Map<String, RegenBlock> getAllBlocks() {
        return Collections.unmodifiableMap(regenBlocksMap);
    }

    // YAML에 저장
    public void save() {
        Map<String, Object> serializedMap = regenBlocksMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> entry.getValue().serialize()
                ));

        // regen-blocks 키를 추가해서 저장
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("regen-blocks", serializedMap);

        saveToYaml(file, rootMap)
                .peek(e -> System.out.println("Failed to save regen blocks: " + e));
    }


    // YAML에서 불러오기
    public void load() {
        regenBlocksMap.clear();

        Try<Map<String, Object>> loadedData = loadFromYaml(file);

        loadedData.onSuccess(data -> {
            if (data != null && data.containsKey("regen-blocks")) {
                Map<String, Object> regenBlocksData = (Map<String, Object>) data.get("regen-blocks");

                regenBlocksData.forEach((key, value) -> {
                    if (value instanceof Map) {
                        try {
                            RegenBlock regenBlock = RegenBlock.deserialize((Map<String, Object>) value);
                            regenBlocksMap.put(key, regenBlock);
                        } catch (Exception e) {
                            System.out.println("Error deserializing regen block for key: " + key);
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).onFailure(e -> System.out.println("Failed to load regen blocks: " + e.getMessage()));
    }
}
