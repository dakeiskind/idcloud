package com.h3c.idcloud.core.adapter.pojo.network;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class SgRuleDelete extends Base {

    private String sgId;
    private String sgRuleId;

    public String getSgId() {
        return sgId;
    }

    public void setSgId(String sgId) {
        this.sgId = sgId;
    }

    public String getSgRuleId() {
        return sgRuleId;
    }

    public void setSgRuleId(String sgRuleId) {
        this.sgRuleId = sgRuleId;
    }

}
