package com.h3c.idcloud.core.adapter.pojo.vm.result;

import java.util.Calendar;

public class SnapshotInfo {

    private String snapshot;
    private String name;
    private String description;
    private Calendar createTime;
    private String createTimeFomat;
    private String state;
    private boolean quiesced;

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeFomat() {
        return createTimeFomat;
    }

    public void setCreateTimeFomat(String createTimeFomat) {
        this.createTimeFomat = createTimeFomat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isQuiesced() {
        return quiesced;
    }

    public void setQuiesced(boolean quiesced) {
        this.quiesced = quiesced;
    }

}
