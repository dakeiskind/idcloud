package com.h3c.idcloud.core.persist.charge.dao;

import java.util.List;
import java.util.Map;

import com.h3c.idcloud.core.pojo.dto.charge.Deposite;
import com.h3c.idcloud.core.pojo.vo.charge.DepositeVo;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositeMapper {
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
    int deleteByPrimaryKey(Long depositeSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Deposite record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Deposite record);

    /**
     * 根据条件查询记录集
     */
    List<Deposite> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Deposite selectByPrimaryKey(Long depositeSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Deposite record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Deposite record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Deposite record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Deposite record);
    
    /**
     * 总的实收金额
     */
    float sumByAmount(Criteria example);

    /**
     * 充值退款当日小计
     */
    List<Map> selectTopupAndRefundByDay(Criteria example);

    /**
     * 用户中心充值记录使用，返回用户所有充值记录信息
     * @param criteria
     * @return
     */
    List<DepositeVo> selectUserDeposites(Criteria criteria);
}