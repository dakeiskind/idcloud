package com.h3c.idcloud.core.adapter.pojo.block.result;


import com.h3c.idcloud.core.adapter.pojo.block.Block;
import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

import java.util.List;

public class BlockListResult extends BaseResult {

    private List<Block> blocks;

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
