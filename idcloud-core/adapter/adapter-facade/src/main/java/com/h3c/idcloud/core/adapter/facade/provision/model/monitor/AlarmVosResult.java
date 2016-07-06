package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.alarm.result.vo.AlarmVo;

import java.util.List;

public class AlarmVosResult extends CommonAdapterResult {
    private List<AlarmVo> alarmVos;

    public List<AlarmVo> getAlarmVos() {
        return alarmVos;
    }

    public void setAlarmVos(List<AlarmVo> alarmVos) {
        this.alarmVos = alarmVos;
    }
}
