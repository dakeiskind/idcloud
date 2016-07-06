package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

/**
 * Res volume 类.
 *
 * @author Chaohong.Mao
 */
public class ResVolume implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The Res volume sid.
     */
    private String resVolumeSid;
    /**
     * The Res storage sid.
     */
    private String resStorageSid;
    /**
     * The Volume name.
     */
    private String volumeName;
    /**
     * The Volume wwn.
     */
    private String volumeWwn;
    /**
     * The Size.
     */
    private Long size;
    /**
     * The Status.
     */
    private String status;
    /**
     * The Status name.
     */
    private String statusName;

    /**
     * 获得 status name.
     *
     * @return the status name
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * 设定 status name.
     *
     * @param statusName the status name
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 获得 res volume sid.
     *
     * @return the res volume sid
     */
    public String getResVolumeSid() {
        return resVolumeSid;
    }

    /**
     * 设定 res volume sid.
     *
     * @param resVolumeSid the res volume sid
     */
    public void setResVolumeSid(String resVolumeSid) {
        this.resVolumeSid = resVolumeSid;
    }

    /**
     * 获得 res storage sid.
     *
     * @return the res storage sid
     */
    public String getResStorageSid() {
        return resStorageSid;
    }

    /**
     * 设定 res storage sid.
     *
     * @param resStorageSid the res storage sid
     */
    public void setResStorageSid(String resStorageSid) {
        this.resStorageSid = resStorageSid;
    }

    /**
     * 获得 volume name.
     *
     * @return the volume name
     */
    public String getVolumeName() {
        return volumeName;
    }

    /**
     * 设定 volume name.
     *
     * @param volumeName the volume name
     */
    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    /**
     * 获得 volume wwn.
     *
     * @return the volume wwn
     */
    public String getVolumeWwn() {
        return volumeWwn;
    }

    /**
     * 设定 volume wwn.
     *
     * @param volumeWwn the volume wwn
     */
    public void setVolumeWwn(String volumeWwn) {
        this.volumeWwn = volumeWwn;
    }

    /**
     * 获得 size.
     *
     * @return the size
     */
    public Long getSize() {
        return size;
    }

    /**
     * 设定 size.
     *
     * @param size the size
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * 获得 status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设定 status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}