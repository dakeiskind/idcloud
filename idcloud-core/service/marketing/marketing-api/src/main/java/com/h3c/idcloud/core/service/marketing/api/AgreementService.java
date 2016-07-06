package com.h3c.idcloud.core.service.marketing.api;



import com.h3c.idcloud.core.pojo.dto.marketing.Agreement;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 大客户协议service接口
 * @author  chong.wu
 *
 */
public interface AgreementService {

    /**
     * 根据条件查询记录总数
     * @param example
     * @return
     */
    int countByParams(Criteria example);

    /**
     * 根据主键查询记录
     * @param agreementSid
     * @return
     */
    Agreement selectByPrimaryKey(Long agreementSid);

    /**
     * 根据条件查询记录集
     * @param example
     * @return
     */
    List<Agreement> selectByParams(Criteria example);

    /**
     * 根据主键删除记录
     * @param agreementSid
     * @return
     */
    int deleteByPrimaryKey(Long agreementSid);

    /**
     * 根据主键更新属性不为空的记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Agreement record);

    /**
     * 根据主键更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(Agreement record);

    /**
     * 根据条件删除记录
     * @param example
     * @return
     */
    int deleteByParams(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     * @param record
     * @param example
     * @return
     */
    int updateByParamsSelective(Agreement record, Criteria example);

    /**
     * 根据条件更新记录
     * @param record
     * @param example
     * @return
     */
    int updateByParams(Agreement record, Criteria example);

    /**
     * 保存记录,不管记录里面的属性是否为空
     * @param record
     * @return
     */
    int insert(Agreement record);

    /**]
     * 保存属性不为空的记录
     * @param record
     * @return
     */
    int insertSelective(Agreement record);
    
    /**
     * 删除大客户协议
     * @param agreementSid
     * @return
     */
    boolean deleteAgreement(Long agreementSid);      
}