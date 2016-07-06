package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResVolume;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 接口 Res volume mapper.
 */
@Repository
public interface ResVolumeMapper {

    /**
     * Select by params list.
     *
     * @param example the example
     * @return the list
     */
    List<ResVolume> selectByParams(Criteria example);

    /**
     * Count by params int.
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
     * @param resSid the res sid
     * @return the int
     */
    int deleteByPrimaryKey(String resSid);

    /**
     * Delete by res storage sid int.
     *
     * @param resStorageSid the res storage sid
     * @return the int
     */
    int deleteByResStorageSid(String resStorageSid);

    /**
     * Delete by res vd sid int.
     *
     * @param resVdSid the res vd sid
     * @return the int
     */
    int deleteByResVdSid(String resVdSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResVolume record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResVolume record);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVolume record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResVolume record);


}