package com.h3c.idcloud.core.pojo.dto.res;


import com.google.common.base.Strings;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.SysDiskVo;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VdVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Res vd 类.
 *
 * @author Chaohong.Mao
 */
public class ResVd implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 资源磁盘ID
     */
    private String resVdSid;

    /**
     * 磁盘名称
     */
    private String vdName;

    /**
     * 占用存储大小
     */
    private Long allocateDiskSize;

    /**
     * 扩容前大小
     */
    private Long boforeExpandSize;

    /**
     * 实际使用存储大小
     */
    private Long useDiskSize;

    /**
     * 分配存储资源ID
     */
    private String allocateResStorageSid;

    /**
     * 关联主机实例SID
     */
    private String resVmSid;

    /**
     * 块存储用途
     */
    private String storagePurpose;

    /**
     * 块存储用途
     */
    private String storagePurposeName;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 磁盘模式
     */
    private String diskMode;

    /**
     * UUID
     */
    private String uuid;

    /**
     * 区域ID
     */
    private String zone;

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 磁盘路径
     */
    private String path;

    /**
     * 挂载点
     */
    private String mountPoint;

    /**
     * 文件系统类型
     */
    private String fileSystemType;

    /**
     * 逻辑卷名称
     */
    private String logicVolume;

    /**
     * 状态
     */
    private String status;

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
     * 操作（方便服务层知道磁盘的操作）
     */
    private String operate;

    /***
     * 所属存储名字
     */
    private String dataStoreName;

    /**
     * 释放方式
     */
    private String releaseMode;

    /***
     * 服务层本地盘Sid
     */
    private String localHostHbaSid;

    private Long mgtObjSid;

    /**
     * The Res vfc.
     */
    private ResVfc resVfc;

    /**
     * 构造 Res vd 的实例.
     */
    public ResVd() {

    }

    /**
     * MQ磁盘对象转换为平台磁盘对象
     *
     * @param vdVo the vd vo
     */
    public ResVd(VdVO vdVo) {
        this.vdName = vdVo.getVdName();
        if (!Strings.isNullOrEmpty(vdVo.getAllocateDiskSize())) {
            this.allocateDiskSize = Long.parseLong(vdVo.getAllocateDiskSize());
        }
        if (!Strings.isNullOrEmpty(vdVo.getUseDiskSize())) {
            this.useDiskSize = Long.parseLong(vdVo.getUseDiskSize());
        }
        this.storagePurpose = vdVo.getStoragePurpose();
        this.diskMode = vdVo.getDiskMode();
        this.uuid = vdVo.getUuid();
        this.path = vdVo.getPath();
        this.dataStoreName = vdVo.getDataStoreName();
    }

    /**
     * MQ磁盘对象转换为平台磁盘对象
     *
     * @param sysDiskVo the sys disk vo
     */
    public ResVd(SysDiskVo sysDiskVo) {
        this.uuid = sysDiskVo.getUuid();
        this.vdName = sysDiskVo.getDiskName();
        this.allocateDiskSize = new BigDecimal(sysDiskVo.getSize()).longValue();
        this.storagePurpose = WebConstants.StoragePurpose.SYSTEM_DISK;
    }

    /**
     * 获得 res vfc.
     *
     * @return the res vfc
     */
    public ResVfc getResVfc() {
        return resVfc;
    }

    /**
     * 设定 res vfc.
     *
     * @param resVfc the res vfc
     */
    public void setResVfc(ResVfc resVfc) {
        this.resVfc = resVfc;
    }

    /**
     * 获得 path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * 设定 path.
     *
     * @param path the path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获得 data store name.
     *
     * @return the data store name
     */
    public String getDataStoreName() {
        return dataStoreName;
    }

    /**
     * 设定 data store name.
     *
     * @param dataStoreName the data store name
     */
    public void setDataStoreName(String dataStoreName) {
        this.dataStoreName = dataStoreName;
    }

    /**
     * 获得 res vd sid.
     *
     * @return 资源磁盘ID res vd sid
     */
    public String getResVdSid() {
        return resVdSid;
    }

    /**
     * 设定 res vd sid.
     *
     * @param resVdSid 资源磁盘ID
     */
    public void setResVdSid(String resVdSid) {
        this.resVdSid = resVdSid;
    }

    /**
     * 获得 vd name.
     *
     * @return the vd name
     */
    public String getVdName() {
        return vdName;
    }

    /**
     * 设定 vd name.
     *
     * @param vdName the vd name
     */
    public void setVdName(String vdName) {
        this.vdName = vdName;
    }

    /**
     * 获得 allocate disk size.
     *
     * @return 占用存储大小 allocate disk size
     */
    public Long getAllocateDiskSize() {
        return allocateDiskSize;
    }

    /**
     * 设定 allocate disk size.
     *
     * @param allocateDiskSize 占用存储大小
     */
    public void setAllocateDiskSize(Long allocateDiskSize) {
        this.allocateDiskSize = allocateDiskSize;
    }

    /**
     * 获得 use disk size.
     *
     * @return 实际使用存储大小 use disk size
     */
    public Long getUseDiskSize() {
        return useDiskSize;
    }

    /**
     * 设定 use disk size.
     *
     * @param useDiskSize 实际使用存储大小
     */
    public void setUseDiskSize(Long useDiskSize) {
        this.useDiskSize = useDiskSize;
    }

    /**
     * 获得 allocate res storage sid.
     *
     * @return 分配存储资源ID allocate res storage sid
     */
    public String getAllocateResStorageSid() {
        return allocateResStorageSid;
    }

    /**
     * 设定 allocate res storage sid.
     *
     * @param allocateResStorageSid 分配存储资源ID
     */
    public void setAllocateResStorageSid(String allocateResStorageSid) {
        this.allocateResStorageSid = allocateResStorageSid;
    }

    /**
     * 获得 bofore expand size.
     *
     * @return the boforeExpandSize
     */
    public Long getBoforeExpandSize() {
        return boforeExpandSize;
    }

    /**
     * 设定 bofore expand size.
     *
     * @param boforeExpandSize the boforeExpandSize to set
     */
    public void setBoforeExpandSize(Long boforeExpandSize) {
        this.boforeExpandSize = boforeExpandSize;
    }

    /**
     * 获得 res vm sid.
     *
     * @return 关联主机实例SID res vm sid
     */
    public String getResVmSid() {
        return resVmSid;
    }

    /**
     * 设定 res vm sid.
     *
     * @param resVmSid 关联主机实例SID
     */
    public void setResVmSid(String resVmSid) {
        this.resVmSid = resVmSid;
    }

    /**
     * 获得 storage purpose.
     *
     * @return 块存储用途 storage purpose
     */
    public String getStoragePurpose() {
        return storagePurpose;
    }

    /**
     * 设定 storage purpose.
     *
     * @param storagePurpose 块存储用途
     */
    public void setStoragePurpose(String storagePurpose) {
        this.storagePurpose = storagePurpose;
    }

    /**
     * 获得 device name.
     *
     * @return 设备名称 device name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设定 device name.
     *
     * @param deviceName 设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 获得 disk mode.
     *
     * @return 磁盘模式 disk mode
     */
    public String getDiskMode() {
        return diskMode;
    }

    /**
     * 设定 disk mode.
     *
     * @param diskMode 磁盘模式
     */
    public void setDiskMode(String diskMode) {
        this.diskMode = diskMode;
    }

    /**
     * 获得 uuid.
     *
     * @return UUID uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设定 uuid.
     *
     * @param uuid UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获得 status.
     *
     * @return 状态 status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设定 status.
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获得 created by.
     *
     * @return 创建人 created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设定 created by.
     *
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获得 created dt.
     *
     * @return 创建时间 created dt
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * 设定 created dt.
     *
     * @param createdDt 创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * 获得 updated by.
     *
     * @return 更新人 updated by
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设定 updated by.
     *
     * @param updatedBy 更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获得 updated dt.
     *
     * @return 更新时间 updated dt
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * 设定 updated dt.
     *
     * @param updatedDt 更新时间
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * 获得 version.
     *
     * @return 版本号 version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * 设定 version.
     *
     * @param version 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 获得 operate.
     *
     * @return the operate
     */
    public String getOperate() {
        return operate;
    }

    /**
     * 设定 operate.
     *
     * @param operate the operate to set
     */
    public void setOperate(String operate) {
        this.operate = operate;
    }

    /**
     * 获得 storage purpose name.
     *
     * @return the storage purpose name
     */
    public String getStoragePurposeName() {
        return storagePurposeName;
    }

    /**
     * 设定 storage purpose name.
     *
     * @param storagePurposeName the storage purpose name
     */
    public void setStoragePurposeName(String storagePurposeName) {
        this.storagePurposeName = storagePurposeName;
    }

    /**
     * 获得 storage path.
     *
     * @return the storage path
     */
    public String getStoragePath() {
        return storagePath;
    }

    /**
     * 设定 storage path.
     *
     * @param storagePath the storage path
     */
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    /**
     * 获得 local host hba sid.
     *
     * @return the local host hba sid
     */
    public String getLocalHostHbaSid() {
        return localHostHbaSid;
    }

    /**
     * 设定 local host hba sid.
     *
     * @param localHostHbaSid the local host hba sid
     */
    public void setLocalHostHbaSid(String localHostHbaSid) {
        this.localHostHbaSid = localHostHbaSid;
    }

    /**
     * 获得 mount point.
     *
     * @return the mountPoint
     */
    public String getMountPoint() {
        return mountPoint;
    }

    /**
     * 设定 mount point.
     *
     * @param mountPoint the mountPoint to set
     */
    public void setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
    }

    /**
     * 获得 file system type.
     *
     * @return the fileSystemType
     */
    public String getFileSystemType() {
        return fileSystemType;
    }

    /**
     * 设定 file system type.
     *
     * @param fileSystemType the fileSystemType to set
     */
    public void setFileSystemType(String fileSystemType) {
        this.fileSystemType = fileSystemType;
    }

    /**
     * 获得 logic volume.
     *
     * @return the logicVolume
     */
    public String getLogicVolume() {
        return logicVolume;
    }

    /**
     * 设定 logic volume.
     *
     * @param logicVolume the logicVolume to set
     */
    public void setLogicVolume(String logicVolume) {
        this.logicVolume = logicVolume;
    }

    public Long getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(Long mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    public String getReleaseMode() {
        return releaseMode;
    }

    public void setReleaseMode(String releaseMode) {
        this.releaseMode = releaseMode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}