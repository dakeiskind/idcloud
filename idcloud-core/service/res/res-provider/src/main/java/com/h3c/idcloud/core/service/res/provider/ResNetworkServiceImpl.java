package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.network.ExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.NetCreate;
import com.h3c.idcloud.core.adapter.pojo.network.NetDelete;
import com.h3c.idcloud.core.adapter.pojo.network.Router;
import com.h3c.idcloud.core.persist.res.dao.ResExtNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcRouterMapper;
import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.res.ResExtNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResIp;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVmNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResVpc;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.service.res.api.ResNetworkService;
import com.h3c.idcloud.core.service.res.api.base.ResBaseService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.Nets;
import com.h3c.idcloud.infrastructure.common.util.IPUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Res network service 类.
 *
 * @author Chaohong.Mao
 */
@Service(version = "1.0.0")
@Component
public class ResNetworkServiceImpl implements ResNetworkService {
    /**
     * 静态变量 logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ResNetworkServiceImpl.class);
    @Autowired
    private ResNetworkMapper resNetworkMapper;
    @Autowired
    private ResIpMapper resIpMapper;
    @Autowired
    private ResTopologyMapper resTopologyMapper;
    @Autowired
    private ResBaseService resBaseService;
    @Autowired
    private ResVpcMapper resVpcMapper;
    @Autowired
    private ResVpcRouterMapper resVpcRouterMapper;
    @Autowired
    private ResExtNetworkMapper resExtNetworkMapper;
    @Autowired
    private ResVmNetworkMapper resVmNetworkMapper;


    /**
     * 删除子网
     *
     * @param resCommonInst the res common inst
     * @return
     *
     * @implSpec JSON
     * {
     * "zone" : "",
     * "subNetSids" : ["1", "2"]
     * }
     */
    @Override
    @Transactional
    public int deleteSubNets(ResCommonInst resCommonInst) {
        logger.info("删除子网 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);

        Set<String> subNetIds = Sets.newHashSet();
        if (jsonNode.get("subNetSids").isArray()) {
            jsonNode.get("subNetSids").forEach(subNetSid -> {
                String sid = subNetSid.getTextValue();
                ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(sid);
                List<ResVmNetwork> resVmNetworks = this.resVmNetworkMapper.selectByNetSid(sid);
                if (resVmNetworks != null && resVmNetworks.size() > 0) {
                    throw new RpcException(RpcException.BIZ_EXCEPTION,
                                           String.format("子网 %s 下有云主机，无法删除，请确认后重试。", resNetwork.getNetworkName())
                    );
                }
                subNetIds.add(resNetwork.getUuid());
                resNetwork.setStatus(WebConstants.NETWORK_STATUS.REMOVING);
                WebUtil.prepareUpdateParams(resNetwork, resCommonInst.getUserAccount());
                this.resNetworkMapper.updateByPrimaryKeySelective(resNetwork);
            });
        }

        NetDelete netDelete = new NetDelete();
        netDelete.setRegion(this.resBaseService.getRegionFromZone(zone));
        ResVe resVe = this.resBaseService.getVeFromZone(zone);
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, netDelete);
        netDelete.setSubnetIds(subNetIds);
        try {
            MQHelper.sendMessage(netDelete);
        } catch (MQException e) {
            logger.error("删除子网 | 发送MQ失败: {}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "删除子网失败。");
        }
        return 1;
    }

    /**
     * 删除网络
     *
     * @param resCommonInst the res common inst
     * @implSpec JSON
     * {
     *      "zone" : "",
     *      "networkSid" : ""
     * }
     */
    @Override
    @Transactional
    public int deleteNetwork(ResCommonInst resCommonInst) {
        logger.info("删除网络 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        String networkSid = jsonNode.get("networkSid").getTextValue();
        ResVpc resVpc = this.resVpcMapper.selectByPrimaryKey(networkSid);
        List<ResVmNetwork> resVmNetworks = this.resVmNetworkMapper.selectByVpc(networkSid);
        if (resVmNetworks != null && resVmNetworks.size() > 0) {
            throw new RpcException(RpcException.BIZ_EXCEPTION,
                                   String.format("网络 %s 下有云主机，无法删除，请确认后重试。", resVpc.getVpcName())
            );
        }
        resVpc.setStatus(WebConstants.NETWORK_STATUS.REMOVING);
        WebUtil.prepareUpdateParams(resVpc, resCommonInst.getUserAccount());
        this.resVpcMapper.updateByPrimaryKeySelective(resVpc);

        NetDelete netDelete = new NetDelete();
        netDelete.setRegion(this.resBaseService.getRegionFromZone(zone));
        ResVe resVe = this.resBaseService.getVeFromZone(zone);
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, netDelete);
        netDelete.setNetId(resVpc.getUuid());
        try {
            MQHelper.sendMessage(netDelete);
        } catch (MQException e) {
            logger.error("删除网络 | 发送MQ失败: {}", Throwables.getStackTraceAsString(e));
            throw new RpcException(RpcException.BIZ_EXCEPTION, "删除网络失败。");
        }
        return 1;
    }

    /**
     * 查询私有网络下面的子网
     *
     * @param example
     * @return
     */
    @Override
    public List<ResNetwork> selectSubnetInVpc(Criteria example) {
        return this.resNetworkMapper.selectSubnetInVpc(example);
    }

    /**
     * 查询网络
     *
     * @param example
     * @return the res network
     */
    @Override
    public List<ResNetwork> selectByParams(Criteria example) {
        return this.resNetworkMapper.selectByParams(example);
    }

    @Override
    public ResNetwork selectByPrimaryKey(String resNetworkSid) {
        return null;
    }

    public int deleteByPrimaryKey(String resNetworkSid) {
        Criteria example = new Criteria();
        example.put("resNetworkSidUsingDelete", resNetworkSid);
        this.resIpMapper.deleteByParams(example);
        return this.resNetworkMapper.deleteByPrimaryKey(resNetworkSid);
    }

    /**
     * 用主键更新网络资源.
     *
     * @param network
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(ResNetwork network) {
        return this.resNetworkMapper.updateByPrimaryKeySelective(network);
    }

    public int insertSelective(ResNetwork record) {

        try {
            if (1 == this.resNetworkMapper.insertSelective(record)) {
                // 插入IP
                this.createNetworkResourceIp(record);
                return 1;
            } else {
                throw new RpcException(RpcException.BIZ_EXCEPTION, "子表ResourceNetwork插入异常，可能原因：数据已存在，或者数据存在多条。");
            }
        } catch (RpcException appExt) {
            appExt.printStackTrace();
            throw appExt;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


    /**
     * 创建网络IP资源
     *
     * @param network the network
     * @return 结果 int
     */
    private int createNetworkResourceIp(ResNetwork network) {
        int result = 0;

        List<ResIp> list = new ArrayList<ResIp>();

        Nets net = IPUtil.getNets(network.getSubnet(), network.getSubnetMask());

        String ipAddressStrat = net.getStartIP();
        String ipAddressEnd = net.getEndIP();

        String[] ipStartArgs = ipAddressStrat.split("\\.");
        String[] ipEndArgs = ipAddressEnd.split("\\.");
        if (ipStartArgs[0].equals(ipEndArgs[0]) && ipStartArgs[1].equals(ipEndArgs[1]) &&
                ipStartArgs[2].equals(ipEndArgs[2])) {
            for (int i = Integer.parseInt(ipStartArgs[3]); i <= Integer.parseInt(ipEndArgs[3]); i++) {
                ResIp ip = new ResIp();
                ip.setIpAddress(ipStartArgs[0] + "." + ipStartArgs[1] + "." + ipStartArgs[2] + "." + i);
                ip.setIpType(network.getIpType());
                ip.setResNetworkSid(network.getResNetworkSid());
                // ip.setMapPublicIp(resourceIp.getMapPublicIp());
                list.add(ip);
            }
        } else if (ipStartArgs[0].equals(ipEndArgs[0]) && ipStartArgs[1].equals(ipEndArgs[1]) &&
                !ipStartArgs[2].equals(ipEndArgs[2])) {
            for (int i = Integer.parseInt(ipStartArgs[2]); i <= Integer.parseInt(ipEndArgs[2]); i++) {
                if (i == Integer.parseInt(ipStartArgs[2])) {
                    for (int j = Integer.parseInt(ipStartArgs[3]); j < 256; j++) {
                        ResIp ip = new ResIp();
                        ip.setIpAddress(ipStartArgs[0] + "." + ipStartArgs[1] + "." + String.valueOf(i) + "." + j);
                        ip.setIpType(network.getIpType());
                        ip.setResNetworkSid(network.getResNetworkSid());
                        // ip.setMapPublicIp(resourceIp.getMapPublicIp());
                        list.add(ip);
                    }
                } else if (i == Integer.parseInt(ipEndArgs[2])) {
                    for (int j = 1; j <= Integer.parseInt(ipEndArgs[3]); j++) {
                        ResIp ip = new ResIp();
                        ip.setIpAddress(ipStartArgs[0] + "." + ipStartArgs[1] + "." + String.valueOf(i) + "." + j);
                        ip.setIpType(network.getIpType());
                        ip.setResNetworkSid(network.getResNetworkSid());
                        // ip.setMapPublicIp(resourceIp.getMapPublicIp());
                        list.add(ip);
                    }
                } else {
                    for (int j = 1; j < 255; j++) {
                        ResIp ip = new ResIp();
                        ip.setIpAddress(ipStartArgs[0] + "." + ipStartArgs[1] + "." + String.valueOf(i) + "." + j);
                        ip.setIpType(network.getIpType());
                        ip.setResNetworkSid(network.getResNetworkSid());
                        // ip.setMapPublicIp(resourceIp.getMapPublicIp());

                        list.add(ip);
                    }
                }
            }
        }

        // 添加ip
        if (!StringUtil.isNullOrEmpty(list)) {
            for (ResIp resIp : list) {
                // 排除网关
                if (resIp.getIpAddress().equals(network.getGateway())) {
                    continue;
                }
                // 排除保留IP段1
                if (!(StringUtil.isNullOrEmpty(network.getIpRetainStart1()) ||
                        StringUtil.isNullOrEmpty(network.getIpRetainEnd1()))) {
                    List<String> lst1 = computeIpSegment(network.getIpRetainStart1(), network.getIpRetainEnd1());
                    if (isIpInSegment(lst1, resIp.getIpAddress())) {
                        continue;
                    }
                }

                // 排除保留IP段2
                if (!(StringUtil.isNullOrEmpty(network.getIpRetainStart2()) ||
                        StringUtil.isNullOrEmpty(network.getIpRetainEnd2()))) {
                    List<String> lst2 = computeIpSegment(network.getIpRetainStart2(), network.getIpRetainEnd2());
                    if (isIpInSegment(lst2, resIp.getIpAddress())) {
                        continue;
                    }
                }
                // 排除保留IP段3
                if (!(StringUtil.isNullOrEmpty(network.getIpRetainStart3()) ||
                        StringUtil.isNullOrEmpty(network.getIpRetainEnd3()))) {
                    List<String> lst3 = computeIpSegment(network.getIpRetainStart3(), network.getIpRetainEnd3());
                    if (isIpInSegment(lst3, resIp.getIpAddress())) {
                        continue;
                    }
                }

                WebUtil.prepareInsertParams(resIp);
                // 插入IP资源
                this.resIpMapper.insertSelective(resIp);
            }
            result = 1;
        }

        return result;
    }


    /**
     * 计算2个ip之间的所有的ip
     *
     * @param startIp the start ip
     * @param endIp   the end ip
     * @return 结果 list
     */
    public List<String> computeIpSegment(String startIp, String endIp) {
        String[] ipStartArgs = startIp.split("\\.");
        String[] ipEndArgs = endIp.split("\\.");
        List<String> list = new ArrayList<String>();

        if (ipStartArgs[0].equals(ipEndArgs[0]) && ipStartArgs[1].equals(ipEndArgs[1]) &&
                ipStartArgs[2].equals(ipEndArgs[2])) {
            for (int i = Integer.parseInt(ipStartArgs[3]); i <= Integer.parseInt(ipEndArgs[3]); i++) {
                list.add(ipStartArgs[0] + "." + ipStartArgs[1] + "." + ipStartArgs[2] + "." + i);
            }
        } else if (ipStartArgs[0].equals(ipEndArgs[0]) && ipStartArgs[1].equals(ipEndArgs[1]) &&
                !ipStartArgs[2].equals(ipEndArgs[2])) {
            for (int i = Integer.parseInt(ipStartArgs[2]); i <= Integer.parseInt(ipEndArgs[2]); i++) {
                if (i == Integer.parseInt(ipStartArgs[2])) {
                    for (int j = Integer.parseInt(ipStartArgs[3]); j < 256; j++) {
                        list.add(ipStartArgs[0] + "." + ipStartArgs[1] + "." + String.valueOf(i) + "." + j);
                    }
                } else if (i == Integer.parseInt(ipEndArgs[2])) {
                    for (int j = 1; j <= Integer.parseInt(ipEndArgs[3]); j++) {
                        list.add(ipStartArgs[0] + "." + ipStartArgs[1] + "." + String.valueOf(i) + "." + j);
                    }
                } else {
                    for (int j = 1; j < 255; j++) {
                        list.add(ipStartArgs[0] + "." + ipStartArgs[1] + "." + String.valueOf(i) + "." + j);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 判断ip是否在列表中
     *
     * @param lst the lst
     * @param ip  the ip
     * @return boolean
     */
    public boolean isIpInSegment(List<String> lst, String ip) {
        for (int i = 0; i < lst.size(); i++) {
            if (ip.equals(lst.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResNetwork statisticalTopologyOfNetwork(String resTopologySid) {
        return this.resNetworkMapper.statisticalTopologyOfNetwork(resTopologySid);
    }

    @Override
    public List<ResNetwork> selectByBizParams(Criteria example) {
        return this.resNetworkMapper.selectByBizParams(example);
    }

    @Override
    public List<ResNetwork> selectByBizParamsOfNetWork(Criteria example) {
        return this.resNetworkMapper.selectByBizParamsOfNetWork(example);
    }

    @Override
    public List<ResNetwork> selectByBizParamsOfEveryNetWork(Criteria example) {
        return this.resNetworkMapper.selectByBizParamsOfEveryNetWork(example);
    }

    @Override
    public List<ResNetwork> selectByBizParamsOfEveryNetWorkType(Criteria example) {
        return this.resNetworkMapper.selectByBizParamsOfEveryNetWorkType(example);
    }

    @Override
    public List<ResNetwork> selectByBizParamsOfEverySubNetWorkType(Criteria example) {
        return this.resNetworkMapper.selectByBizParamsOfEverySubNetWorkType(example);
    }

    @Override
    public List<ResNetwork> selectByBizParamsOfCountNetWorkType(Criteria example) {
        return this.resNetworkMapper.selectByBizParamsOfCountNetWorkType(example);
    }

    @Override
    public ResNetwork statisticalBizOfNetwork(Criteria example) {
        return this.resNetworkMapper.statisticalBizOfNetwork(example);
    }

    @Override
    public List<ResNetwork> selectByBizParamsOfNetWorkType(Criteria example) {
        return this.resNetworkMapper.selectByBizParamsOfNetWorkType(example);
    }

    @Override
    public List<ResNetwork> selectCustomNetworkByMgtObjSid(Criteria example) {
        return this.resNetworkMapper.selectCustomNetworkByMgtObjSid(example);
    }

    /**
     * @implSpec {
     * zone:""
     * vpcName:"",
     * vpcDescription:"",
     * vpcCidr:"",
     * subNet:{
     * subNetName:"",
     * subNetDescription:"",
     * subNetCidr:"".
     * }
     * }
     */
    @Override
    @Transactional
    public String createNetworkWithSub(ResCommonInst resCommonInst) {
        logger.info("创建网络及子网 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        resCommonInst.setZoneId(jsonNode.get("zone").getTextValue());
        // 新建网络
        ResVpc resVpc = new ResVpc();
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
        resVpc.setMgtObjSid(resCommonInst.getMgtObjSid());
        resVpc.setParentTopologySid(resVe.getResTopologySid());
        List<ResTopology> resTopologyList = this.resTopologyMapper.selectTopologyByType(new Criteria().put(
                "resTopologyType",
                WebConstants.RES_TOPOLOGY_TYPE.PNI
        ).put("zoneId", resCommonInst.getZoneId()));
        resVpc.setZone(resCommonInst.getZoneId());
        resVpc.setResPoolSid(resTopologyList.get(0).getResTopologySid());
        resVpc.setVpcName(jsonNode.get("vpcName").getTextValue());
        resVpc.setCidr(jsonNode.get("vpcCidr").getTextValue());
        resVpc.setDescription(jsonNode.get("vpcDescription").getTextValue());
        // 插入新增网络状态为：创建中
        resVpc.setStatus(WebConstants.NETWORK_STATUS.CREATING);
        WebUtil.prepareInsertParams(resVpc, resCommonInst.getUserAccount());
        this.resVpcMapper.insertSelective(resVpc);

        // 新建子网
        ResNetwork resNetwork = new ResNetwork();
        JsonNode networkJson = jsonNode.get("subNet");
        resNetwork.setZone(resCommonInst.getZoneId());
        resNetwork.setNetworkName(networkJson.get("subNetName").getTextValue());
        String cidr = networkJson.get("subNetCidr").getTextValue();
        resNetwork.setParentTopologySid(resVpc.getResVpcSid());
        resNetwork.setNetworkType(WebConstants.ResNetworkType.PRIVATE);
        resNetwork.setIpType(WebConstants.IpType.IPV4);
        // "10.10.100.0/24" => ["10.10.100.0", "24"]
        String[] temp = cidr.split("/");
        resNetwork.setSubnet(temp[0]);
        // 24 => "255.255.255.0"
        resNetwork.setSubnetMask(IPUtil.getMask(Integer.parseInt(temp[1])));
        // 插入新增网络状态为：创建中
        resNetwork.setStatus(WebConstants.NETWORK_STATUS.CREATING);
        resNetwork.setDescription(networkJson.get("subNetDescription").getTextValue());
        WebUtil.prepareInsertParams(resNetwork, resCommonInst.getUserAccount());
        // 插入resNetwork表
        if (1 == this.resNetworkMapper.insertSelective(resNetwork)) {
            // 插入网络的关系表，生成IP地址
            this.createNetworkResourceIp(resNetwork);

            // 调用openstack创建网络
            NetCreate netCreate = new NetCreate();
            // 管理基本信息
            this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, netCreate);

            String region = this.resBaseService.getRegionFromZone(resCommonInst.getZoneId());
            netCreate.setRegion(region);
            netCreate.setName(resNetwork.getNetworkName());
            netCreate.setCidr(cidr);
            netCreate.setResId(resNetwork.getResNetworkSid());
            Map<String, Object> options = Maps.newHashMap();
            options.put("ownerId", resCommonInst.getUserAccount());
            options.put("mgtObjSid", resCommonInst.getMgtObjSid());
            netCreate.setOptions(options);
            List resVpcRouters = this.resVpcRouterMapper.selectByParams(new Criteria("mgtObjSid",
                                                                                     resCommonInst.getMgtObjSid()
            ));
            // 同一个租户用一个公网的路由，如果没有，则创建
            if (resVpcRouters == null || resVpcRouters.size() == 0) {
                // 查询外部资源池
                Criteria param = new Criteria();
                param.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.PNE)
                     .put("zoneId", resCommonInst.getZoneId());
                List<ResTopology> resTopologys = this.resTopologyMapper.selectTopologyByType(param);
                // 查询外部网络资源
                param.clear();
                param.put("parentTopologySid", resVe.getResTopologySid())
                     .put("resPoolSid", resTopologys.get(0).getResTopologySid());
                List<ResExtNetwork> resExtNetworks = this.resExtNetworkMapper.selectByParams(param);
                netCreate.setRouter(Router.builder()
                                          .name("Public-Router")
                                          .adminStateUp(true)
                                          .externalGateway(ExternalGateway.builder()
                                                                          .networkId(resExtNetworks.get(0).getUuid())
                                                                          .enableSnat(true)
                                                                          .build())
                                          .build());
            }
            try {
                logger.info("创建网络 | MQ参数：" + JsonUtil.toJson(netCreate));
                MQHelper.sendMessage(netCreate);
            } catch (MQException e) {
                logger.error("创建网络 | 发送MQ失败");
                throw new RpcException(RpcException.BIZ_EXCEPTION, e.getCause());
            }
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "子表ResNetwork插入异常，可能原因：数据已存在，或者数据存在多条。");
        }
        return resNetwork.getResNetworkSid();
    }


    /**
     * @implSpec {
     * zone:""
     * subNet:{
     * vpcSid:"",
     * subNetName:"",
     * subNetDescription:"",
     * subNetCidr:"".
     * }
     * }
     */
    @Override
    @Transactional
    public String createSubnet(ResCommonInst resCommonInst) {
        logger.info("创建子网 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        resCommonInst.setZoneId(jsonNode.get("zone").getTextValue());
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());

        // 新建子网
        ResNetwork resNetwork = new ResNetwork();
        JsonNode networkJson = jsonNode.get("subNet");
        resNetwork.setNetworkName(networkJson.get("subNetName").getTextValue());
        String cidr = networkJson.get("subNetCidr").getTextValue();
        resNetwork.setParentTopologySid(networkJson.get("vpcSid").getTextValue());
        resNetwork.setNetworkType(WebConstants.ResNetworkType.PRIVATE);
        resNetwork.setIpType(WebConstants.IpType.IPV4);
        // "10.10.100.0/24" => ["10.10.100.0", "24"]
        String[] temp = cidr.split("/");
        resNetwork.setSubnet(temp[0]);
        // 24 => "255.255.255.0"
        resNetwork.setSubnetMask(IPUtil.getMask(Integer.parseInt(temp[1])));
        // 插入新增网络状态为：创建中
        resNetwork.setStatus(WebConstants.NETWORK_STATUS.CREATING);
        resNetwork.setDescription(networkJson.get("subNetDescription").getTextValue());
        WebUtil.prepareInsertParams(resNetwork, resCommonInst.getUserAccount());
        // 插入resNetwork表
        if (1 == this.resNetworkMapper.insertSelective(resNetwork)) {
            // 插入网络的关系表，生成IP地址
            this.createNetworkResourceIp(resNetwork);

            // 调用openstack创建网络
            NetCreate netCreate = new NetCreate();
            // 管理基本信息
            this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, netCreate);

            String region = this.resBaseService.getRegionFromZone(resCommonInst.getZoneId());
            netCreate.setRegion(region);
            netCreate.setName(resNetwork.getNetworkName());
            netCreate.setCidr(cidr);
            netCreate.setResId(resNetwork.getResNetworkSid());
            Map<String, Object> options = Maps.newHashMap();
            options.put("ownerId", resCommonInst.getUserAccount());
            options.put("mgtObjSid", resCommonInst.getMgtObjSid());
            netCreate.setOptions(options);
            try {
                logger.info("创建子网 | MQ参数：" + JsonUtil.toJson(netCreate));
                MQHelper.sendMessage(netCreate);
            } catch (MQException e) {
                logger.error("创建子网 | 发送MQ失败");
                throw new RpcException(RpcException.BIZ_EXCEPTION, e.getCause());
            }
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "子表ResNetwork插入异常，可能原因：数据已存在，或者数据存在多条。");
        }
        return resNetwork.getResNetworkSid();
    }

    @Override
    public List<ResVpc> findNetworkByTenant(ServiceBaseInput baseInput) {
        ResVe resVe = this.resBaseService.getVeFromZone(baseInput.getZoneId());
        List<ResVpc> resVpcList = new ArrayList<>();
        if (resVe != null) {
            Criteria criteria = new Criteria().put("parentTopologySid", resVe.getResTopologySid())
                                              .put("mgtObjSid", baseInput.getMgtObjSid());

            resVpcList = this.resVpcMapper.selectByParams(criteria);
        }
        return resVpcList;
    }

    @Override
    public Map<String, String> findSubnetByNetwork(String resVpcSid) {
        List<ResNetwork> resNetworks = this.resNetworkMapper.selectByVpc(resVpcSid);
        if (CollectionUtils.isEmpty(resNetworks)) {
            return Maps.newHashMap();
        }
        return resNetworks.stream().collect(Collectors.toMap(ResNetwork::getResNetworkSid, resNet -> {
            StringBuilder builder = new StringBuilder().append(resNet.getSubnet())
                                                       .append("/")
                                                       .append(IPUtil.getStandardMask(resNet.getSubnetMask()));
            if (!Strings.isNullOrEmpty(resNet.getNetworkName())) {
                builder.append(" (").append(resNet.getNetworkName()).append(")");
            }
            return builder.toString();
        }));
    }
}