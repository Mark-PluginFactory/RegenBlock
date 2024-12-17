package org.mark.regenBlock.service;

import com.google.inject.Inject;
import org.apache.commons.lang3.tuple.Pair;
import org.mark.regenBlock.entity.Coordinate;
import org.mark.regenBlock.entity.RegenBlock;
import org.mark.regenBlock.manager.RegenBlockManager;

public class RegenBlockService {

    @Inject
    private RegenBlockManager regenBlockStatusManager;

    public void create(String name, Pair<Coordinate, Coordinate> pair) {
        RegenBlock regenBlock = RegenBlock.create(name, pair);

        regenBlockStatusManager.addBlock(regenBlock);
    }
}
