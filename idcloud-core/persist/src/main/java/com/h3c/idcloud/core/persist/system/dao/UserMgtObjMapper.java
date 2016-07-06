package com.h3c.idcloud.core.persist.system.dao;

import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMgtObjMapper {
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
    int deleteByPrimaryKey(UserMgtObjKey key);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(UserMgtObjKey record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(UserMgtObjKey record);

    /**
     * 根据条件查询记录集
     */
    List<UserMgtObjKey> selectByParams(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") UserMgtObjKey record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") UserMgtObjKey record, @Param("condition") Map<String, Object> condition);
//
//	UserMgtObjKey selectByPrimaryKey(UserMgtObjKey key);
//
	int updateByPrimaryKeySelective(UserMgtObjKey record);
//
//	int updateByPrimaryKey(UserMgtObjKey record);
}