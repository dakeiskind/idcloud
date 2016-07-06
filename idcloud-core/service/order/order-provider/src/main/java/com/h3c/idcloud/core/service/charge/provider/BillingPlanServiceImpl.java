package com.h3c.idcloud.core.service.charge.provider;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.persist.charge.dao.BillingPlanMapper;
import com.h3c.idcloud.core.persist.charge.dao.BillingPlanSpecMapper;
import com.h3c.idcloud.core.persist.charge.dao.BillingPricingDetailMapper;
import com.h3c.idcloud.core.persist.charge.dao.BillingPricingMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPlan;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPlanSpec;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPricing;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPricingDetail;
import com.h3c.idcloud.core.pojo.vo.charge.BillingPlanSpecVo;
import com.h3c.idcloud.core.service.charge.api.BillingPlanService;
import com.h3c.idcloud.infrastructure.common.constants.BusinessMessageConstants;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import com.rabbitmq.tools.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Service(version = "1.0.0")
@Component
public class BillingPlanServiceImpl implements BillingPlanService {


    private static final String INTERRREGIONAL = "[1-9]{1}[0-9]*\\-[1-9]{1}[0-9]*\\*[1-9]{1}[0-9]*";

    private static final String OTHER = "OTHER";

    @Autowired
    private BillingPlanMapper billingPlanMapper;

    @Autowired
    private BillingPricingMapper billingPricingMapper;

    @Autowired
    private BillingPricingDetailMapper billingPricingDetailMapper;

    @Autowired
    private BillingPlanSpecMapper billingPlanSpecMapper;

