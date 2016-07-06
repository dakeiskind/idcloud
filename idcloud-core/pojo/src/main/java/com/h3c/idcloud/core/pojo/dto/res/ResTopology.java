package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResTopology implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源拓扑结构ID
     */
    private String resTopologySid;

    /**
     * 资源拓扑结构名称
     */
    private String resTopologyName;
    
    /**
     * 资源拓扑结构名称like
     */
    private String resTopologyNameLike;

    /**
     * 资源拓扑结构类型
     */
    private String resTopologyType;

    /**
     * 查询拓扑结构的子集
     */
    private String findChildBySid;
    
    /**
     * 资源拓扑结构类型名称
     */
    private String resTopologyTypeName;

    /**
     * 上级资源拓扑结构ID
     */
    private String parentTopologySid;
    
    /**
     * 上级资源拓扑结构名称
     */
    private String parentTopologyName;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序号
     */
    private Integer sortNo;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedDt;

    /**
     * 版本号
     */
    private Long version;
    
    /**
     * 资源选项卡的类型
     */
    private String resTabsType;
    
    /**
     * 资源中tree显示的值
     */
    private String topologyValue;
    
    /**
     * 拓扑结构的icon
     */
    private String topologyIcon;
    
    /**
     * 资源池sid
     */
    private String resPoolSid;
    
    /**
     * 集群sids
     */
    private String resClusterSids;
    
    /**
     * 存储sids
     */
    private String resStorageSids;
    
    /**
     * vlan池个数
     */
    private String vlanCount;
    /**
     * 网络池下的内部网络池个数
     */
    private String pniCount;
    /**
     * 网络池下的外部网络池个数
     */
    private String pneCount;
    
    private String topologyTypes;

    /**
     * 资源分区关联资源环境SID
     */
    private String resEnvId;

    /**
     * 资源分区关联资源环境类型
     */
    private String resEnvType;

    /**
     * 资源分区关联资源环境类型
     */
    private String regionName;
    
    /**
     * @return 资源拓扑结构ID
     */
    public String getResTopologySid() {
        return resTopologySid;
    }

    /**
     * @param resTopologySid 
	 *            资源拓扑结构ID
     */
    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    /**
     * @return 资源拓扑结构名称
     */
    public String getResTopologyName() {
        return resTopologyName;
    }

    /**
     * @param resTopologyName 
	 *            资源拓扑结构名称
     */
    public void setResTopologyName(String resTopologyName) {
        this.resTopologyName = resTopologyName;
    }

    /**
     * @return 资源拓扑结构类型
     */
    public String getResTopologyType() {
        return resTopologyType;
    }

    /**
     * @param resTopologyType 
	 *            资源拓扑结构类型
     */
    public void setResTopologyType(String resTopologyType) {
        this.resTopologyType = resTopologyType;
    }

    /**
     * @return 上级资源拓扑结构ID
     */
    public String getParentTopologySid() {
        return parentTopologySid;
    }

    /**
     * @param parentTopologySid 
	 *            上级资源拓扑结构ID
     */
    public void setParentTopologySid(String parentTopologySid) {
        this.parentTopologySid = parentTopologySid;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 排序号
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * @param sortNo 
	 *            排序号
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 
	 *            创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * @param createdDt 
	 *            创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * @return 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy 
	 *            更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt 
	 *            更新时间
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * @return 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version 
	 *            版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

	public String getTopologyValue() {
		return topologyValue;
	}

	public void setTopologyValue(String topologyValue) {
		this.topologyValue = topologyValue;
	}

	public String getTopologyIcon() {
		return topologyIcon;
	}

	public void setTopologyIcon(String topologyIcon) {
		this.topologyIcon = topologyIcon;
	}

	public String getResTopologyTypeName() {
		return resTopologyTypeName;
	}

	public void setResTopologyTypeName(String resTopologyTypeName) {
		this.resTopologyTypeName = resTopologyTypeName;
	}

	public String getParentTopologyName() {
		return parentTopologyName;
	}

	public void setParentTopologyName(String parentTopologyName) {
		this.parentTopologyName = parentTopologyName;
	}

	public String getResTabsType() {
		return resTabsType;
	}

	public void setResTabsType(String resTabsType) {
		this.resTabsType = resTabsType;
	}

	public String getResTopologyNameLike() {
		return resTopologyNameLike;
	}

	public void setResTopologyNameLike(String resTopologyNameLike) {
		this.resTopologyNameLike = resTopologyNameLike;
	}

	public String getResPoolSid() {
		return resPoolSid;
	}

	public void setResPoolSid(String resPoolSid) {
		this.resPoolSid = resPoolSid;
	}

	public String getResClusterSids() {
		return resClusterSids;
	}

	public void setResClusterSids(String resClusterSids) {
		this.resClusterSids = resClusterSids;
	}

	public String getResStorageSids() {
		return resStorageSids;
	}

	public void setResStorageSids(String resStorageSids) {
		this.resStorageSids = resStorageSids;
	}

	public String getVlanCount() {
		return vlanCount;
	}

	public void setVlanCount(String vlanCount) {
		this.vlanCount = vlanCount;
	}

	public String getPniCount() {
		return pniCount;
	}

	public void setPniCount(String pniCount) {
		this.pniCount = pniCount;
	}

	public String getPneCount() {
		return pneCount;
	}

	public void setPneCount(String pneCount) {
		this.pneCount = pneCount;
	}

	public String getTopologyTypes() {
		return topologyTypes;
	}

	public void setTopologyTypes(String topologyTypes) {
		this.topologyTypes = topologyTypes;
	}

    public String getFindChildBySid() {
        return findChildBySid;
    }

    public void setFindChildBySid(String findChildBySid) {
        this.findChildBySid = findChildBySid;
    }

    public String getResEnvId() {
        return resEnvId;
    }

    public void setResEnvId(String resEnvId) {
        this.resEnvId = resEnvId;
    }

    public String getResEnvType() {
        return resEnvType;
    }

    public void setResEnvType(String resEnvType) {
        this.resEnvType = resEnvType;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}