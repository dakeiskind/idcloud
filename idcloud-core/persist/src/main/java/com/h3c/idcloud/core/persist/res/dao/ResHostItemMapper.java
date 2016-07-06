package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResHostItem;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res host item mapper.
 */
@Repository
public interface ResHostItemMapper {
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
     * @param hostItemId the host item id
     * @return the int
     */
    int deleteByPrimaryKey(String hostItemId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResHostItem record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResHostItem record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResHostItem> selectByParams(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param resVeSid the res ve sid
     * @return the list
     */
    List<ResHostItem> selectByPowerVe(String resVeSid);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResHostItem> selectByPowerVeForIo(Criteria example);

    /**
     * 查询磁盘卡及磁盘数量
     *
     * @param condition the condition
     * @return list
     */
    List<ResHostItem> selectDiskItemForVd(Criteria condition);

    /**
     * 根据主键查询记录
     *
     * @param hostItemId the host item id
     * @return the res host item
     */
    ResHostItem selectByPrimaryKey(String hostItemId);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResHostItem record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResHostItem record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResHostItem record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResHostItem record);

    /**
     * Select no disk root hba list.
     *
     * @param resHostSid the res host sid
     * @return the list
     */
    List<ResHostItem> selectNoDiskRootHba(@Param("resHostSid") String resHostSid);
}