    private static final Logger logger = LoggerFactory.getLogger(BillingPlanServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.billingPlanMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public BillingPlan selectByPrimaryKey(Long billingPlanSid) {
        return this.billingPlanMapper.selectByPrimaryKey(billingPlanSid);
    }

    public List<BillingPlan> selectByParams(Criteria example) {
        return this.billingPlanMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long billingPlanSid) {
        return this.billingPlanMapper.deleteByPrimaryKey(billingPlanSid);
    }

    public int updateByPrimaryKeySelective(BillingPlan record) {
        return this.billingPlanMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(BillingPlan record) {
        return this.billingPlanMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.billingPlanMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(BillingPlan record, Criteria example) {
        return this.billingPlanMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(BillingPlan record, Criteria example) {
        return this.billingPlanMapper.updateByParams(record, example.getCondition());
    }

    public int insert(BillingPlan record) {
        return this.billingPlanMapper.insert(record);
    }

    public int insertSelective(BillingPlan record) {
        return this.billingPlanMapper.insertSelective(record);
    }

	@Override
	public BillingPlan selectBillingByServiceSidAndStatus(
			Long serviceSid) {
		
		return this.billingPlanMapper.selectBillingByServiceSidAndStatus(serviceSid);
	}

    @Override
    public List<BillingPlanSpecVo> selectBillingPlanSpecVos(Long serviceSid, Long billingPlanSid) {
        return billingPlanMapper.selectBillingPlanSpecVos(serviceSid, billingPlanSid);
    }

    @Override
    public List<BillingPlanSpecVo> selectBillingPlanSpecVos(Long serviceSid) {
        return billingPlanMapper.selectAllBillingPlanSpecVos(serviceSid);
    }

    @Override
    public Long getServiceSid(String serviceCode) {
        //TODO 其他暂未定义
        Long serviceSid;
        switch (serviceCode){
            case WebConstants.ServiceCode.CS:
                serviceSid = 100L;
                break;
            case WebConstants.ServiceCode.OSS:
                serviceSid = 104L;
                break;
            case WebConstants.ServiceCode.CBS:
                serviceSid = 105L;
                break;
            case WebConstants.ServiceCode.EIP:
                serviceSid = 106L;
                break;
            case WebConstants.ServiceCode.CND:
                serviceSid = 107L;
                break;
            default:
                serviceSid = 100L;
                break;
        }
        return serviceSid;
    }

    @Override
    public Map<String, Object> getBillingPriceConfig(Long serviceSid, String billingType, Map<String, Object> specifications) {
        Map<String,Object> billingConfigMap = new HashMap<>();
        //获取并验证资费计划
        String billingPlanType = (billingType.equals(WebConstants.BILLING_TYPE.MONTH)||
                billingType.equals(WebConstants.BILLING_TYPE.YEAR))?WebConstants.BILLING_PLAN_TYPE.YM:WebConstants.BILLING_PLAN_TYPE.METERING;
        Criteria cirteria = new Criteria();
        cirteria.put("serviceSid",serviceSid);
        cirteria.put("billingPlanType",billingPlanType);
        cirteria.put("planStatus", WebConstants.BILL_PLAN_STATUS.ABLE);
        List<BillingPlan> billingPlanList =  billingPlanMapper.selectByParams(cirteria);
        if(StringUtil.isNullOrEmpty(billingPlanList) || billingPlanList.size() !=1)
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(BusinessMessageConstants.BillingMessage.CAN_NOT_FIND_BILLING_PLAN,
                    new String[]{String.valueOf(serviceSid)}));
        // 获取当前资费计划下配置的所有与计费相关的规格项
        Criteria specsPara = new Criteria();
        specsPara.put("billingPlanSid",billingPlanList.get(0).getBillingPlanSid());
        List<BillingPlanSpec> billingPlanSpecs = billingPlanSpecMapper.selectByParams(specsPara);
        // 根据与计费相关的配置项对原始specifications进行过滤最后只保留与计费相关的配置项，
        // 一切从相应的资费计划配置出发自动匹配最后调用计费服务获得最终价格
        for(Map.Entry<String,Object> specMap:specifications.entrySet()){
            boolean isMatched = isMatched(specMap.getKey(),billingPlanSpecs);
            boolean needFormatSubItem = (specMap.getValue() instanceof ArrayList)?true:false;
            while(isMatched){
                if(needFormatSubItem){
                    List<Map<String,String>> specItems = (List<Map<String,String>>)specMap.getValue();
                    List<Map<String,String>> filteredItems = new ArrayList<>();
                    for(Map<String,String> item:specItems){
                        Map<String,String> filteredItem = new HashMap<>();
                        for(Map.Entry<String,String> itemDetail:item.entrySet()){
                            boolean itemMatched = isMatched(itemDetail.getKey(),billingPlanSpecs);
                            if(itemMatched)
                                filteredItem.put(itemDetail.getKey(),itemDetail.getValue());
                        }
                        filteredItems.add(filteredItem);
                    }
                    billingConfigMap.put(specMap.getKey(),filteredItems);

                }else{
                    billingConfigMap.put(specMap.getKey(),specMap.getValue());
                }
                break;
            }
        }
        return billingConfigMap;
    }

    @Override
    public BigDecimal getPriceForWebCenter(ArrayList<Map<String, Object>> models) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map<String, Object> map : models) {
            Long serviceSid = getServiceSid(map.get(WebConstants.SpecificationProperty.SERVICE_CODE).toString());
            Map<String,Object> data = (Map<String,Object>)map.get(WebConstants.SpecificationProperty.DATA);
            String billingType = StringUtil.nullToEmpty(data.get(WebConstants.SpecificationProperty.BILLING_TYPE));
            //过滤与资费不相关的规格配置
            Map<String,Object> specifications = (Map<String,Object>)map.get(WebConstants.SpecificationProperty.SPECIFICATIONS);
            Map<String,Object> billingSpecJsonMap = getBillingPriceConfig(serviceSid,billingType,specifications);
            //调用资费服务获取当前配置的总价
            BigDecimal price = getBillingPrice(serviceSid,billingType,JsonUtil.toJson(billingSpecJsonMap));
            // TODO 包年包月
            boolean isYm = (billingType.equals(WebConstants.BILLING_TYPE.MONTH)||
                    billingType.equals(WebConstants.BILLING_TYPE.YEAR))?true:false;
            //根据不同的计费类型获得最终价格
            if(isYm){
                BigDecimal currentItemAmount = price.multiply(new BigDecimal(Integer.parseInt(data.get(WebConstants.SpecificationProperty.QUANTITY).toString())))
                        .multiply(BigDecimal.valueOf(Double.valueOf(data.get(WebConstants.SpecificationProperty.DURATION).toString())));
                totalPrice = totalPrice.add(currentItemAmount);
            }else{
                // TODO 按使用量计费
                totalPrice = price;
            }

        }
        return totalPrice;
    }

    /**
     * 规格匹配
     * @param matchKey  服务sid，对应的云服务
     * @param billingPlanSpecs  所属资费计划的与计费相关的所有规格
     * @return match result
     */
    private boolean isMatched(String matchKey,List<BillingPlanSpec> billingPlanSpecs){
        for(BillingPlanSpec bps:billingPlanSpecs){
            if(matchKey.equals(bps.getSpecName()))
                return true;
        }
        return false;
    }

    @Override
    public BigDecimal getBillingPrice(Long serviceSid, String billingType, String billingSpecConfig) {

        String billingPlanType = (billingType.equals(WebConstants.BILLING_TYPE.MONTH)||
                billingType.equals(WebConstants.BILLING_TYPE.YEAR))?WebConstants.BILLING_PLAN_TYPE.YM:WebConstants.BILLING_PLAN_TYPE.METERING;
        BigDecimal price = BigDecimal.ZERO;
        //资费计划验证
        Criteria param = new Criteria();
        param.put("serviceSid",serviceSid);
        param.put("billingPlanType",billingPlanType);
        param.put("planStatus", WebConstants.BILL_PLAN_STATUS.ABLE);
        List<BillingPlan> billingPlans = this.billingPlanMapper.selectByParams(param);
        if(StringUtil.isNullOrEmpty(billingPlans) || billingPlans.size() !=1)
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(BusinessMessageConstants.BillingMessage.CAN_NOT_FIND_BILLING_PLAN,
                    new String[]{String.valueOf(serviceSid)}));
        //查找对应匹配的资费计划组合
        Criteria example = new Criteria();
        example.put("billingPlanSid", billingPlans.get(0).getBillingPlanSid());
        example.put("billingType", billingType);
        example.put("status",WebConstants.BILL_PLAN_STATUS.ABLE);
        List<BillingPricing> billingPricings = this.billingPricingMapper.selectByParams(example);
        if(StringUtil.isNullOrEmpty(billingPricings))
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(BusinessMessageConstants.BillingMessage.CAN_NOT_FIND_BILLING_PRICE_CONFIG,
                    new String[]{String.valueOf(billingSpecConfig)}));
        //查找匹配BillingPricing
        Map<String,Object> billingSpecConfigMap = JsonUtil.fromJson(billingSpecConfig,HashMap.class);
        Criteria specsPara = new Criteria();
        List<String> specs = new ArrayList<>();
        billingSpecConfigMap.forEach((key,value) -> specs.add(key));
        specsPara.put("specNames",specs);
        specsPara.put("billingPlanSid",billingPlans.get(0).getBillingPlanSid());
        List<BillingPlanSpec> billingPlanSpecs = this.billingPlanSpecMapper.selectDiffBilledList(specsPara);
        Map<String,Object> maps = new HashMap<>();
        Map<String,Object> specNamesMap = new HashMap<>();
        billingPlanSpecs.forEach(bps ->{
            maps.put(String.valueOf(bps.getIsBill()),bps.getSpecSids());
            specNamesMap.put(String.valueOf(bps.getIsBill()),bps.getSpecNames());
        });
        String billedSids = StringUtil.nullToEmpty(maps.get(String.valueOf(WebConstants.SpecBillStatus.BILLED))) ;
        String noBillSids = StringUtil.nullToEmpty(maps.get(String.valueOf(WebConstants.SpecBillStatus.NO_BILLED)));
        BillingPricing compareBillingPricing = null;
        for(BillingPricing bp:billingPricings){
            boolean isCompareBilled = this.compareSpecSids(billedSids,bp.getBillSpecSids());
            boolean isCompareNoBilled = this.compareSpecSids(noBillSids,bp.getNoBillSpecSids());
            if(isCompareBilled && isCompareNoBilled){
                compareBillingPricing = bp;
                break;
            }
        }
        if(StringUtil.isNullOrEmpty(compareBillingPricing))
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(BusinessMessageConstants.BillingMessage.CAN_NOT_FIND_BILLING_PRICE_CONFIG,
                    new String[]{String.valueOf(billingSpecConfig)}));

        //对匹配的billing pricing
        Map<String,String> associationMap = JsonUtil.fromJson(compareBillingPricing.getBillingConfigName(),HashMap.class);

        //过滤并获取必须要计费的配置
        Map<String,Object> billedConfigMap = getMustBilledConfig(compareBillingPricing.getBillingPlanSid(),billedSids.split(","),billingSpecConfig);

        //进行未计费的组合规格项验证
        validateNoBilledSpecConfig(associationMap,billedConfigMap,billingSpecConfigMap);

        //获取当前计费组合下的所有计费规格配置明细
        Criteria para = new Criteria();
        para.put("billingPricingSid",compareBillingPricing.getBillingPricingSid());
        para.put("specSids",compareBillingPricing.getBillSpecSids().split(","));
        para.put("status",WebConstants.BILL_PLAN_STATUS.ABLE);
        List<BillingPricingDetail> billingPricingDetails = billingPricingDetailMapper.selectByParams(para);

        //获取整合规划billingPricingDetails 为Map<configKey,List<BillingPricingDetail>>
        Map<String,List<BillingPricingDetail>> billingConfigMaps = getSpeBillingPriceDetaisMap(billingPricingDetails);
        //价格计算
        for(Map.Entry<String,Object> entry:billedConfigMap.entrySet()){
            List<BillingPricingDetail> matchedDetails = billingConfigMaps.get(entry.getKey());
            Map<String,Object> priceMap = getConfigPrice(entry,matchedDetails);
            BigDecimal totalPrice = new BigDecimal(priceMap.get("totalPrice").toString());
            logger.info("BillingPlanServiceImpl----getPrice---spec:"+entry.getKey()+" value:"+priceMap);
            price = price.add(totalPrice);
        }
        return price.setScale(3,BigDecimal.ROUND_DOWN);
    }

    /**
     * 验证未计费的规格项值的正确性
     * @param associationMap 计费组合json map
     * @param billedConfigMap 计费的所有项目
     * @param billingSpecConfigMap 所有计费规格json map
     */
    private void validateNoBilledSpecConfig(Map<String,String> associationMap,Map<String,Object> billedConfigMap,Map<String,Object> billingSpecConfigMap){
        int matchedCount = 0;
        int noBilledCount = billingSpecConfigMap.size() - billedConfigMap.size();
        Map<String,String> noMatchedSpecMap = new HashMap<>();
        for(Map.Entry<String,String> entry:associationMap.entrySet()){
            if(!billedConfigMap.containsKey(entry.getKey()) ){
                if(entry.getValue().equals(billingSpecConfigMap.get(entry.getKey())))
                    matchedCount = matchedCount + 1;
                else
                    noMatchedSpecMap.put(entry.getKey(),StringUtil.nullToEmpty(billingSpecConfigMap.get(entry.getKey())));
            }
        }
        if(matchedCount != noBilledCount)
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(BusinessMessageConstants.BillingMessage.CAN_NOT_FIND_BILLING_PRICE_DETAIL_CONFIG,
                    new String[]{noMatchedSpecMap.toString()}));
    }

    /**
     * 获取规格计费价格
     * @param configEntry  规格配置entry key="systemDisk" value="[{
                            "systemDiskCategory": "cloud_efficiency",
                            "systemDiskSize": "40"
                              }]"  所有的计费配置value 都为List<Map>,计费组合，每个组合下的每个规格都必须全部吻合</>
     * @param matchedDetails  当前计费规格下已经的配置的所有计费配置项
     * @return  如：{
     *            "instance": [{
                  "instanceCategory": "idc-S",
                  "cpu": "1",
                  "memory": "1",
                  "price": "1000",
                  "discount": "1"
                  }],
                 "totalPrice":1000
                 } 返回值是原来的规格map，子项增加 price属性计算的价格及discount折扣， root 级增加totalPrice 当前下所有组合的总价格
     */
    private Map<String,Object> getConfigPrice(Map.Entry<String,Object> configEntry,List<BillingPricingDetail> matchedDetails){
        BigDecimal totalPrice = BigDecimal.ZERO;
        Map<String,Object> pricedMap = new HashMap<>();
        Map<String,BigDecimal> itemPrice = new HashMap<>();
        List<Map<String,String>> subListMap = new ArrayList<>();
        //组合定价
        if(configEntry.getValue() instanceof ArrayList) {
            List<Map<String,String>> specConfigs = (List<Map<String,String>>)configEntry.getValue();
            //每一项都必须匹配
            for(Map<String,String> specMap:specConfigs){
                //计算每一个配置项价格数据
                itemPrice = calculatePrice(specMap,matchedDetails);
                totalPrice = totalPrice.add(itemPrice.get("price"));
                specMap = getConfigPriceMap(specMap,itemPrice);
                subListMap.add(specMap);
            }
            pricedMap.put(configEntry.getKey(),subListMap);
        }else{
            //单项定价
            Map<String,String> singleConfigMap = new HashMap<>();
            singleConfigMap.put(configEntry.getKey(),configEntry.getValue().toString());
            itemPrice = calculatePrice(singleConfigMap,matchedDetails);
            totalPrice = totalPrice.add(itemPrice.get("price"));
            singleConfigMap = getConfigPriceMap(singleConfigMap,itemPrice);
            pricedMap.put(configEntry.getKey(),singleConfigMap);
        }
        pricedMap.put("totalPrice",totalPrice);
        return pricedMap;
    }

    private Map<String,String> getConfigPriceMap(Map<String,String> singleConfigMap,Map<String,BigDecimal> itemPrice){
        singleConfigMap.put("price",itemPrice.get("price").toString());
        singleConfigMap.put("discount",itemPrice.get("discount").toString());
        return singleConfigMap;
    }

    /**
     * 计算当前配置的最终价格，返回的为Map<String,BigDecimal>  key1:price 最终折扣后的价格  key2:discount 折扣率
     * @param singleSpecGroup  计费的规格配置组合
     * @param matchedDetails   当前规格配置所配置的所有计费明细信息
     * @return Map<String,BigDecimal>  key1:price 最终折扣后的价格  key2:discount 折扣率
     */
    private Map<String,BigDecimal> calculatePrice(Map<String,String> singleSpecGroup,List<BillingPricingDetail> matchedDetails){
        Map<String,BigDecimal> priceDiscountMap = new HashMap<>();
        BigDecimal price = BigDecimal.ZERO;
        List<Map<String,String>> matchedBilledJsonConfig = null;
        BillingPricingDetail matchedBillingDetail = null;
        for(BillingPricingDetail bpd:matchedDetails){
            List<Map<String,String>> configValues = JsonUtil.fromJson(bpd.getValue(),List.class);
            //规格项定义个数相同
            if(configValues.size() == singleSpecGroup.size()){
                int matchedSize = 0;
               for(Map.Entry<String,String> entryConfiged:singleSpecGroup.entrySet()){
                   for(Map<String,String> configItem:configValues){
                       if(configItem.get("billingChargeType").equals(WebConstants.BILLING_CHARGE_TYPE.INCREMENT_CHARGE)
                               && configItem.get("moduleCode").equals(entryConfiged.getKey())){
                           String value = entryConfiged.getValue();
                           //增量收费方式需要区间值匹配
                           BigDecimal[] interregionalValues = getInterregionalValues(configItem.get("moduleValue"));
                           BigDecimal startValue = interregionalValues[0];  //区间起始值
                           BigDecimal endValue = interregionalValues[1];  //区间结束值
                           BigDecimal interval = interregionalValues[2];  //间隔价格值
                           if(StringUtil.isNumericS(value)
                            &&Double.valueOf(value).doubleValue() <= endValue.doubleValue()
                                   && Double.valueOf(value).doubleValue() >= startValue.doubleValue()){
                               List<BigDecimal> values = interregionalValueGenerator(startValue,endValue,interval);
                               boolean isMatched = isMatchedBigDecimal(Double.valueOf(value),values);
                               if(isMatched){
                                   matchedSize = matchedSize + 1;
                                   break;
                               }

                           }
                       }else{
                           //固定收费与不收费，code与value应该是完全匹配的
                           if(configItem.get("moduleCode").equals(entryConfiged.getKey())
                                   && configItem.get("moduleValue").equals(entryConfiged.getValue())){
                               matchedSize = matchedSize + 1;
                               break;
                           }
                       }
                   }
               }
                //找到匹配
                if(matchedSize == singleSpecGroup.size()){
                    matchedBilledJsonConfig = configValues;
                    matchedBillingDetail = bpd;
                    break;
                }
            }

        }
        if(StringUtil.isNullOrEmpty(matchedBilledJsonConfig))
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(BusinessMessageConstants.BillingMessage.CAN_NOT_FIND_BILLING_PRICE_DETAIL_CONFIG,
                    new String[]{singleSpecGroup.toString()}));
        //开始计算价格
        for (Map.Entry<String,String> entryConfiged:singleSpecGroup.entrySet()){
            for(Map<String,String> itemConfig:matchedBilledJsonConfig){
                //每个计费配置moduleCode是唯一，不可以重复配置
                if(entryConfiged.getKey().equals(itemConfig.get("moduleCode")) ){
                    if(itemConfig.get("billingChargeType").equals(WebConstants.BILLING_CHARGE_TYPE.FIX_CHARGE)
                            &&entryConfiged.getValue().equals(itemConfig.get("moduleValue"))){
                        //固定收费直接获取配置的价格
                        price = (price.add(new BigDecimal(itemConfig.get("unitPrice")))).multiply(new BigDecimal(matchedBillingDetail.getDiscount()));
                    }else if(itemConfig.get("billingChargeType").equals(WebConstants.BILLING_CHARGE_TYPE.INCREMENT_CHARGE)){
                       //计算区间价格，这里获得一定是可计算匹配的区间
                        BigDecimal[] stepConfigValues = getInterregionalValues(itemConfig.get("moduleValue"));
                        BigDecimal startValue = stepConfigValues[0];
                        BigDecimal endValue = stepConfigValues[1];
                        BigDecimal interval = stepConfigValues[2];
                        List<BigDecimal> stepValues = interregionalValueGenerator(startValue,endValue,interval);
                        for(BigDecimal bd:stepValues){
                            if (bd.doubleValue() == Double.valueOf(entryConfiged.getValue())){
                                BigDecimal unitPrice = new BigDecimal(itemConfig.get("unitPrice"));
                                BigDecimal treads = (bd.subtract(startValue)).divide(interval);
                                BigDecimal initValue = new BigDecimal(itemConfig.get("initPrice").toString());
                                price = (price.add(initValue).add(treads.multiply(unitPrice))).multiply(new BigDecimal(matchedBillingDetail.getDiscount()));;
                                break;
                            }
                        }
                    }else{
                        //其他类型为不计费不关心
                    }
                }
            }
        }
        priceDiscountMap.put("price",price.setScale(3,BigDecimal.ROUND_DOWN));
        priceDiscountMap.put("discount",new BigDecimal(matchedBillingDetail.getDiscount()));
        return priceDiscountMap;
    }

    /**
     * 数值匹配
     * @param needMatched 需要匹配的值
     * @param source 匹配源
     * @return
     */
    private boolean isMatchedBigDecimal(double needMatched,List<BigDecimal> source){
        for(BigDecimal bd : source){
            if(bd.doubleValue() == needMatched)
                return true;
        }
        return false;
    }

    /**
     * 计算规格是否完全匹配
     * @param source main
     * @param compareAbleString compare string
     * @return boolean
     */
    private boolean compareSpecSids(String source, String compareAbleString){
        boolean isCompare = false;
        if(!StringUtil.isNullOrEmpty(source) && !StringUtil.isNullOrEmpty(compareAbleString)){
           String[] sourceSids  = source.split(",");
            String[] compareAbleSids = compareAbleString.split(",");
            if(sourceSids.length != compareAbleSids.length)
                return isCompare;
            int matchCount = 0;
            for(String s :sourceSids){
                for(String cs:compareAbleSids){
                    if(s.equals(cs))
                        matchCount = matchCount +1;
                }
            }
            if(matchCount == sourceSids.length)
                isCompare = true;
        }else if(StringUtil.isNullOrEmpty(source) && StringUtil.isNullOrEmpty(compareAbleString)) {
            isCompare = true;
        }else if(StringUtil.isNullOrEmpty(source) && !StringUtil.isNullOrEmpty(compareAbleString)){
            isCompare = false;
        }else if(!StringUtil.isNullOrEmpty(source) && StringUtil.isNullOrEmpty(compareAbleString)){
            isCompare = false;
        }else{

        }
        return isCompare;
    }

    /**
     * 对所有当前的计费配置项进行分类并以Map<configKey,List<BillingPricingDetail>> 的方式返回
     * @param billingPricingDetails 所有关联的计费项
     * @return
     */
    private Map<String,List<BillingPricingDetail>> getSpeBillingPriceDetaisMap(List<BillingPricingDetail> billingPricingDetails){
        Map<String,List<BillingPricingDetail>> configMapList = new HashMap<String,List<BillingPricingDetail>>();
        for(BillingPricingDetail bpd:billingPricingDetails){
            List<BillingPricingDetail> billingPricingDetailList = null;
            if(configMapList.containsKey(bpd.getName())){
                billingPricingDetailList = configMapList.get(bpd.getName());
            }else{
                billingPricingDetailList = new ArrayList<>();
            }
            billingPricingDetailList.add(bpd);
            configMapList.put(bpd.getName(),billingPricingDetailList);
        }
        return configMapList;
    }

    /**
     * 根据区间参数返回初始值，结束值，间隔值
     * @param interregionalValue 区间参数10~100*10
     * @return BigDecimal[初始值，结束值，间隔值]
     */
    private BigDecimal[] getInterregionalValues(String interregionalValue){
        BigDecimal startValue = BigDecimal.valueOf(Double.valueOf(interregionalValue.split("~")[0]));
        BigDecimal endValue = BigDecimal.valueOf(Double.valueOf(interregionalValue.split("~")[1].split("[*]")[0]));
        BigDecimal interval = BigDecimal.valueOf(Double.valueOf(interregionalValue.split("~")[1].split("[*]")[1]));
        return new BigDecimal[]{startValue,endValue,interval};
    }

    /**
     * 创建区间值
     * @param startValue 起始值
     * @param endValue   结束值
     * @param interval  间隔值
     * @return 生成的区间值组
     */
    private List<BigDecimal> interregionalValueGenerator(BigDecimal startValue,BigDecimal endValue,BigDecimal interval){
        List<BigDecimal> values = new ArrayList<>();
        values.add(startValue);
        BigDecimal currValue = startValue;
        while(true){
            currValue = currValue.add(interval);
            if(currValue.doubleValue() > endValue.doubleValue())
                break;
            values.add(currValue);
        }
        return values;
    }

    /**
     * 获取计费的配置map
     * @param billSpecSids  计费的配置sids
     * @param configDetail 详细配置的json string
     * @return
     */
    private Map<String,Object> getMustBilledConfig(Long billingPlanSid,String[] billSpecSids,String configDetail) {
        Map<String,Object> billedConfigMap = new HashMap<String,Object>();
        Map<String,Object> priceConfig = JsonUtil.fromJson(configDetail,HashMap.class);
        Criteria param = new Criteria();
        param.put("billingPlanSpecSids",billSpecSids);
        param.put("billingPlanSid",billingPlanSid);
        List<BillingPlanSpec> billingPlanSpecs = billingPlanSpecMapper.selectByParams(param);
        for(Map.Entry<String,Object> entry:priceConfig.entrySet()){
            for(BillingPlanSpec bps:billingPlanSpecs){
                if(bps.getSpecName().equals(entry.getKey()))
                    billedConfigMap.put(entry.getKey(),entry.getValue());
            }
        }
        return billedConfigMap;
    }

    @Override
    @Transactional
    public boolean addBillingPlan(BillingPlan billingPlan,AuthUser authUserInfo) {
        Criteria example = new Criteria();
        example.put("serviceSid", billingPlan.getServiceSid());
        example.put("planStatus", WebConstants.BILL_PLAN_STATUS.ABLE);

        List<BillingPlan> list = this.billingPlanMapper.selectByParams(example);
        if(!StringUtil.isNullOrEmpty(list) && list.size()>0)
            throw new RpcException(RpcException.BIZ_EXCEPTION,"相同的服务只能有一个启用的资费计划!");

        WebUtil.prepareInsertParams(billingPlan,authUserInfo);
        billingPlanMapper.insertSelective(billingPlan);
        // 非关键性业务不影响暂未优化插入
        for(BillingPlanSpecVo bpvo:billingPlan.getBillingPlanSpecVos()){
            BillingPlanSpec billingPlanSpec = new BillingPlanSpec();
            billingPlanSpec.setBillingPlanSid(billingPlan.getBillingPlanSid());
            billingPlanSpec.setServiceSpecSid(bpvo.getSid());
            billingPlanSpec.setSpecName(bpvo.getName());
            billingPlanSpec.setSpecDescription(bpvo.getDescription());
            billingPlanSpec.setIsBill(bpvo.isBill()?1:0);
            WebUtil.prepareInsertParams(billingPlanSpec,authUserInfo);
            this.billingPlanSpecMapper.insertSelective(billingPlanSpec);
        }
        return true;
    }
}