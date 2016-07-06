package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.dto.res.ResVdBackup;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;

import java.util.List;

/**
 * 接口 Res vd backup service.
 */
public interface ResVdBackupService {

    /**
     * 创建块存储快照
     *
     * @param vdBackup the vd backup
     * @param mgtObj   the mgt obj
     *
     * @return the res inst result
     */
    ResInstResult createSnapShot(ResVdBackup vdBackup, MgtObj mgtObj);

    /**
     * 创建备份
     *
     * @param vdBackup the vd backup
     * @param mgtObj   the mgt obj
     *
     * @return the res inst result
     */
    ResInstResult createBackup(ResVdBackup vdBackup, MgtObj mgtObj);

    /**
     * 获取虚机下快照集合
     *
     * @param resId the res id
     *
     * @return the snap shot list
     */
    public List<ResVdBackup> getSnapShotList(String resId);

    /**
     * 获取虚机下的备份
     *
     * @param resId the res id
     *
     * @return the backup list
     */
    public List<ResVdBackup> getBackupList(String resId);

    /**
     * 从快照恢复块存储
     *
     * @param vdBackup the vd backup
     * @param mgtObj   the mgt obj
     *
     * @return the res inst result
     */
    ResInstResult revertVdBySnapshot(ResVdBackup vdBackup, MgtObj mgtObj);

    /**
     * 从备份恢复块存储
     *
     * @param vdBackup the vd backup
     * @param mgtObj   the mgt obj
     *
     * @return the res inst result
     */
    ResInstResult revertVdByBackup(ResVdBackup vdBackup, MgtObj mgtObj);

    /**
     * 删除快照
     *
     * @param vdBackup the vd backup
     * @param mgtObj   the mgt obj
     *
     * @return the res inst result
     */
    ResInstResult deleteVdBySnapShot(ResVdBackup vdBackup, MgtObj mgtObj);

    /**
     * Delete vd res inst result.
     *
     * @param resVdSid  the res vd sid
     * @param mgtObjSid the mgt obj sid
     *
     * @return the res inst result
     */
    ResInstResult deleteVd(String resVdSid, long mgtObjSid);
}