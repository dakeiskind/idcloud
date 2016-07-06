package com.h3c.idcloud.core.service.charge.provider;

import java.util.*;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.charge.dao.BillingPricingMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPricing;
import com.h3c.idcloud.core.pojo.vo.charge.BillingPlanSpecVo;
import com.h3c.idcloud.core.service.charge.api.BillingPricingService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;


@Service(version = "1.0.0")
@Component
public class BillingPricingServiceImpl implements BillingPricingService {
    @Autowired
    private BillingPricingMapper billingPricingMapper;

    private static final Logger logger = LoggerFactory.getLogger(BillingPricingServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.billingPricingMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public BillingPricing selectByPrimaryKey(Long billingPricingSid) {
        return this.billingPricingMapper.selectByPrimaryKey(billingPricingSid);
    }

    public List<BillingPricing> selectByParams(Criteria example) {
        return this.billingPricingMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long billingPricingSid) {
        return this.billingPricingMapper.deleteByPrimaryKey(billingPricingSid);
    }

    public int updateByPrimaryKeySelective(BillingPricing record) {
        return this.billingPricingMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(BillingPricing record) {
        return this.billingPricingMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.billingPricingMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(BillingPricing record, Criteria example) {
        return this.billingPricingMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(BillingPricing record, Criteria example) {
        return this.billingPricingMapper.updateByParams(record, example.getCondition());
    }

    public int insert(BillingPricing record) {
        return this.billingPricingMapper.insert(record);
    }

    public int insertSelective(BillingPricing record) {
        return this.billingPricingMapper.insertSelective(record);
    }

    @Override
    @Transactional
    public int addBillingPrice(BillingPricing record) {
        List<BillingPlanSpecVo> billingPlanSpecVos = record.getBillingPlanSpecVoList();
        List<Long> billedSpecSids = new ArrayList<>();
        List<Long> noBilledSpecSids = new ArrayList<>();
        Map<String,String> configName = new LinkedHashMap<>();
        Map<String,String> configDescription = new HashMap<>();
        Observable<BillingPlanSpecVo> billingPlanSpecVoObservable = Observable.from((BillingPlanSpecVo[])billingPlanSpecVos.toArray(new BillingPlanSpecVo[billingPlanSpecVos.size()]));
        billingPlanSpecVoObservable.flatMap(bvp -> Observable.just(bvp)).subscribe(entity ->{
            configName.put(entity.getName(),entity.getValue());
            configDescription.put(entity.getDescription(),entity.getValue());
        });
        billingPlanSpecVoObservable.filter(bpv -> bpv.isBill()).subscribe(bpv ->{billedSpecSids.add(bpv.getSid());});
        billingPlanSpecVoObservable.filter(bpv -> !bpv.isBill()).subscribe(bpv ->{noBilledSpecSids.add(bpv.getSid());});
        record.setBillingConfigName(StringUtil.formatJson(JsonUtil.toJson(configName)));
        record.setBillingConfigDescription(StringUtil.formatJson(JsonUtil.toJson(configDescription)));
        String billedSpecSidsStr = StringUtil.getFormatedString(billedSpecSids,",");
        String noBilledSpecSidsStr = StringUtil.getFormatedString(noBilledSpecSids,",");
        record.setBillSpecSids(billedSpecSidsStr);
        record.setNoBillSpecSids(noBilledSpecSidsStr);
        int result = this.billingPricingMapper.insertSelective(record);
        return result;
    }
}