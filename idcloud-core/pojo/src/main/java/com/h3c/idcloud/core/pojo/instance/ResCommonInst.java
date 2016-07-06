package com.h3c.idcloud.core.pojo.instance;

import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;

/**
 * 资源层通用参数类
 * @apiNote
 * <ul>
 *     <li>{@link ServiceBaseInput}</li>
 *     <li>resSpecParam :   资源层指定参数(JSON)</li>
 * </ul>
 * @see com.h3c.idcloud.core.pojo.common.ServiceBaseInput
 * @author Chaohong.Mao
 */
public class ResCommonInst extends ServiceBaseInput {
    /** 资源层指定参数 JSON*/
    private String resSpecParam;
    /** 服务实例ID JSON*/
    private String instId;

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getResSpecParam() {
        return resSpecParam;
    }

    public void setResSpecParam(String resSpecParam) {
        this.resSpecParam = resSpecParam;
    }
}
