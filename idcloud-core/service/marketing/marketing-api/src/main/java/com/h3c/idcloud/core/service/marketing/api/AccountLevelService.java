package com.h3c.idcloud.core.service.marketing.api;


import com.h3c.idcloud.core.pojo.dto.marketing.AccountLevel;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * @author gujie
 */
public interface AccountLevelService {

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    int countByParams(Criteria example);

    /**
     *
     * @param levelSid 参数
     * @return 返回值
     */
    AccountLevel selectByPrimaryKey(Long levelSid);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    List<AccountLevel> selectByParams(Criteria example);

    /**
     *
     * @param levelSid 参数
     * @return 返回值
     */
    int deleteByPrimaryKey(Long levelSid);

    /**
     *
     * @param record 参数
     * @return 返回值
     */
    int updateByPrimaryKeySelective(AccountLevel record);

    /**
     *
     * @param record 参数
     * @return 返回值
     */
    int updateByPrimaryKey(AccountLevel record);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    int deleteByParams(Criteria example);

    /**
     *
     * @param record 参数
     * @param example 参数
     * @return 返回值
     */
    int updateByParamsSelective(AccountLevel record, Criteria example);

    /**
     *
     * @param record 参数
     * @param example 参数
     * @return 返回值
     */
    int updateByParams(AccountLevel record, Criteria example);

    /**
     *
     * @param record 参数
     * @return 返回值
     */
    int insert(AccountLevel record);

    /**
     *
     * @param record 参数
     * @return 返回值
     */
    int insertSelective(AccountLevel record);
    
    /**
     * 新增用户等级
     * 
     * @param level 新增用户登录数据
     * @return 反正结果
     */
    boolean insertLevel(AccountLevel level);  
    
    /**
     * 删除用户等级
     * 
     * @param levelSid 用户等级Sid
     * @return 返回删除结果
     */
    boolean deleteLevel(Long levelSid);    
}