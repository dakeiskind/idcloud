package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.system.ApproveRecord;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveRecordMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long approveRecordSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ApproveRecord record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ApproveRecord record);

    /**
     * 根据条件查询记录集
     */
    List<ApproveRecord> selectByParams(Criteria example);

    //ChengQi start
    /**
     * 根据条件查询记录集
     */
    List<ApproveRecord> selectByParams2(Criteria example);
    
    
    Integer countByParams2(Criteria example);
    //ChengQi end

    /**
     * 根据主键查询记录
     */
    ApproveRecord selectByPrimaryKey(Long approveRecordSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ApproveRecord record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ApproveRecord record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ApproveRecord record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ApproveRecord record);
    
    
    /**
     * 根据条件查询 审核信息记录集
     */
    List<ApproveRecord> selectOrderOpenByParams(Criteria example);
    
    /**
     * 根据条件查询 流程实例ID和TYPE集
     */
    List<ApproveRecord> selectProcessInstanceIdAndType(Criteria example);
    
    /**
     * 根据条件查询运营管理审批记录
     */
    List<ApproveRecord> selectOperateApproveRecord(Criteria example);
    
    /**
     * 根据条件查询已审批记录
     */
    List<ApproveRecord> selectApprovedRecord(Criteria example);

    //ChengQi start
    /**
     * 根据条件查询已审批记录
     */
    List<ApproveRecord> selectApprovedRecord2(Criteria example);
    
    Integer countApprovedRecord2(Criteria example);
    //ChengQi end
}