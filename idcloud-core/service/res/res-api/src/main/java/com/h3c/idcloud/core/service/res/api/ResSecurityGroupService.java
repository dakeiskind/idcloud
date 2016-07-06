package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.adapter.pojo.network.result.SecurityGroupQueryResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.ServerSecurityGroupAddResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.ServerSecurityGroupDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmQuerySgsResult;
import com.h3c.idcloud.core.pojo.dto.res.ResSecurityGroup;
import com.h3c.idcloud.core.pojo.instance.ResSecurityGroupInst;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 安全组接口.
 *
 * @author Chaohong.Mao
 */
public interface ResSecurityGroupService {

    /**
     * Select by params list.
     *
     * @param example the example
     *
     * @return the list
     */
    List<ResSecurityGroup> selectByParams(Criteria example);

    /**
     * Update by primary key selective int.
     *
     * @param record the record
     *
     * @return the int
     */
    int updateByPrimaryKeySelective(ResSecurityGroup record);

    /**
     * Select security groups security group query result.
     *
     * @param mgtObjSid the mgt obj sid
     *
     * @return the security group query result
     */
    SecurityGroupQueryResult selectSecurityGroups(String mgtObjSid);

    /**
     * 创建安全组
     *
     * @param resSecurityGroupInst the res security group inst
     *
     * @return sg create result
     */
    SgCreateResult createSg(ResSecurityGroupInst resSecurityGroupInst);

    /**
     * 删除安全组
     *
     * @param resSecurityGroupInstSid the res security group inst sid
     *
     * @return sg delete result
     */
    SgDeleteResult deleteSg(String resSecurityGroupInstSid);


    /**
     * 虚拟机绑定安全组
     *
     * @param sgId the sg id
     * @param vmId the vm id
     *
     * @return server security group add result
     */
    ServerSecurityGroupAddResult attach(String sgId, String vmId);


    /**
     * 虚拟机解绑安全组
     *
     * @param sgId the sg id
     * @param vmId the vm id
     *
     * @return server security group delete result
     */
    ServerSecurityGroupDeleteResult detach(String sgId, String vmId);

    /**
     * 查询虚拟机所绑定的安全组
     *
     * @param vmId      the vm id
     * @param mgtObjSid the mgt obj sid
     *
     * @return vm query sgs result
     */
    VmQuerySgsResult selectByVm(String vmId, String mgtObjSid);
}