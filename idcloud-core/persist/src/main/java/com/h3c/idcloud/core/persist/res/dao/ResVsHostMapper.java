package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResVsHost;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res vs host mapper.
 */
@Repository
public interface ResVsHostMapper {
    /**
     * 根据条件查询记录总数
     *
     * @param example the example
     * @return the int
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     *
     * @param example the example
     * @return the int
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     *
     * @param key the key
     * @return the int
     */
    int deleteByPrimaryKey(ResVsHost key);

    /**
     * 根据主键删除记录
     *
     * @param key the key
     * @return the int
     */
    int deleteByResVsSid(ResVsHost key);

    /**
     * 根据主键删除记录
     *
     * @param key the key
     * @return the int
     */
    int deleteByPrimaryKeyHostSid(ResVsHost key);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResVsHost record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResVsHost record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResVsHost> selectByParams(Criteria example);

    /**
     * 根据虚拟机环境查询关系，并按VS分组
     *
     * @param example the example
     * @return the list
     */
    List<ResVsHost> selectResVsHostByVe(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVsHost record);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResVsHost record, @Param("condition") Map<String, Object> condition);
}