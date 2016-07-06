package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResVmVncPort;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res vm vnc port mapper.
 */
@Repository
public interface ResVmVncPortMapper {
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
     * @param resVmSid the res vm sid
     * @return the int
     */
    int deleteByPrimaryKey(String resVmSid);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResVmVncPort record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResVmVncPort> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param resVmSid the res vm sid
     * @return the res vm vnc port
     */
    ResVmVncPort selectByPrimaryKey(String resVmSid);

    /**
     * 根据主键查询同VE下虚拟机的vnc端口占用
     *
     * @param example the example
     * @return the list
     */
    List<ResVmVncPort> selectByParamByVmSid(Criteria example);

    /**
     * 根据主键查询同VE下虚拟机的vnc端口占用数量
     *
     * @param resTopologySid the res topology sid
     * @return the int
     */
    int selectByParamByCountVnc(String resTopologySid);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResVmVncPort record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVmVncPort record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResVmVncPort record);
}