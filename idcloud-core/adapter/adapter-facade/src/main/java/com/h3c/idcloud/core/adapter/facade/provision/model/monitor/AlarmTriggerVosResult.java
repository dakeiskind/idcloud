package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.alarm.result.vo.AlarmTriggerVo;

import java.util.List;

public class AlarmTriggerVosResult extends CommonAdapterResult {
    private List<AlarmTriggerVo> alarmTriggerVos;

    public List<AlarmTriggerVo> getAlarmTriggerVos() {
        return alarmTriggerVos;
    }

    public void setAlarmTriggerVos(List<AlarmTriggerVo> alarmTriggerVos) {
        this.alarmTriggerVos = alarmTriggerVos;
    }
}
