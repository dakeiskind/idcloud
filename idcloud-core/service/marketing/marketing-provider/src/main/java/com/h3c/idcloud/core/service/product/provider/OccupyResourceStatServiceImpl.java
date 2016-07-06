package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.OccupyResourceStatMapper;
import com.h3c.idcloud.core.pojo.dto.product.OccupyResourceStat;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.service.product.api.MgtObjResService;
import com.h3c.idcloud.core.service.product.api.OccupyResourceStatService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResHostService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class OccupyResourceStatServiceImpl implements OccupyResourceStatService {
	
    @Autowired
    private OccupyResourceStatMapper occupyResourceStatMapper;
    
    @Autowired
    private MgtObjResService mgtObjResService;
    
    @Reference(version = "1.0.0")
    private ResHostService resHostService;
    
    @Autowired
    private ServiceInstanceService instanceService;

	/** 虚拟机Service */
//	@Autowired         ----wsl
//	private ResBizVmService resBizVmService;
	
//	@Autowired      ------wsl
//	private ResStorageService resStorageService;

    @Reference(version = "1.0.0")
	private MgtObjService mgtObjService;
	
    private static final Logger logger = LoggerFactory.getLogger(OccupyResourceStatServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.occupyResourceStatMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public OccupyResourceStat selectByPrimaryKey(Long occupyResourceSid) {
        return this.occupyResourceStatMapper.selectByPrimaryKey(occupyResourceSid);
    }

    public List<OccupyResourceStat> selectByParams(Criteria example) {
        return this.occupyResourceStatMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long occupyResourceSid) {
        return this.occupyResourceStatMapper.deleteByPrimaryKey(occupyResourceSid);
    }

    public int updateByPrimaryKeySelective(OccupyResourceStat record) {
        return this.occupyResourceStatMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(OccupyResourceStat record) {
        return this.occupyResourceStatMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.occupyResourceStatMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(OccupyResourceStat record, Criteria example) {
        return this.occupyResourceStatMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(OccupyResourceStat record, Criteria example) {
        return this.occupyResourceStatMapper.updateByParams(record, example.getCondition());
    }

    public int insert(OccupyResourceStat record) {
        return this.occupyResourceStatMapper.insert(record);
    }

    public int insertSelective(OccupyResourceStat record) {
        return this.occupyResourceStatMapper.insertSelective(record);
    }

	@Override
	public List<OccupyResourceStat> selectByParamsSum(Criteria example) {
		return this.occupyResourceStatMapper.selectByParamsSum(example);
	}

	@Override
	public List<OccupyResourceStat> selectByParamsSumByDate(Criteria example) {
		return this.occupyResourceStatMapper.selectByParamsSumByDate(example);
	}
	
	@Override
	public List<OccupyResourceStat> selectUsageByParamsSum(Criteria example) {
		return this.occupyResourceStatMapper.selectUsageByParamsSum(example);
	}

	@Override
	public List<OccupyResourceStat> selectUsageByParamsForRank(Criteria example) {
		return this.occupyResourceStatMapper.selectUsageByParamsForRank(example);
	}

	@Override
	public void insertOccupyResStat() {
		Criteria example = new Criteria();
		example.put("level", "0");
		List<MgtObj> fbizs = mgtObjService.selectByParams(example);
//		if(!CollectionUtils.isEmpty(fbizs)){
//			for (MgtObj biz : fbizs) {
//				example = new Criteria();
//				example.put("resBizSid", biz.getMgtObjSid());
//				ResHost hosts = this.resHostService.statisticalBizOfHost(example);
//				ResBizVm vms = this.resBizVmService.statisticalBizOfVM(biz.getMgtObjSid());
//				ResStorage storages = this.resStorageService.statisticalBizOfStorage(example);
//
//				OccupyResourceStat ors1 = new OccupyResourceStat();
//				ors1.setBizSid(biz.getMgtObjSid()+"");
//				ors1.setBizName(biz.getName());
//				ors1.setStatDate(new Date());
//
//				OccupyResourceStat ors2 = new OccupyResourceStat();
//				ors2.setBizSid(biz.getMgtObjSid()+"");
//				ors2.setBizName(biz.getName());
//				ors2.setStatDate(new Date());
//
//				OccupyResourceStat ors3 = new OccupyResourceStat();
//				ors3.setBizSid(biz.getMgtObjSid()+"");
//				ors3.setBizName(biz.getName());
//				ors3.setStatDate(new Date());
//
//				ors1.setResType("1");
//				ors1.setResTotalValue(hosts.getCpuCores()==null?0:hosts.getCpuCores().doubleValue());
//
//				ors2.setResType("2");
//				ors2.setResTotalValue(hosts.getMemorySize()==null?0:hosts.getMemorySize().doubleValue());
//
//				ors3.setResType("3");
//				ors3.setResTotalValue(storages.getTotalCapacity()==null?0:storages.getTotalCapacity().doubleValue());
//
//				ors1.setResOccupyValue(vms.getCpuCores()==null?0:vms.getCpuCores().doubleValue());
//				ors2.setResOccupyValue(vms.getMemorySize()==null?0:vms.getMemorySize().doubleValue());
//				ors3.setResOccupyValue(storages.getAllotCapacity()==null?0:storages.getAllotCapacity().doubleValue());
//
//				this.occupyResourceStatMapper.insertSelective(ors1);
//				this.occupyResourceStatMapper.insertSelective(ors2);
//				this.occupyResourceStatMapper.insertSelective(ors3);
//			}
//		}
	}
	
}