package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.MgtObjRes;
import com.h3c.idcloud.core.pojo.dto.product.MgtObjResKey;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MgtObjResMapper {
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
    int deleteByPrimaryKey(MgtObjResKey key);

    /**
     * 保存记录,不管记录里面的属性是否为
     */
    int insert(MgtObjRes record);

    /**
     * 保存属不为空的记
     */
    int insertSelective(MgtObjRes record);

    /**
     * 根据条件查询记录
     */
    List<MgtObjRes> selectByParams(Criteria example);

    /**
     * 根据条件查询记录
     */
    List<MgtObjRes> selectBizReses(Criteria example);

    /**
     * 根据主键查询记录
     */
    MgtObjRes selectByPrimaryKey(MgtObjResKey key);

    /**
     * 根据条件更新属不为空的记
     */
    int updateByParamsSelective(@Param("record") MgtObjRes record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") MgtObjRes record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属不为空的记
     */
    int updateByPrimaryKeySelective(MgtObjRes record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgtObjRes record);

	List<MgtObjRes> selectByParams2(Criteria example);

	List<MgtObjRes> selectMgtObjResByParams(Criteria example);
}