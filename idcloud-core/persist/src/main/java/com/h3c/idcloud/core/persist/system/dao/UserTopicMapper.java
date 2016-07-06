package com.h3c.idcloud.core.persist.system.dao;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.UserTopic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTopicMapper {
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
    int deleteByPrimaryKey(UserTopic key);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(UserTopic record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(UserTopic record);

    /**
     * 根据条件查询记录集
     */
    List<UserTopic> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    UserTopic selectByPrimaryKey(UserTopic key);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(UserTopic record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(UserTopic record);
}