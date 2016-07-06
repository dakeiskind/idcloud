package com.h3c.idcloud.core.service.res.provider;


import com.h3c.idcloud.core.persist.res.dao.ResPoolVlanVsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVlanMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsPortGroupMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResPoolVlanVs;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVlan;
import com.h3c.idcloud.core.pojo.dto.res.ResVs;
import com.h3c.idcloud.core.pojo.dto.res.ResVsPortGroup;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.core.service.res.api.ResVsService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResVsServiceImpl implements ResVsService {
    private static final Logger logger = LoggerFactory.getLogger(ResVsServiceImpl.class);
    @Autowired
    private ResVsMapper resVsMapper;
    @Autowired
    private ResVeService resVeService;
    @Autowired
    private ResPoolVlanVsMapper resPoolVlanVsMapper;
    @Autowired
    private ResVsPortGroupMapper resVsPortGroupMapper;
    @Autowired
    private ResVlanMapper resVlanMapper;

    /**
     * 单个交换机同步
     */
    @Override
    public boolean findAllByVs(String resPoolSid) {

        boolean result = false;

        try {
            Criteria vlanVs = new Criteria();
            vlanVs.put("resPoolSid", resPoolSid);
            List<ResPoolVlanVs> vsList = this.resPoolVlanVsMapper.selectByParams(vlanVs);
            // 组合参数
            String resVsSids = "";
            for (ResPoolVlanVs aVsList : vsList) {
                resVsSids += "'" + aVsList.getResVsSid() + "',";
            }
            resVsSids = resVsSids.substring(0, resVsSids.length() - 1);

            // 查询交换机所属虚拟化环境
            Criteria criteria = new Criteria();
            criteria.put("resVsSidSet", resVsSids);
            List<ResVs> resVeList = this.resVsMapper.selectResVeByResVsSet(criteria);

            boolean flag = false;
            // 遍历虚拟化环境下所有网络，比对是否存在
            for (ResVs resVs : resVeList) {
                ResVe resVe = new ResVe();
                resVe.setResTopologySid(resVs.getResTopologySid());
                resVe.setManagementUrl(resVs.getManagementUrl());
                resVe.setManagementAccount(resVs.getManagementAccount());
                resVe.setManagementPassword(resVs.getManagementPassword());

                boolean result1 = this.resVeService.getNetWorkByVe(resVe);
                if (!result1) {
                    logger.error(resVe.getResTopologyName() + "同步失败");
                    result = false;
                    flag = false;
                    break;
                }
                flag = true;
            }
            if (flag) {
                boolean updateVlan = this.synchronousVlanRelationDvs(resPoolSid);
                if (!updateVlan) {
                    logger.error("同步Vlan失败");
                    result = false;
                }
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 同步Vlan
     */
    public boolean synchronousVlanRelationDvs(String resPoolSid) {
        int result = 0;

        // 1、检查一遍VLAN池关联的交换机，是否还存在
        Criteria vlanVs = new Criteria();
        vlanVs.put("resPoolSid", resPoolSid);
        List<ResPoolVlanVs> vsList = this.resPoolVlanVsMapper.selectByParams(vlanVs);

        // 2、存在，查看端口组更新；不存在，删除关联关系和VLAN表中的数据
        if (vsList != null && vsList.size() > 0) {
            for (ResPoolVlanVs vs : vsList) {
                // 判断该交换机是否还存在
                boolean isExist = decideVsIsExist(vs.getResVsSid());
                if (isExist) {
                    // 存在，跟新交换机下面的端口组
                    Criteria portGroup = new Criteria();
                    portGroup.put("resVsSid", vs.getResVsSid());
                    List<ResVsPortGroup> group = this.resVsPortGroupMapper.selectByParams(portGroup);

                    if (group != null && group.size() > 0) {
                        // 更新res_vlan表
                        for (ResVsPortGroup port : group) {
                            // vlanId为空或者0，把原来vlan中对应的vlanId删掉
                            if (StringUtil.isNullOrEmpty(port.getVlanId()) || "0".equals(port.getVlanId())) {
                                // 删除vlan
                                Criteria vlanInsert = new Criteria();
                                vlanInsert.put("resVsSidDelete", vs.getResVsSid());
                                vlanInsert.put("uuidDelete", port.getUuid());
                                vlanInsert.put("resPoolSidDelete", resPoolSid);
                                this.resVlanMapper.deleteByParams(vlanInsert);
                                continue;
                            }
                            // 原来res_vlan表中存在该VLAN，应更新该VLAN
                            Criteria vlanUpdate = new Criteria();
                            vlanUpdate.put("vlanId", port.getVlanId());
                            List<ResVlan> list = this.resVlanMapper.selectByParams(vlanUpdate);
                            if (list != null && list.size() > 0) {
                                list.get(0).setVlanId(port.getVlanId());
                                list.get(0).setResVsSid(vs.getResVsSid());
                                list.get(0).setResPoolSid(resPoolSid);
                                list.get(0).setTag(port.getName());
                                list.get(0).setUuid(port.getUuid());
                                result = this.resVlanMapper.updateByPrimaryKeySelective(list.get(0));
                                if (1 != result) {
                                    String returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
                                                                                       WebUtil.getMessage("同步失败！")
                                    ));
                                    return false;
                                }
                                continue;
                            }
                            // VLAN不为空，且在VLAN表中不存在，应插入新的VLAN
                            ResVlan res = new ResVlan();
                            res.setVlanId(port.getVlanId());
                            res.setResVsSid(vs.getResVsSid());
                            res.setResPoolSid(resPoolSid);
                            res.setTag(port.getName());
                            res.setUuid(port.getUuid());
                            result = this.resVlanMapper.insertSelective(res);
                            if (1 != result) {
                                String returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
                                                                                   WebUtil.getMessage("同步失败！")
                                ));
                                return false;
                            }
                        }

                    } else {
                        // 删除vlan
                        Criteria vlanDelete = new Criteria();
                        vlanDelete.put("resVsSidDelete", vs.getResVsSid());
                        this.resVlanMapper.deleteByParams(vlanDelete);
                    }
                } else {
                    // 不存在，删除关联表，删除VLAN
                    // 删除VLAN
                    Criteria vlanDelete = new Criteria();
                    vlanDelete.put("resVsSidDelete", vs.getResVsSid());
                    this.resVlanMapper.deleteByParams(vlanDelete);

                    // 删除关系表
                    ResPoolVlanVs Vs = new ResPoolVlanVs();
                    Vs.setResPoolSid(resPoolSid);
                    Vs.setResVsSid(vs.getResVsSid());
                    this.resPoolVlanVsMapper.deleteByPrimaryKey(Vs);
                }
            }
        }

        String returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage("同步成功！")));
        return true;
    }

    // 判断VLAN关联的交换机，是否还在
    public boolean decideVsIsExist(String resVsSid) {
        boolean isExist = false;
        ResVs vs = this.resVsMapper.selectByPrimaryKey(resVsSid);
        if (vs != null) {
            isExist = true;
        }
        return isExist;
    }

}