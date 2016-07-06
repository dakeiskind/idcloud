package com.h3c.idcloud.core.pojo.dto.res;

import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IOSlotVo;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IoVo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResHostItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 1111
     */
    private String hostItemId;

    private String hostItemTypeCode;

    private String hostItemTypeCodeName;

    private String supHostItemId;

    private String owner;

    /**
     * 资源ID
     */
    private String resHostSid;

    /**
     * 资源实例ID
     */
    private String relateResSid;

    private String hostItemDesc;

    private String hostItemIndex;

    private String hostItemAddr;

    private String itemLocation;

    private Integer resAllocFlag;

    private Integer resAllocStatus;

    private BigDecimal partAmount;

    private BigDecimal subPartAmount;

    private String uuid;

    private String macAddress;

    private String wwpn;

    private Integer diskNum;

    public ResHostItem() {

    }

    public ResHostItem(IoVo ioVo) {
        this.uuid = ioVo.getUuid();
        this.hostItemAddr = ioVo.getUnitPhysloc();
        this.hostItemDesc = ioVo.getDescription();
        this.hostItemIndex = ioVo.getDrcIndex();
        this.hostItemTypeCode = ioVo.getIoType();
        this.itemLocation = ioVo.getDrcName();
        if (ioVo.getLparId() != null && !"".equals(ioVo.getLparId())) {
            this.resAllocStatus = WebConstants.HostItemAllocStatus.OCCUPY;
            this.resAllocFlag = Integer.parseInt(WebConstants.ResHostItemAllocFlag.OCCUPIED);
        } else {
            this.resAllocStatus = WebConstants.HostItemAllocStatus.FREE;
            this.resAllocFlag = Integer.parseInt(WebConstants.ResHostItemAllocFlag.NOT_OCCUPIED);
        }
    }

    public ResHostItem(IOSlotVo ioSlot) {
        this.uuid = ioSlot.getParentPhysLoc() + "#" + ioSlot.getPhysLoc();
        this.hostItemDesc = ioSlot.getDescripton();
        this.itemLocation = ioSlot.getPhysLoc();
        this.hostItemTypeCode = ioSlot.getSlotChildren();
        this.macAddress = "null".equalsIgnoreCase(ioSlot.getMacAddress()) ? null : ioSlot.getMacAddress();
        this.wwpn = "null".equalsIgnoreCase(ioSlot.getWwpn()) ? null : ioSlot.getWwpn();
        if (ioSlot.getLparId() != null && !"".equals(ioSlot.getLparId())) {
            this.resAllocStatus = WebConstants.HostItemAllocStatus.OCCUPY;
            this.resAllocFlag = Integer.parseInt(WebConstants.ResHostItemAllocFlag.OCCUPIED);
        } else {
            this.resAllocStatus = WebConstants.HostItemAllocStatus.FREE;
            this.resAllocFlag = Integer.parseInt(WebConstants.ResHostItemAllocFlag.NOT_OCCUPIED);
        }
    }

    public Integer getDiskNum() {
        return diskNum;
    }

    public void setDiskNum(Integer diskNum) {
        this.diskNum = diskNum;
    }

    /**
     * @return 1111
     */
    public String getHostItemId() {
        return hostItemId;
    }

    /**
     * @param hostItemId 1111
     */
    public void setHostItemId(String hostItemId) {
        this.hostItemId = hostItemId;
    }

    public String getHostItemTypeCode() {
        return hostItemTypeCode;
    }

    public void setHostItemTypeCode(String hostItemTypeCode) {
        this.hostItemTypeCode = hostItemTypeCode;
    }

    public String getSupHostItemId() {
        return supHostItemId;
    }

    public void setSupHostItemId(String supHostItemId) {
        this.supHostItemId = supHostItemId;
    }

    /**
     * @return 资源ID
     */
    public String getResHostSid() {
        return resHostSid;
    }

    /**
     * @param resHostSid 资源ID
     */
    public void setResHostSid(String resHostSid) {
        this.resHostSid = resHostSid;
    }

    /**
     * @return 资源实例ID
     */
    public String getRelateResSid() {
        return relateResSid;
    }

    /**
     * @param relateResSid 资源实例ID
     */
    public void setRelateResSid(String relateResSid) {
        this.relateResSid = relateResSid;
    }

    public String getHostItemDesc() {
        return hostItemDesc;
    }

    public void setHostItemDesc(String hostItemDesc) {
        this.hostItemDesc = hostItemDesc;
    }

    public String getHostItemIndex() {
        return hostItemIndex;
    }

    public void setHostItemIndex(String hostItemIndex) {
        this.hostItemIndex = hostItemIndex;
    }

    public String getHostItemAddr() {
        return hostItemAddr;
    }

    public void setHostItemAddr(String hostItemAddr) {
        this.hostItemAddr = hostItemAddr;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public Integer getResAllocFlag() {
        return resAllocFlag;
    }

    public void setResAllocFlag(Integer resAllocFlag) {
        this.resAllocFlag = resAllocFlag;
    }

    public Integer getResAllocStatus() {
        return resAllocStatus;
    }

    public void setResAllocStatus(Integer resAllocStatus) {
        this.resAllocStatus = resAllocStatus;
    }

    public BigDecimal getPartAmount() {
        return partAmount;
    }

    public void setPartAmount(BigDecimal partAmount) {
        this.partAmount = partAmount;
    }

    public BigDecimal getSubPartAmount() {
        return subPartAmount;
    }

    public void setSubPartAmount(BigDecimal subPartAmount) {
        this.subPartAmount = subPartAmount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getWwpn() {
        return wwpn;
    }

    public void setWwpn(String wwpn) {
        this.wwpn = wwpn;
    }

    /**
     * @return the hostItemTypeCodeName
     */
    public String getHostItemTypeCodeName() {
        return hostItemTypeCodeName;
    }

    /**
     * @param hostItemTypeCodeName the hostItemTypeCodeName to set
     */
    public void setHostItemTypeCodeName(String hostItemTypeCodeName) {
        this.hostItemTypeCodeName = hostItemTypeCodeName;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
}