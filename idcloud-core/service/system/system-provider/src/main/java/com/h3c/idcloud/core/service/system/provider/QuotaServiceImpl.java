package com.h3c.idcloud.core.service.system.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.QuotaMapper;
import com.h3c.idcloud.core.pojo.dto.system.Quota;
import com.h3c.idcloud.core.service.system.api.QuotaService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class QuotaServiceImpl implements QuotaService {
    @Autowired
    private QuotaMapper quotaMapper;

    private static final Logger logger = LoggerFactory.getLogger(QuotaServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.quotaMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Quota selectByPrimaryKey(Long quotaSid) {
        return this.quotaMapper.selectByPrimaryKey(quotaSid);
    }

    public List<Quota> selectByParams(Criteria example) {
        return this.quotaMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long quotaSid) {
        return this.quotaMapper.deleteByPrimaryKey(quotaSid);
    }

    public int updateByPrimaryKeySelective(Quota record) {
        return this.quotaMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Quota record) {
        return this.quotaMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.quotaMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Quota record, Criteria example) {
        return this.quotaMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Quota record, Criteria example) {
        return this.quotaMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Quota record) {
        return this.quotaMapper.insert(record);
    }

    public int insertSelective(Quota record) {
        return this.quotaMapper.insertSelective(record);
    }
    
    public Long selectByQuotaUsage(Criteria example) {
        return this.quotaMapper.selectByQuotaUsage(example);
    }

    public List<Quota> selectParentBizQuota(Criteria example) {
    	return this.quotaMapper.selectParentBizQuota(example);
    }
    
    public List<Quota> selectChildrenBizQuotas(Criteria example) {
    	return this.quotaMapper.selectChildrenBizQuotas(example);
    }
    
    public List<Quota> selectDeptUsageQuotas(Long orgSid) {
    	return this.quotaMapper.selectDeptUsageQuotas(orgSid);
    }

    public Map<String, Object> selectMgtObjQuotasByOsTypes(Long mgtObjSid, String osTypes) {
    	//查询出管理对象下，指定操作系统类型的已使用配额(CPU, 内存，存储)
    	Criteria criteria = new Criteria();
    	String [] osTypeArray = osTypes.split(",");
    	criteria.put("mgtObjSid", mgtObjSid);
    	criteria.put("osTypes", osTypeArray);
    	criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
    	List<Map<String, Object>> usedQuotas = this.quotaMapper.countUsageQuotaByParams(criteria);
    	
    	//查询出管理对象下，指定操作系统类型的云主机数量
    	Long vmCount = this.quotaMapper.countUsageVmByParams(criteria);
    	
    	//查询出管理对象下，当前配置的配额
    	criteria = new Criteria();
    	criteria.put("quotaType", osTypes);
    	criteria.put("quotaObjSid", mgtObjSid);
//    	criteria.put("quotaObj", 2);
    	List<Quota> quotaList = this.quotaMapper.selectByParams(criteria);
    	
    	//
    	Map<String, Object> quotaInfo = new HashMap<String, Object>();

//    	Long cpuUsed = ResDataUtils.getSpecCountValue(WebConstants.InstanceSpecType.CPU, usedQuotas)==null?0:
//    		ResDataUtils.getSpecCountValue(WebConstants.InstanceSpecType.CPU, usedQuotas);
//    	quotaInfo.put("cpuUsed", cpuUsed);
//    	String cpuQuota = ResDataUtils.getQuotaByKey(WebConstants.TenantQuota.CORES, quotaList);
//    	if(cpuQuota != null) {
//	    	quotaInfo.put("cpuQuota", Long.parseLong(cpuQuota));
//	    	quotaInfo.put("cpuUnused", Long.parseLong(cpuQuota) - cpuUsed);
//    	}
//
//    	Long memoryUsed = ResDataUtils.getSpecCountValue(WebConstants.InstanceSpecType.MEMORY, usedQuotas)==null?0:
//    		ResDataUtils.getSpecCountValue(WebConstants.InstanceSpecType.MEMORY, usedQuotas);
//    	quotaInfo.put("memoryUsed", memoryUsed);
//    	String memoryQuota = ResDataUtils.getQuotaByKey(WebConstants.TenantQuota.RAMS, quotaList);
//    	if(memoryQuota != null) {
//	    	quotaInfo.put("memoryQuota", Long.parseLong(memoryQuota));
//	    	quotaInfo.put("memoryUnused", Long.parseLong(memoryQuota) - memoryUsed);
//    	}
//
//    	quotaInfo.put("vmUsed", vmCount);
//    	String vmQuota = ResDataUtils.getQuotaByKey(WebConstants.TenantQuota.INSTANCES, quotaList);
//    	if(vmQuota != null) {
//	    	quotaInfo.put("vmQuota", Long.parseLong(vmQuota));
//	    	quotaInfo.put("vmUnused", Long.parseLong(vmQuota) - vmCount);
//	    }
//
//    	Long storageUsed = ResDataUtils.getSpecCountValue(WebConstants.InstanceSpecType.DATA_DISK, usedQuotas)==null?0:
//    		ResDataUtils.getSpecCountValue(WebConstants.InstanceSpecType.DATA_DISK, usedQuotas);
//    	quotaInfo.put("storageUsed", storageUsed);
//    	String storageQuota = ResDataUtils.getQuotaByKey(WebConstants.TenantQuota.STORAGES, quotaList);
//    	if(storageQuota != null) {
//	    	quotaInfo.put("storageQuota", Long.parseLong(storageQuota));
//	    	quotaInfo.put("storageUnused", Long.parseLong(storageQuota) - storageUsed);
//    	}
    	
    	
//    	criteria.put("mgtObjSid", mgtObjSid);
//    	criteria.put("osTypes", osTypeArray);
//    	Long storageUsed = this.quotaMapper.countUsageStorageByParams(criteria);
//    	quotaInfo.put("storageUsed", storageUsed);
//    	quotaInfo.put("storageUsed", storageUsed==null?0:storageUsed);
//    	String stroageQuota = ResDataUtils.getQuotaByKey(WebConstants.TenantQuota.STORAGES, quotaList);
//    	if(stroageQuota != null) {
//	    	quotaInfo.put("storageQuota", Long.parseLong(stroageQuota));
//	    	quotaInfo.put("storageUnused", Long.parseLong(stroageQuota) - (storageUsed==null?0:storageUsed));
//	    }
    	return quotaInfo;
    }

	@Override
	public List<Quota> countByQuotaKey(Criteria example) {
		return this.quotaMapper.countByQuotaKey(example);
	}
}