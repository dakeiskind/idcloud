package com.h3c.idcloud.core.adapter.pojo.network.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class SgRuleDeleteResult extends BaseResult {

    private String sgRuleId;

    public String getSgRuleId() {
        return sgRuleId;
    }

    public void setSgRuleId(String sgRuleId) {
        this.sgRuleId = sgRuleId;
    }

}
