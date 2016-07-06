package com.h3c.idcloud.core.service.charge.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.charge.dao.BillingAccountCdrMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountCdr;
import com.h3c.idcloud.core.service.charge.api.BillingAccountCdrService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class BillingAccountCdrServiceImpl implements BillingAccountCdrService {

	@Autowired
    private BillingAccountCdrMapper billingServiceCdrMapper;
	
	@Override
	public int insert(BillingAccountCdr record) {
		return this.billingServiceCdrMapper.insert(record);
	}

	@Override
	public int insertSelective(BillingAccountCdr record) {
		return this.billingServiceCdrMapper.insertSelective(record);
	}

	@Override
	public List<BillingAccountCdr> selectByParams(Criteria example) {
		return this.billingServiceCdrMapper.selectByParams(example);
	}

}
