package com.h3c.idcloud.core.persist.product.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.product.FreeResCheckLog;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;

public interface FreeResCheckLogMapper {
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
    int deleteByPrimaryKey(Long fresCheckLogSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(FreeResCheckLog record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(FreeResCheckLog record);

    /**
     * 根据条件查询记录集
     */
    List<FreeResCheckLog> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    FreeResCheckLog selectByPrimaryKey(Long fresCheckLogSid);

    /**
     * 根据fresSid查询记录
     */
    FreeResCheckLog selectByFreeSid(Long fresSid);
    
    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") FreeResCheckLog record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") FreeResCheckLog record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(FreeResCheckLog record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(FreeResCheckLog record);

    /**
     * 查询业务所闲置的资源的总和
     */
    List<FreeResCheckLog> selectResByBizForSummary(Criteria example);
    
    /**
     * 根据实例id，取得对应的服务变更id
     */
    FreeResCheckLog selectResByInstIdForCmId(Criteria example);
    
}