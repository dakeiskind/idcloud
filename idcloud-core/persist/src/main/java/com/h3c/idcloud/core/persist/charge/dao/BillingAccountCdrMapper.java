package com.h3c.idcloud.core.persist.charge.dao;

import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountCdr;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingAccountCdrMapper {

	int insert(BillingAccountCdr record);

	int insertSelective(BillingAccountCdr record);

	/**
	 * 根据条件查询记录集
	 */
	List<BillingAccountCdr> selectByParams(Criteria example);
}
