package com.h3c.idcloud.core.pojo.dto.system;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Administrator on 2016/3/10.
 */
public class AlarmDateResponse {
    /**
     * JSON格式示例：
     * {"alarmLevel": "01",      //告警类型
     *	"alarmLevelName": " 主机",  //告警类型名称 如：主机、交换机
     *	"alarmTotal": {}
     *	}
     */

    @JsonProperty("alarmLevel")
    private String alarmLevel;

    @JsonProperty("alarmLevelName")
    private String alarmLevelName;

    @JsonProperty("alarmTotal")
    private String alarmTotal;

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmLevelName() {
        return alarmLevelName;
    }

    public void setAlarmLevelName(String alarmLevelName) {
        this.alarmLevelName = alarmLevelName;
    }

    public String getAlarmTotal() {
        return alarmTotal;
    }

    public void setAlarmTotal(String alarmTotal) {
        this.alarmTotal = alarmTotal;
    }
}
