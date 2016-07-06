package com.h3c.idcloud.core.service.charge.api;



import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPlan;
import com.h3c.idcloud.core.pojo.vo.charge.BillingPlanSpecVo;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BillingPlanService {
    int countByParams(Criteria example);

    BillingPlan selectByPrimaryKey(Long billingPlanSid);

    List<BillingPlan> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long billingPlanSid);

    int updateByPrimaryKeySelective(BillingPlan record);

    int updateByPrimaryKey(BillingPlan record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(BillingPlan record, Criteria example);

    int updateByParams(BillingPlan record, Criteria example);

    int insert(BillingPlan record);

    int insertSelective(BillingPlan record);
    
    /**
     */
    BillingPlan selectBillingByServiceSidAndStatus(Long serviceSid);

    /**
     * 获取服务配置价格，如果数据验证失败or规格项为被配置则会抛出RpcException 业务异常
     * @param serviceSid  服务sid
     * @param billingType 资费类型
     * @param billingSpecConfig   资费配置为jsonmap key一定是参与计费的规格项
     *                            {
                                    "region": "10",
                                    "zone": "12e0c825-3ff5-11e5-8c09-005056ba3c46",
                                    "instance": [{
                                          "instanceCategory": "idc-S",
                                          "cpu": "1",
                                          "memory": "1"
                                          }],
                                    "systemDisk": [{
                                         "systemDiskCategory": "cloud_efficiency",
                                         "systemDiskSize": "40"
                                        }],
                                    "dataDisk": [{
                                         "dataDiskCategory": "cloud_ssd",
                                         "dataDiskSize": "100"
                                         },{
                                        "dataDiskCategory": "cloud_efficiency",
                                        "dataDiskSize": "150"
                                        }]
                                  }
     * @return 最终计算的价格
     */
    BigDecimal getBillingPrice(Long serviceSid,String billingType,String billingSpecConfig);

    /**
     * 查询与资费计划关联的服务规格配置数据
     * @param serviceSid
     * @param billingPlanSid
     * @return
     */
    List<BillingPlanSpecVo> selectBillingPlanSpecVos(Long serviceSid,Long billingPlanSid);


    /**
     * 查询获取当前服务的配置项
     * @param serviceSid
     * @return
     */
    List<BillingPlanSpecVo> selectBillingPlanSpecVos(Long serviceSid);


    /**
     * 根据服务代码获取对应的服务SID
     * @param serviceCode
     * @return
     */
    Long getServiceSid(String serviceCode);


    /**
     * 过滤页面传入原始规格数据并最终返回需与计费相关的规格配置数据
     * @param serviceSid  服务sid，对应的云服务
     * @param billingType  计费类型
     * @param specifications  页面传入的原始规格配置包含了一些非必要的计费规格
     * @return 规格配置map数据
     */
    Map<String,Object> getBillingPriceConfig(Long serviceSid, String billingType, Map<String,Object> specifications);


    /**
     * 获取价格，参数为前台提交的规格项配置map
     * @param models
     * @return
     */
    BigDecimal getPriceForWebCenter(ArrayList<Map<String, Object>> models);

    /**
     * 添加billingplan
     * @param billingPlan
     * @return
     */
    boolean addBillingPlan(BillingPlan billingPlan,AuthUser authUserInfo);

}