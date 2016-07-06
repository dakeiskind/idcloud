package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.TemplateVO;

import java.util.ArrayList;
import java.util.List;

public class TemplateScanResult extends BaseResult {

    private String resVeSid;
    private List<TemplateVO> templates = new ArrayList<TemplateVO>();

    public String getResVeSid() {
        return resVeSid;
    }

    public void setResVeSid(String resVeSid) {
        this.resVeSid = resVeSid;
    }

    public List<TemplateVO> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateVO> templates) {
        this.templates = templates;
    }

}
