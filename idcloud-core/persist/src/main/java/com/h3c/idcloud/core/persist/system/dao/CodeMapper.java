package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;


import com.h3c.idcloud.core.pojo.dto.system.Code;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeMapper {
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
    int deleteByPrimaryKey(Long codeSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Code record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Code record);

    /**
     * 根据条件查询记录集
     */
    List<Code> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Code selectByPrimaryKey(Long codeSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Code record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Code record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Code record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Code record);

    /**
     * 查寻镜像可部署的数据库和中间件数据
     */
    List<Code> findImageSoftWare(Criteria example);

    List<Code> findParentCodeByCodeVaule(Criteria example);

    List<Code> getParentCodeByCodeVaule(Criteria example);
}