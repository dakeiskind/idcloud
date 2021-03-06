package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

import java.util.List;

public class KvmVolumeVo {
    private String userId;
    private String name;
    private String tenantId;
    private String sourceVolid;
    private String snapshotId;
    private String id;
    private String size;
    private List<KvmVolumeAttachment> attachments;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSourceVolid() {
        return sourceVolid;
    }

    public void setSourceVolid(String sourceVolid) {
        this.sourceVolid = sourceVolid;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<KvmVolumeAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<KvmVolumeAttachment> attachments) {
        this.attachments = attachments;
    }

}
