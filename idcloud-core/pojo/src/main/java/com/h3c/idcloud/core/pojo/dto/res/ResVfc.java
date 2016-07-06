package com.h3c.idcloud.core.pojo.dto.res;


import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataDiskVo;

import java.io.Serializable;

public class ResVfc implements Serializable {
    private static final long serialVersionUID = 1L;

    private String resFcPortSid;

    /**
     * 资源磁盘ID
     */
    private String resVdSid;

    private String wwpn;

    private String status;

    private Integer clientSlotNumber;

    private String resViosId;

    private Integer viosSlotNumber;

    private String fcName;

    public ResVfc() {
    }

    /**
     * MQ磁盘对象转换为平台磁盘对象
     *
     * @param dataDiskVo
     */
    public ResVfc(DataDiskVo dataDiskVo) {
        this.clientSlotNumber = Integer.parseInt(dataDiskVo.getSlotNumber());
        this.fcName = dataDiskVo.getFcName();
        this.viosSlotNumber = Integer.parseInt(dataDiskVo.getViosSlotNumber());
        this.resViosId = dataDiskVo.getViosId();
        this.wwpn = dataDiskVo.getVfcWWPNs();
    }

    public String getResFcPortSid() {
        return resFcPortSid;
    }

    public void setResFcPortSid(String resFcPortSid) {
        this.resFcPortSid = resFcPortSid;
    }

    /**
     * @return 资源磁盘ID
     */
    public String getResVdSid() {
        return resVdSid;
    }

    /**
     * @param resVdSid 资源磁盘ID
     */
    public void setResVdSid(String resVdSid) {
        this.resVdSid = resVdSid;
    }

    public String getWwpn() {
        return wwpn;
    }

    public void setWwpn(String wwpn) {
        this.wwpn = wwpn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getClientSlotNumber() {
        return clientSlotNumber;
    }

    public void setClientSlotNumber(Integer clientSlotNumber) {
        this.clientSlotNumber = clientSlotNumber;
    }

    public String getResViosId() {
        return resViosId;
    }

    public void setResViosId(String resViosId) {
        this.resViosId = resViosId;
    }

    public Integer getViosSlotNumber() {
        return viosSlotNumber;
    }

    public void setViosSlotNumber(Integer viosSlotNumber) {
        this.viosSlotNumber = viosSlotNumber;
    }

    public String getFcName() {
        return fcName;
    }

    public void setFcName(String fcName) {
        this.fcName = fcName;
    }
}