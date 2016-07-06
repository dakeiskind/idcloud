package com.h3c.idcloud.core.service.charge.api;

import com.h3c.idcloud.core.pojo.dto.charge.BillingRecord;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface BillingRecordService {
	int countByParams(Criteria example);

    BillingRecord selectByPrimaryKey(Long accountSid);

    List<BillingRecord> selectByParams(Criteria example);
    
    /**
     * 充值
     * @param record
     * @return
     */
    boolean updateBillingRecordAndAccount(BillingRecord record);

    int deleteByPrimaryKey(Long accountSid);

    int updateByPrimaryKeySelective(BillingRecord record);

    int updateByPrimaryKey(BillingRecord record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(BillingRecord record, Criteria example);

    int updateByParams(BillingRecord record, Criteria example);

    int insert(BillingRecord record);

    int insertSelective(BillingRecord record);
}
