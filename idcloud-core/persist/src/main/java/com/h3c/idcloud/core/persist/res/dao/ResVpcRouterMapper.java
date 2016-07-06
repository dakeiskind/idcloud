package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResVpcRouter;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 接口 Res volume mapper.
 */
@Repository
public interface ResVpcRouterMapper {

    /**
     * Select by params list.
     *
     * @param example the example
     *
     * @return the list
     */
    List<ResVpcRouter> selectByParams(Criteria example);

    /**
     * Select by primary key list.
     *
     * @param key the key
     *
     * @return the list
     */
    ResVpcRouter selectByPrimaryKey(String key);

    /**
     * 根据主键删除记录
     *
     * @param resSid the res sid
     *
     * @return the int
     */
    int deleteByPrimaryKey(String resSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     *
     * @return the int
     */
    int insert(ResVpcRouter record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     *
     * @return the int
     */
    int insertSelective(ResVpcRouter record);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     *
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVpcRouter record);



}