package com.h3c.idcloud.core.service.charge.provider;

import java.util.Date;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.charge.dao.BillingRecordMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountCdr;
import com.h3c.idcloud.core.pojo.dto.charge.BillingRecord;
import com.h3c.idcloud.core.service.charge.api.BillingAccountCdrService;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.charge.api.BillingRecordService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class BillingRecordServiceImpl implements BillingRecordService {
	
	@Autowired
	private BillingRecordMapper billingRecordMapper;

	@Autowired
	private BillingAccountCdrService billingRecordCdrService;

	@Autowired
	private BillingAccountService baService;
	
	private static final Logger logger = LoggerFactory.getLogger(BillingRecordServiceImpl.class);


	@Override
	public int countByParams(Criteria example) {
		return this.billingRecordMapper.countByParams(example);
	}

	@Override
	public BillingRecord selectByPrimaryKey(Long accountSid) {
		return this.billingRecordMapper.selectByPrimaryKey(accountSid);
	}

	@Override
	public List<BillingRecord> selectByParams(Criteria example) {
		return this.billingRecordMapper.selectByParams(example);
	}

	@Override
	public int deleteByPrimaryKey(Long accountSid) {
		return this.billingRecordMapper.deleteByPrimaryKey(accountSid);
	}

	@Override
	public int updateByPrimaryKeySelective(BillingRecord record) {
		return this.billingRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BillingRecord record) {
		return this.billingRecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByParams(Criteria example) {
		return this.billingRecordMapper.deleteByParams(example);
	}

	@Override
	public int updateByParamsSelective(BillingRecord record, Criteria example) {
		return this.billingRecordMapper.updateByParamsSelective(record, example.getCondition());
	}

	@Override
	public int updateByParams(BillingRecord record, Criteria example) {
		return this.billingRecordMapper.updateByParams(record, example.getCondition());
	}

	@Override
	public int insert(BillingRecord record) {
		return this.billingRecordMapper.insert(record);
	}

	@Override
	public int insertSelective(BillingRecord record) {
		return this.billingRecordMapper.insertSelective(record);
	}

	@Override
	public boolean updateBillingRecordAndAccount(BillingRecord record) {
		BillingRecord br = this.billingRecordMapper.selectByPrimaryKey(record.getId());
		if(br.getStatus().equals("PAID")){
			//如果状态已支付，则直接返回成功。
			logger.info("BillingRecord:"+br.getId()+" has status of PAID.");
			return true;
		}else{
			int i = this.billingRecordMapper.updateByPrimaryKeySelective(record);
			if(i >0){
				BillingAccount ba = this.baService.selectByPrimaryKey(br.getAccountSid());
				if(ba==null){
					logger.error("BillingAccount is null.");
					return false;
				}else{
					boolean re = false;
					try {
						BillingAccountCdr baCdr = new BillingAccountCdr();
						baCdr.setOpType(WebConstants.billingAccountCdrOpType.DEPOSIT);
						baCdr.setBalance(ba.getBalance().add(br.getAmount()));
						baCdr.setOpAmount(br.getAmount());
						baCdr.setOpDate(new Date());
						baCdr.setBillingAccountSid(ba.getAccountSid());
						baCdr.setOpUser(br.getCreatedBy());
						baCdr.setTradeNo(record.getFlowNo());
						baCdr.setStatus(WebConstants.billingAccountCdrStatus.SUCCESS);
//						WebUtil.prepareInsertParams(baCdr);
						re = this.baService.updateAccount(ba.getAccountSid(), br.getAmount(), false);
						logger.info("Add Balance for account:"+ba.getAccountSid()+" for amount of "+br.getAmount()+".");
						if(re){
							this.billingRecordCdrService.insert(baCdr);
							logger.info("Add AccountCdr for account:"+ba.getAccountSid()+" for amount of "+br.getAmount()+".");
						}
					} catch (Exception e) {
						logger.error("BillingAccount:update account error."+e);
						return false;
					}
					return re;
				}
			}else{
				return false;
			}
		}
	}

}
