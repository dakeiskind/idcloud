package com.h3c.idcloud.core.service.charge.api;

import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountCdr;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;


public interface BillingAccountCdrService {

	int insert(BillingAccountCdr record);

	int insertSelective(BillingAccountCdr record);

	List<BillingAccountCdr> selectByParams(Criteria example);
}
