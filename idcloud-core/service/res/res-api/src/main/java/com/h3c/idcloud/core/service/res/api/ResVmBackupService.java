package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.dto.res.ResVmBackup;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 Res vm backup service.
 */
public interface ResVmBackupService {

    /**
     *
     * @param example
     * @return
     */
    List<ResVmBackup> selectByParams(Criteria example);

    /**
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ResVmBackup record);

    /**
     * 创建虚拟机快照
     *
     * @param vmBackup the vm backup
     * @param mgtObj   the mgt obj
     *
     * @return res inst result
     */
    ResInstResult createSnapShot(ResVmBackup vmBackup, MgtObj mgtObj);

    /**
     * 删除虚拟机快照
     *
     * @param resVmBackupSid the res vm backup sid
     * @param mgtObj         the mgt obj
     *
     * @return res inst result
     */
    ResInstResult deleteVmSnapShot(long resVmBackupSid, MgtObj mgtObj);

    /**
     * 从快照恢复虚拟机
     *
     * @param backupSid the backup sid
     * @param mgtObj    the mgt obj
     *
     * @return res inst result
     */
    ResInstResult revertVmBySnapshot(long backupSid, MgtObj mgtObj);
}