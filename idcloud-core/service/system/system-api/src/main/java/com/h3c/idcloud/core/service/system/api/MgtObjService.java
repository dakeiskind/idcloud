package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface MgtObjService {
    int countByParams(Criteria example);

    MgtObj selectByPrimaryKey(Long mgtObjSid);

    List<MgtObj> selectByParams(Criteria example);
    
    List<MgtObj> selectMgtObjTreeByParams(Criteria example);
    
    List<MgtObj> findParentMgtObj(Criteria example);

    int deleteByPrimaryKey(Long mgtObjSid);

    int updateByPrimaryKeySelective(MgtObj record);

    int updateByPrimaryKey(MgtObj record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(MgtObj record, Criteria example);

    int updateByParams(MgtObj record, Criteria example);

    int insert(MgtObj record);

    int insertSelective(MgtObj record);

    List<ResTopology> selectResZoneTopologyByParams(Criteria example);

    List<ResTopology> selectVeListByHost(Criteria example);

    /**
     * 删除底层租户资源
     * @param mgtObjSid
     * @return
     */
	int deleteTenantResByPrimaryKey(Long mgtObjSid);
	
	/**
     * 查询账户信息
     */
    List<MgtObj> selectByBillingAccount(Criteria example);
    
    /**
     * 
     */
    List<MgtObj> selectAllProject(Criteria example);

	List<ResTopology> selectMgtObjComByParams(Criteria example);

	int deleteLocalDataByPrimaryKey(Long mgtObjSid);

	/**
	 * 查询用户对应的第一个管理对象
	 * 
	 */
	MgtObj selectFirstUserMgtObj(Long userSid);

	List<MgtObj> selectMgtObjTreeByParams2(Criteria criteria);

	List<ResTopology> selectMgtObjResZoneTopologyByParams(Criteria resZoneParam);

	List<MgtObj> selectBaseFileByParams(Criteria condition);

	int changeManager(String mgtObjSid, String newManagerSid, String oldManagerSid);
}