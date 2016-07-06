package com.h3c.idcloud.core.persist.marketing.dao;

import java.util.List;
import java.util.Map;


import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.marketing.AccountLevel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author gujie
 */
@Repository
public interface AccountLevelMapper {
    /**
     * 根据条件查询记录总数
     * @param example 参数
     * @return 返回值
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     * @param example 参数
     * @return 返回值
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     * @param levelSid 参数
     * @return 返回值
     */
    int deleteByPrimaryKey(Long levelSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     * @param record 参数
     * @return 返回值
     */
    int insert(AccountLevel record);

    /**
     * 保存属性不为空的记录
     * @param record 参数
     * @return 返回值
     */
    int insertSelective(AccountLevel record);

    /**
     * 根据条件查询记录集
     * @param example 参数
     * @return 返回值
     */
    List<AccountLevel> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     * @param levelSid 参数
     * @return 返回值
     */
    AccountLevel selectByPrimaryKey(Long levelSid);

    /**
     * 根据条件更新属性不为空的记录
     * @param record 参数
     * @param condition 参数
     * @return 返回值
     */
    int updateByParamsSelective(@Param("record") AccountLevel record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     * @param record 参数
     * @param condition 参数
     * @return 返回值
     */
    int updateByParams(@Param("record") AccountLevel record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     * @param record 参数
     * @return 返回值
     */
    int updateByPrimaryKeySelective(AccountLevel record);

    /**
     * 根据主键更新记录
     * @param record 参数
     * @return 返回值
     */
    int updateByPrimaryKey(AccountLevel record);
}