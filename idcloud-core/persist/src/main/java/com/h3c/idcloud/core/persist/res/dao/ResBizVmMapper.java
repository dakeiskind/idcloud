package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResBizVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public  interface ResBizVmMapper
{
   int countByParams(Criteria paramCriteria);

  int deleteByParams(Criteria paramCriteria);

   int deleteByPrimaryKey(String paramLong);

   int insert(ResBizVm paramResBizVm);

   int insertSelective(ResBizVm paramResBizVm);
  
   List<ResBizVm> selectByParams(Criteria paramCriteria);
  
   List<ResBizVm> selectNanotubeableVmInBiz(Criteria paramCriteria);

   ResBizVm selectByPrimaryKey(String paramLong);

   int updateByParamsSelective(@Param("record") ResBizVm paramResBizVm, @Param("condition") Map<String, Object> paramMap);

   int updateByParams(@Param("record") ResBizVm paramResBizVm, @Param("condition") Map<String, Object> paramMap);

   int updateByPrimaryKeySelective(ResBizVm paramResBizVm);

   int updateByPrimaryKey(ResVm paramResBizVm);

   List<ResBizVm> selectByParamsForReport(Criteria example);

   List<ResBizVm> selectSumByParams(Criteria example);

   List<ResBizVm> selectByParams2(Criteria example);
  
   ResBizVm statisticalBizOfVM(Long resBizSid);

   List<ResBizVm> selectByParamsForList(Criteria params);

   List<ResBizVm> selectByParamsForPortal(Criteria example);
}