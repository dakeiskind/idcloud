package com.h3c.idcloud.core.adapter.pojo.alarm.result;


import com.h3c.idcloud.core.adapter.pojo.alarm.result.vo.AlarmVo;
import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

import java.util.List;

public class AlarmListResult extends BaseResult {
    private List<AlarmVo> alarmList;

    public List<AlarmVo> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<AlarmVo> alarmList) {
        this.alarmList = alarmList;
    }
}
