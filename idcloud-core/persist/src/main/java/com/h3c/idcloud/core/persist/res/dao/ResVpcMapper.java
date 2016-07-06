package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResVpc;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 接口 Res volume mapper.
 *
 * @author Chaohong.Mao
 */
@Repository
public interface ResVpcMapper {

    /**
     * Select by params list.
     *
     * @param example the example
     * @return the list
     */
    List<ResVpc> selectByParams(Criteria example);

    /**
     * Select by primary key list.
     *
     * @param key the key
     * @return the list
     */
    ResVpc selectByPrimaryKey(String key);

    /**
     * Select by primary key list.
     *
     * @param uuid the uuid
     * @return the list
     */
    ResVpc selectByUuid(String uuid);

    /**
     * 根据主键删除记录
     *
     * @param resSid the res sid
     * @return the int
     */
    int deleteByPrimaryKey(String resSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResVpc record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResVpc record);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVpc record);


}