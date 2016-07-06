package com.h3c.idcloud.core.adapter.pojo.alarm.result;


import com.h3c.idcloud.core.adapter.pojo.alarm.result.vo.AlarmTriggerVo;
import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

import java.util.List;

public class AlarmTriggerResult extends BaseResult {
    private List<AlarmTriggerVo> alarmTriggerVos;

    public List<AlarmTriggerVo> getAlarmTriggerVos() {
        return alarmTriggerVos;
    }

    public void setAlarmTriggerVos(List<AlarmTriggerVo> alarmTriggerVos) {
        this.alarmTriggerVos = alarmTriggerVos;
    }
}
