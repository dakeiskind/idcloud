package com.h3c.idcloud.core.service.res.api;
import com.h3c.idcloud.core.pojo.dto.res.ResBizVm;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public  interface ResBizVmService
{
//  public abstract int countByParams(Criteria paramCriteria);
//
//  public abstract ResBizVm selectByPrimaryKey(String paramLong);
//
//  public abstract List<ResBizVm> selectByParams(Criteria paramCriteria);
//
//  public abstract int deleteByPrimaryKey(String paramLong);
//
//  public abstract int updateByPrimaryKeySelective(ResBizVm paramResBizVm);
//
//  public abstract int updateByPrimaryKey(ResBizVm paramResBizVm);
//
//  public abstract int deleteByParams(Criteria paramCriteria);
//
//  public abstract int updateByParamsSelective(ResBizVm paramResBizVm, Criteria paramCriteria);
//
//  public abstract int updateByParams(ResBizVm paramResBizVm, Criteria paramCriteria);
//
//  public abstract int insert(ResBizVm paramResBizVm);
//
//  public abstract int insertSelective(ResBizVm paramResBizVm);
//
//  public abstract ResBizVm getVmInfo(ResBizVm paramResBizVm)
//    throws Exception;
//
//  public abstract int createRelation(ResBizVmTO resBizVmTO);
//
//  public abstract int cancelRelation(ResBizVmTO resBizVmTO);
//
//  public abstract List<ResBizVm> selectByParams2(Criteria example);
//
//  public abstract int setMonitorNode(ResBizVmTO resBizVmTO);
//
//  public abstract int modifyVm(ResBizVmTO resBizVmTO);
//
//  public abstract List<ResBizVm> selectByParamsForReport(Criteria example);
//
//  public abstract List<ResBizVm> selectSumByParams(Criteria example);
  
    ResBizVm statisticalBizOfVM(Long resBizSid);
  
    List<ResBizVm> selectNanotubeableVmInBiz(Criteria paramCriteria);
//
//  public abstract List<ResBizVm> selectByParamsForList(Criteria params);
//
//  public abstract List<ResBizVm> selectByParamsForPortal(Criteria example);
//
//  public abstract void deleteServiceInstanceRelation(String resVmId);
}