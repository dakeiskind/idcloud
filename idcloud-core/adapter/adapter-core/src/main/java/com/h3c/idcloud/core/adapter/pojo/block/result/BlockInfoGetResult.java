package com.h3c.idcloud.core.adapter.pojo.block.result;


import com.h3c.idcloud.core.adapter.pojo.block.Block;
import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

import java.util.List;

public class BlockInfoGetResult extends BaseResult {

    private List<Block> blocks;
    private String description;

    private String id;

    private String name;

    private String size;

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
