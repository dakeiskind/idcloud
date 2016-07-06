package com.h3c.idcloud.infrastructure.common.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有需要被拦截Action TraceService 必须继承的基础obj
 * Created by swq on 2/12/2016.
 */
public class BaseServiceObj implements Serializable {

    /**
     * 操作的详细内容
     */
    private String actionDetail;

    private Map<String,Object> businessDataMap = new HashMap<>();

    public String getActionDetail() {
        return actionDetail;
    }

    public void setActionDetail(String actionDetail) {
        this.actionDetail = actionDetail;
    }

    public Map<String, Object> getBusinessDataMap() {
        return businessDataMap;
    }

    public void setBusinessDataMap(Map<String, Object> businessDataMap) {
        this.businessDataMap = businessDataMap;
    }
}
