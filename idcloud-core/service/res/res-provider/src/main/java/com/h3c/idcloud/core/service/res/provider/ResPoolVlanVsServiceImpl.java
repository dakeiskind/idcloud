package com.h3c.idcloud.core.service.res.provider;


import com.h3c.idcloud.core.persist.res.dao.ResPoolVlanVsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVlanMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsPortGroupMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResPoolVlanVs;
import com.h3c.idcloud.core.pojo.dto.res.ResVlan;
import com.h3c.idcloud.core.pojo.dto.res.ResVsPortGroup;
import com.h3c.idcloud.core.service.res.api.ResPoolVlanVsService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResPoolVlanVsServiceImpl implements ResPoolVlanVsService {
    private static final Logger logger = LoggerFactory.getLogger(ResPoolVlanVsServiceImpl.class);
    @Autowired
    private ResPoolVlanVsMapper resPoolVlanVsMapper;
    @Autowired
    private ResVlanMapper resVlanMapper;
    @Autowired
    private ResVsPortGroupMapper resVsPortGroupMapper;

    @Override
    public int insertOrUpdateRelationShipDvsWithVlanPool(String resPoolSid, String vsSids) {
        // 查询出所有的vsSid
        Criteria vlanVs = new Criteria();
        vlanVs.put("resPoolSid", resPoolSid);
        List<ResPoolVlanVs> poolVlanVsList = this.resPoolVlanVsMapper.selectByParams(vlanVs);

        try {
            if (vsSids.equals("null")) {
                // 直接删除vlan_vs关联表
                if (poolVlanVsList != null && poolVlanVsList.size() > 0) {

                    for (ResPoolVlanVs vs : poolVlanVsList) {
                        // 删除掉交换机下的vlan
                        Criteria vlan = new Criteria();
                        vlan.put("resVsSidDelete", vs.getResVsSid());
                        vlan.put("resPoolSidDelete", vs.getResPoolSid());
                        this.resVlanMapper.deleteByParams(vlan);

                        this.resPoolVlanVsMapper.deleteByPrimaryKey(vs);
                    }
                }

            } else {
                // 原来关联表有值，进行对比
                if (poolVlanVsList != null && poolVlanVsList.size() > 0) {
                    String[] sids = vsSids.split(",");

                    // 更新、插入，vs关联表
                    for (String sid : sids) {
                        boolean isExist = false;
                        for (ResPoolVlanVs vs : poolVlanVsList) {
                            if (vs.getResVsSid().equals(sid)) {
                                isExist = true;
                            }
                        }
                        // 查询出交换机对象
                        Criteria portGroup = new Criteria();
                        portGroup.put("resVsSid", sid);
                        List<ResVsPortGroup> group = this.resVsPortGroupMapper.selectByParams(portGroup);
                        if (isExist) {
                            // 交换机存在端口
                            if (group != null && group.size() > 0) {
                                // 更新res_vlan表
                                for (ResVsPortGroup port : group) {
                                    if (StringUtil.isNullOrEmpty(port.getVlanId()) || "0".equals(port.getVlanId())) {
                                        // 删除
                                        Criteria vlanInsert = new Criteria();
                                        vlanInsert.put("resVsSidDelete", sid);
                                        vlanInsert.put("uuidDelete", port.getUuid());
                                        vlanInsert.put("resPoolSidDelete", resPoolSid);
                                        this.resVlanMapper.deleteByParams(vlanInsert);
                                    } else {
                                        // 修改
                                        Criteria vlanInsert = new Criteria();
                                        vlanInsert.put("resVsSid", sid);
                                        vlanInsert.put("uuid", port.getUuid());
                                        List<ResVlan> list = this.resVlanMapper.selectByParams(vlanInsert);
                                        if (list != null && list.size() > 0) {
                                            list.get(0).setVlanId(port.getVlanId());
                                            list.get(0).setTag(port.getName());
                                            this.resVlanMapper.updateByPrimaryKeySelective(list.get(0));
                                        }
                                    }
                                }

                            } else {
                                // 删除vlan
                                Criteria vlanDelete = new Criteria();
                                vlanDelete.put("resVsSidDelete", sid);
                                vlanDelete.put("resPoolSidDelete", resPoolSid);
                                this.resVlanMapper.deleteByParams(vlanDelete);
                            }

                        } else {
                            // 插入关系表
                            ResPoolVlanVs vs = new ResPoolVlanVs();
                            vs.setResPoolSid(resPoolSid);
                            vs.setResVsSid(sid);
                            this.resPoolVlanVsMapper.insertSelective(vs);
                            // 插入res_vlan表
                            if (group != null && group.size() > 0) {
                                for (ResVsPortGroup port : group) {
                                    if (StringUtil.isNullOrEmpty(port.getVlanId()) || "0".equals(port.getVlanId())) {
                                        continue;
                                    }
                                    // 原来res_vlan表中存在该VLAN
                                    Criteria vlanUpdate = new Criteria();
                                    vlanUpdate.put("vlanId", port.getVlanId());
                                    vlanUpdate.put("resPoolSid", resPoolSid);
                                    List<ResVlan> list = this.resVlanMapper.selectByParams(vlanUpdate);
                                    if (list != null && list.size() > 0) {
                                        list.get(0).setVlanId(port.getVlanId());
                                        list.get(0).setResVsSid(sid);
                                        list.get(0).setResPoolSid(resPoolSid);
                                        list.get(0).setTag(port.getName());
                                        list.get(0).setUuid(port.getUuid());
                                        this.resVlanMapper.updateByPrimaryKeySelective(list.get(0));
                                        continue;
                                    }
                                    // 原来res_vlan表中不存在该VLAN
                                    ResVlan res = new ResVlan();
                                    res.setVlanId(port.getVlanId());
                                    res.setResVsSid(sid);
                                    res.setResPoolSid(resPoolSid);
                                    res.setTag(port.getName());
                                    res.setUuid(port.getUuid());
                                    this.resVlanMapper.insertSelective(res);
                                }
                            }
                        }
                    }

                    // 删除，vs关联表
                    for (ResPoolVlanVs vs : poolVlanVsList) {
                        boolean isExist = false;
                        for (String sid : sids) {
                            if (vs.getResVsSid().equals(sid)) {
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            // 删除,vlan
                            Criteria vlan = new Criteria();
                            vlan.put("resVsSidDelete", vs.getResVsSid());
                            vlan.put("resPoolSidDelete", vs.getResPoolSid());
                            this.resVlanMapper.deleteByParams(vlan);
                            // 删除,关联表
                            this.resPoolVlanVsMapper.deleteByPrimaryKey(vs);
                        }
                    }

                } else {
                    // 原来关联表不存在数据，直接插入
                    String[] sids = vsSids.split(",");

                    for (String sid : sids) {
                        // 插入关系表
                        ResPoolVlanVs vs = new ResPoolVlanVs();
                        vs.setResPoolSid(resPoolSid);
                        vs.setResVsSid(sid);
                        this.resPoolVlanVsMapper.insertSelective(vs);

                        // vlan表
                        Criteria portGroup = new Criteria();
                        portGroup.put("resVsSid", sid);
                        List<ResVsPortGroup> group = this.resVsPortGroupMapper.selectByParams(portGroup);

                        if (group != null && group.size() > 0) {
                            for (ResVsPortGroup port : group) {
                                if (StringUtil.isNullOrEmpty(port.getVlanId()) || "0".equals(port.getVlanId())) {
                                    continue;
                                }
                                // 原来res_vlan表中存在该VLAN
                                Criteria vlanUpdate = new Criteria();
                                vlanUpdate.put("vlanId", port.getVlanId());
                                vlanUpdate.put("resPoolSid", resPoolSid);
                                List<ResVlan> list = this.resVlanMapper.selectByParams(vlanUpdate);
                                if (list != null && list.size() > 0) {
                                    list.get(0).setVlanId(port.getVlanId());
                                    list.get(0).setResVsSid(sid);
                                    list.get(0).setResPoolSid(resPoolSid);
                                    list.get(0).setTag(port.getName());
                                    list.get(0).setUuid(port.getUuid());
                                    this.resVlanMapper.updateByPrimaryKeySelective(list.get(0));
                                    continue;
                                }
                                // 原来res_vlan表中不存在该VLAN
                                ResVlan res = new ResVlan();
                                res.setVlanId(port.getVlanId());
                                res.setResVsSid(sid);
                                res.setResPoolSid(resPoolSid);
                                res.setTag(port.getName());
                                res.setUuid(port.getUuid());
                                this.resVlanMapper.insertSelective(res);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
    }

}