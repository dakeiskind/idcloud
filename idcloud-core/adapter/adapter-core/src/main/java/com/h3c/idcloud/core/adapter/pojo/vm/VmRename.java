package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class VmRename extends Base {

    private String id;
    private String name;
    private String nameTobe;

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

    public String getNameTobe() {
        return nameTobe;
    }

    public void setNameTobe(String nameTobe) {
        this.nameTobe = nameTobe;
    }

}
