package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyConfigMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVcMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.system.dao.CodeMapper;
import com.h3c.idcloud.core.persist.task.dao.TaskMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResTopologyConfig;
import com.h3c.idcloud.core.pojo.dto.res.ResVc;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.system.Code;
import com.h3c.idcloud.core.service.res.api.ResTopologyService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ResTopologyServiceImpl implements ResTopologyService {
    private static final Logger logger = LoggerFactory.getLogger(ResTopologyServiceImpl.class);
    @Autowired
    private ResTopologyMapper resTopologyMapper;
    @Autowired
    private ResTopologyConfigMapper resTopologyConfigMapper;
    @Autowired
    private ResVeMapper resVeMapper;
    @Autowired
    private ResVcMapper resVcMapper;
    @Autowired
    private ResHostMapper resHostMapper;
    @Autowired
    private ResStorageMapper resStorageMapper;
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public int deleteByPrimaryKey(String resTopologySid) {
        ResTopology topology = this.resTopologyMapper.selectByPrimaryKey(resTopologySid);
        // 查询是否存在子集
        Criteria example = new Criteria();
        example.put("findChildBySid", resTopologySid);
        List<ResTopology> list = this.resTopologyMapper.selectByParams(example);
        if (list.size() > 0) {
            return 0;
        } else {
            // 是VE
            if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(topology.getResTopologyType())) {

                ResVe resVe = this.resVeMapper.selectByPrimaryKey(resTopologySid);
                // 删除task
                this.taskMapper.deleteByPrimaryKey(resVe.getTaskId());
                // 删除vE表
                this.resVeMapper.deleteByPrimaryKey(resTopologySid);
                // 是VC
            } else if (WebConstants.RES_TOPOLOGY_TYPE.DC.equals(topology.getResTopologyType())) {
                this.resVcMapper.deleteByPrimaryKey(resTopologySid);
            }

            return this.resTopologyMapper.deleteByPrimaryKey(resTopologySid);
        }

    }


    /**
     * 获取所有的资源分区拓扑
     *
     * @return 资源分区拓扑
     */
    @Override
    public List<ResTopology> getAllResZoneTopology() {
        return this.getResZoneTopology(new Criteria());
    }

    /**
     * 获取资源分区拓扑
     *
     * @return 资源分区拓扑
     */
    @Override
    public List<ResTopology> getResZoneTopology(Criteria param) {
        return this.resTopologyMapper.selectResZoneTopologyByParams(param);
    }

    /**
     * 根据资源分区及条件获取其下子资源拓扑
     *
     * @param param
     *
     * @return 资源分区拓扑
     */
    public List<ResTopology> getResZoneChildTopology(Criteria param) {
        return this.resTopologyMapper.selectResZoneTopologyChildByParams(param);
    }

    /**
     * 新增资源分区
     *
     * @param record 资源拓扑结构
     *
     * @return 00:失败，01：成功
     */
    @Override
    public String insertRzSelective(ResTopology record) {
        // 先在code表中查询出资源分区下面所有的字典数据
        Criteria example = new Criteria();
        example.put("getChildCode", record.getResTopologyType());
        example.put("enabled", "1");
        List<Code> codeList = this.codeMapper.selectByParams(example);
        // 插入资源分区,获得resTopologySid
        int rzResult = this.resTopologyMapper.insertSelective(record);

        //插入RZ关联资源环境数据
        Criteria rzConfig = new Criteria();
        rzConfig.put("enabled", WebConstants.CodeEnable.ABLE);
        rzConfig.put("codeCategory", "RES_TOPOLOGY_ZONE_CONFIG");
        List<Code> configList = this.codeMapper.selectByParams(rzConfig);
        if (configList.size() > 0 || configList != null) {
            for (int i = 0; i < configList.size(); i++) {
                ResTopologyConfig config = new ResTopologyConfig();
                config.setConfigKey(configList.get(i).getCodeValue());
                config.setConfigName(configList.get(i).getCodeDisplay());

                if (configList.get(i).getCodeValue().equals("res_env_type")) {
                    config.setConfigValue(record.getResEnvType());
                } else if (configList.get(i).getCodeValue().equals("res_env_id")) {
                    config.setConfigValue(record.getResEnvId());
                } else if (configList.get(i).getCodeValue().equals("region_name")) {
                    config.setConfigValue(record.getRegionName());
                }
                config.setResTopologySid(record.getResTopologySid());
                this.resTopologyConfigMapper.insertSelective(config);
            }
        }


        // 根据查询出的字典数据，插入Topology表
        if (codeList != null && codeList.size() > 0) {
            // 资源分区有子集
            for (Code code : codeList) {
                // 第一级,code码的parentValue与resTopologyType相同
                if (code.getParentCodeValue().equals(record.getResTopologyType())) {
                    ResTopology topology = new ResTopology();
                    topology.setResTopologyName(code.getCodeDisplay());
                    topology.setResTopologyType(code.getCodeValue());
                    topology.setSortNo(code.getSort());
                    topology.setParentTopologySid(record.getResTopologySid());
                    // 插入数据库
                    int result = this.resTopologyMapper.insertSelective(topology);

                    // 判断是否是计算资源池
                    if (WebConstants.RES_TOPOLOGY_TYPE.PCVP.equals(code.getCodeValue()) ||
                            WebConstants.RES_TOPOLOGY_TYPE.PCP.equals(code.getCodeValue()) ||
                            WebConstants.RES_TOPOLOGY_TYPE.PCVX.equals(code.getCodeValue()) ||
                            WebConstants.RES_TOPOLOGY_TYPE.PCX.equals(code.getCodeValue())) {
                        // 插入拓扑配置表
                        Criteria criteria = new Criteria();
                        criteria.put("enabled", WebConstants.CodeEnable.ABLE);
                        criteria.put("codeCategory", "RES_TOPOLOGY_PC_CONFIG");
                        List<Code> list = this.codeMapper.selectByParams(criteria);

                        if (list != null || list.size() > 0) {
                            for (Code aList : list) {
                                ResTopologyConfig config = new ResTopologyConfig();
                                config.setConfigKey(aList.getCodeValue());
                                config.setConfigName(aList.getCodeDisplay());
                                config.setConfigValue(aList.getAttribute1());
                                config.setResTopologySid(topology.getResTopologySid());
                                this.resTopologyConfigMapper.insertSelective(config);
                            }
                        }
                    }

                    // 判断是否是存储资源池
                    if (WebConstants.RES_TOPOLOGY_TYPE.PS.equals(code.getCodeValue())) {
                        // 插入拓扑配置表
                        Criteria criteria = new Criteria();
                        criteria.put("enabled", WebConstants.CodeEnable.ABLE);
                        criteria.put("codeCategory", "RES_TOPOLOGY_PS_CONFIG");
                        List<Code> list = this.codeMapper.selectByParams(criteria);

                        if (list != null || list.size() > 0) {
                            for (Code aList : list) {
                                ResTopologyConfig config = new ResTopologyConfig();
                                config.setConfigKey(aList.getCodeValue());
                                config.setConfigName(aList.getCodeDisplay());
                                config.setConfigValue(aList.getAttribute1());
                                config.setResTopologySid(topology.getResTopologySid());
                                this.resTopologyConfigMapper.insertSelective(config);
                            }
                        }
                    }

                    if (1 == result) {
                        // 插入第二级
                        for (Code subCode : codeList) {
                            if (subCode.getParentCodeValue().equals(code.getCodeValue())) {
                                ResTopology subTop = new ResTopology();
                                subTop.setResTopologyName(subCode.getCodeDisplay());
                                subTop.setResTopologyType(subCode.getCodeValue());
                                subTop.setSortNo(subCode.getSort());
                                subTop.setParentTopologySid(topology.getResTopologySid());
                                this.resTopologyMapper.insertSelective(subTop);
                            }
                        }
                    }

                } else {
                    continue;
                }


            }
        }

        return rzResult == 1 ? "01" : "00";
    }

    @Override
    public int deleteRzByPrimaryKey(String resTopologySid) {
        // TO-DO 删除详细的资源
        int result = 0;
        // 查询该资源分区下所有的子集
        Criteria example = new Criteria();
        example.put("findChildBySid", resTopologySid);
        List<ResTopology> list = this.resTopologyMapper.selectByParams(example);
        // 删除子集下面的相应数据，包括关联信息
        for (ResTopology subRz : list) {
            if (WebConstants.RES_TOPOLOGY_TYPE.PCP.equals(subRz.getResTopologyType())) {
                // 计算资源池
                // 取消关联的集群
            }
            if (WebConstants.RES_TOPOLOGY_TYPE.PCVP.equals(subRz.getResTopologyType())) {
                // 计算资源池
                // 取消关联的集群
            }
            if (WebConstants.RES_TOPOLOGY_TYPE.PCX.equals(subRz.getResTopologyType())) {
                // 计算资源池
                // 取消关联的集群
            }
            if (WebConstants.RES_TOPOLOGY_TYPE.PCVX.equals(subRz.getResTopologyType())) {
                // X86虚拟化资源池
                // 1.取消关联的集群及集群下面的主机
                example = new Criteria();
                example.put("resPoolSid", subRz.getResTopologySid());
                List<ResVc> vcList = this.resVcMapper.selectByParams(example);
                if (vcList.size() > 0 && vcList != null) {
                    for (ResVc vc : vcList) {
                        // 查询vc下面的主机
                        example = new Criteria();
                        example.put("parentTopologySid", vc.getResTopologySid());
                        List<ResHost> hostList = this.resHostMapper.selectByParams(example);
                        if (hostList.size() > 0 && hostList != null) {
                            for (ResHost host : hostList) {
                                // 清除主机与资源池关系
                                host.setResPoolSid(null);
                                this.resHostMapper.updateByPrimaryKey(host);
                            }
                        }

                        // 清除vc与主机资源池关系
                        vc.setResPoolSid(null);
                        this.resVcMapper.updateByPrimaryKey(vc);
                    }
                }
                // 2.清除主机直接与资源池的关系
                example = new Criteria();
                example.put("resPoolSid", subRz.getResTopologySid());
                List<ResHost> hostList = this.resHostMapper.selectByParams(example);
                if (hostList != null && hostList.size() > 0) {
                    for (ResHost host : hostList) {
                        // 清除主机与资源池关系
                        host.setResPoolSid(null);
                        this.resHostMapper.updateByPrimaryKey(host);
                    }
                }
                // 3.删除资源池配置相关信息
                example = new Criteria();
                example.put("resTopologySid", subRz.getResTopologySid());
                this.resTopologyConfigMapper.deleteByParams(example);

            } else if (WebConstants.RES_TOPOLOGY_TYPE.PS.equals(subRz.getResTopologyType())) {
                // 存储资源池
                // 1.取消关联资源池的存储
                example = new Criteria();
                example.put("resPoolSid", subRz.getResTopologySid());
                List<ResStorage> stroageList = this.resStorageMapper.selectByParams(example);
                if (stroageList != null && stroageList.size() > 0) {
                    for (ResStorage storage : stroageList) {
                        // 清除主机与资源池关系
                        storage.setResPoolSid(null);
                        this.resStorageMapper.updateByPrimaryKey(storage);
                    }
                }
                // 2.删除资源池配置相关信息
                example = new Criteria();
                example.put("resTopologySid", subRz.getResTopologySid());
                this.resTopologyConfigMapper.deleteByParams(example);
            } else if (WebConstants.RES_TOPOLOGY_TYPE.PNV.equals(subRz.getResTopologyType())) {
                // vlan池
            } else if (WebConstants.RES_TOPOLOGY_TYPE.PNI.equals(subRz.getResTopologyType())) {
                // 内部网络资源池
            } else if (WebConstants.RES_TOPOLOGY_TYPE.PNE.equals(subRz.getResTopologyType())) {
                // 外部网络资源池
            }
            result += this.resTopologyMapper.deleteByPrimaryKey(subRz.getResTopologySid());
        }

        if (list.size() == result) {
            // 删除RZ配置信息
            example = new Criteria();
            example.put("resTopologySid", resTopologySid);
            this.resTopologyConfigMapper.deleteByParams(example);
            // 删除资源分区
            this.resTopologyMapper.deleteByPrimaryKey(resTopologySid);
            result = 1;
        }

        return result;
    }

    @Override
    public List<ResTopology> selectVirtualTopologyTree(Criteria criteria) {
        return this.resTopologyMapper.selectVirtualTopologyTree(criteria);
    }

    @Override
    public List<ResTopology> selectPoolTopologyTree(Criteria criteria) {
        return this.resTopologyMapper.selectPoolTopologyTree(criteria);
    }

    @Override
    public List<ResTopology> selectVirtualStorageTopologyTree(Criteria criteria) {
        return this.resTopologyMapper.selectVirtualStorageTopologyTree(criteria);
    }

    /**
     * 根据拓扑SID查询topology信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResTopology selectByPrimaryKey(String resTopologySid) {
        return this.resTopologyMapper.selectByPrimaryKey(resTopologySid);
    }

    /**
     * 根据条件查询拓扑结构图
     *
     * @param param
     *
     * @return
     */
    @Override
    public List<ResTopology> selectByParams(Criteria param) {
        return this.resTopologyMapper.selectByParams(param);
    }

    /**
     * 插入数据
     *
     * @param record
     *
     * @return
     */
    @Override
    public int insertSelective(ResTopology record) {
        return this.resTopologyMapper.insertSelective(record);
    }

}