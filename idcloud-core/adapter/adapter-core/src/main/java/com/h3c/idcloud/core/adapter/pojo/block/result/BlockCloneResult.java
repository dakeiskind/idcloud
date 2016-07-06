package com.h3c.idcloud.core.adapter.pojo.block.result;


import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class BlockCloneResult extends BaseResult {

    private String description;

    private String id;

    private String name;

    private String size;

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
