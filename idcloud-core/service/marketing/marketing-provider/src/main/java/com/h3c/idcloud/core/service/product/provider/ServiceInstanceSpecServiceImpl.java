package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstanceSpecMapper;
import com.h3c.idcloud.core.pojo.common.ResDataUtils;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Service(version = "1.0.0")
@Component
public class ServiceInstanceSpecServiceImpl implements ServiceInstanceSpecService {
    @Autowired
    private ServiceInstanceSpecMapper serviceInstanceSpecMapper;

	@Autowired
    private ServiceInstanceService serviceInstanceService;

    private static final Logger logger = LoggerFactory.getLogger(ServiceInstanceSpecServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.serviceInstanceSpecMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ServiceInstanceSpec selectByPrimaryKey(Long specSid) {
        return this.serviceInstanceSpecMapper.selectByPrimaryKey(specSid);
    }

    public List<ServiceInstanceSpec> selectByParams(Criteria example) {
        return this.serviceInstanceSpecMapper.selectByParams(example);
    }

    public List<ServiceInstanceSpec> selectByParamsNewVersion(Criteria example) {
        return this.serviceInstanceSpecMapper.selectByParamsNewVersion(example);
    }

    public List<ServiceInstanceSpec> selectByParamsLatest(Criteria example) {
        return this.serviceInstanceSpecMapper.selectByParamsLatest(example);
    }

    public int deleteByPrimaryKey(Long specSid) {
        return this.serviceInstanceSpecMapper.deleteByPrimaryKey(specSid);
    }

    public int updateByPrimaryKeySelective(ServiceInstanceSpec record) {
        return this.serviceInstanceSpecMapper.updateByPrimaryKeySelective(record);
    }

    public int insertSelective(ServiceInstanceSpec record) {
        return this.serviceInstanceSpecMapper.insertSelective(record);
    }

	@Override
	public List<ServiceInstanceSpec> selectByInstanceSid(Long instanceSid) {
		return this.serviceInstanceSpecMapper.selectByInstanceSid(instanceSid);
	}
	
    public int deleteByParams(Criteria example) {
        return this.serviceInstanceSpecMapper.deleteByParams(example);
    }

	@Override
	public List<ServiceInstanceSpec> selectByInstanceSpecBySid(Long instanceSid) {
		return serviceInstanceSpecMapper.selectByInstanceSpecBySid(instanceSid);
	}

	@Override
	public Long selectByInstanceSpecMaxVersion(Long instanceSid) {
		return serviceInstanceSpecMapper.selectByInstanceSpecMaxVersion(instanceSid);
	}

	@Override
	public List<ServiceInstanceSpec> selectByInstanceSpecByVersion(
			Long instanceSid, String status, Long version) {
		return serviceInstanceSpecMapper.selectByInstanceSpecByVersion(instanceSid, status, version);
	}

	@Override
	public int updateByName(Long instanceSid, String name, String value) {
		return serviceInstanceSpecMapper.updateByName(instanceSid, name, value);
	}

	@Override
	public void modifyInstanceSpecToValid(Long instanceSid) {
		//获取实例规格的最大版本号
		Long maxVersion = serviceInstanceSpecMapper.selectByInstanceSpecMaxVersion(instanceSid);
		//获取当前版本中最新规格
		List<ServiceInstanceSpec> newInstaceSpecs = serviceInstanceSpecMapper.selectByInstanceSpecByVersion(instanceSid, null, maxVersion);
		//更新实例规格状态为有效
		for(ServiceInstanceSpec spec : newInstaceSpecs) {
			spec.setStatus(WebConstants.SpecStatus.valid);
			serviceInstanceSpecMapper.updateByPrimaryKeySelective(spec);
		}
	}
	
	@Override
	public void modifyInstanceSpecToChanging(Long instanceSid) {
		//获取实例规格的最大版本号
		Long maxVersion = serviceInstanceSpecMapper.selectByInstanceSpecMaxVersion(instanceSid);
		//获取当前版本中未生效规格
		List<ServiceInstanceSpec> newInstaceSpecs = serviceInstanceSpecMapper.selectByInstanceSpecByVersion(instanceSid, null, maxVersion);
		//更新实例规格状态为有效
		for(ServiceInstanceSpec spec : newInstaceSpecs) {
			spec.setStatus(WebConstants.SpecStatus.changing);
			serviceInstanceSpecMapper.updateByPrimaryKeySelective(spec);
		}
	}
	
	@Override
	public void modifyInstanceSpecToInvalid(Long instanceSid) {
		//获取实例规格的最大版本号
		Long maxVersion = serviceInstanceSpecMapper.selectByInstanceSpecMaxVersion(instanceSid);
		//获取当前版本中最新规格
		List<ServiceInstanceSpec> newInstaceSpecs = serviceInstanceSpecMapper.selectByInstanceSpecByVersion(instanceSid, null, maxVersion);
		//更新实例规格状态为有效
		for(ServiceInstanceSpec spec : newInstaceSpecs) {
			spec.setStatus(WebConstants.SpecStatus.invalid);
			serviceInstanceSpecMapper.updateByPrimaryKeySelective(spec);
		}
	}
	
	@Override
	public List<ServiceInstanceSpec> selectByInstanceSpecForDataDiskBySid(Long instanceSid) {
		StringBuffer dataDiskByComma = new StringBuffer();
		StringBuffer sysDiskByComma = new StringBuffer();
		//找出所有系统盘、数据盘实例
		Criteria example = new Criteria();
		example.put("parentInstanceSid", instanceSid.toString());
		example.put("instanceSid", instanceSid.toString());
		List<ServiceInstance> serviceInstanceDiskList = this.serviceInstanceService.selectByParams(example);
		int count = 0;
		int count1 = 0;
		//找出数据盘的磁盘容量规格
		for (ServiceInstance serviceInstanceDisk : serviceInstanceDiskList) {
			List<ServiceInstanceSpec> serviceInstanceSpecDiskList = this.selectByInstanceSpecBySid(serviceInstanceDisk.getInstanceSid());
			boolean isDataDisk = false;
			boolean isSysDisk = false;
			String diskSize = "";
			for (ServiceInstanceSpec serviceInstanceSpecDisk : serviceInstanceSpecDiskList) {
				if (WebConstants.InstanceSpecType.STORAGE_PURPOSE.equals(serviceInstanceSpecDisk.getName())) {
					if (WebConstants.StoragePurpose.DATA_DISK.equals(serviceInstanceSpecDisk.getValue())) {
						isDataDisk = true;
					}
					if (WebConstants.StoragePurpose.SYSTEM_DISK.equals(serviceInstanceSpecDisk.getValue())) {
						isSysDisk = true;
					}
				}
				if (WebConstants.InstanceSpecType.DISK_SIZE.equals(serviceInstanceSpecDisk.getName())) {
					diskSize = serviceInstanceSpecDisk.getValue();
				}
			}
			
			if (isDataDisk) {
				if (count == 0) {
					dataDiskByComma.append(diskSize + "GB");
				}
				else {
					dataDiskByComma.append("," +diskSize + "GB");
				}
				count++;
			}
			if (isSysDisk) {
				if (count1 == 0) {
					sysDiskByComma.append("系统盘:"+diskSize+"GB");
				}
				else {
					sysDiskByComma.append("," +diskSize + "GB");
				}
				count1++;
			}
		}
		if(dataDiskByComma.length()>0){
			sysDiskByComma.append(";数据盘:"+dataDiskByComma);
		}
		//使用多个以逗号分割的数据盘磁盘容量规格替换虚拟机实例中的数据盘规格
		List<ServiceInstanceSpec> sisList = new ArrayList<ServiceInstanceSpec>();
		List<ServiceInstanceSpec> serviceInstanceSpecList = this.serviceInstanceSpecMapper.selectByInstanceSpecBySid(instanceSid);
		String cpuCores = ResDataUtils.getSpecValueFromSpecs(WebConstants.InstanceSpecType.CPU, serviceInstanceSpecList);
		String memrory = ResDataUtils.getSpecValueFromSpecs(WebConstants.InstanceSpecType.MEMORY, serviceInstanceSpecList);

		ServiceInstanceSpec sis = new ServiceInstanceSpec();
		sis.setDescription("配置");
		sis.setValueText("CPU:" + (StringUtil.isNullOrEmpty(cpuCores)?"":cpuCores) + "核;内存:" + (StringUtil.isNullOrEmpty(memrory)?"":memrory) + "GB;存储("+ sysDiskByComma.toString()+")");
		sisList.add(sis);
		for (ServiceInstanceSpec serviceInstanceSpec : serviceInstanceSpecList) {
			if (!WebConstants.InstanceSpecType.DATA_DISK.equals(serviceInstanceSpec.getName())
					&&!WebConstants.InstanceSpecType.SYSTEM_DISK.equals(serviceInstanceSpec.getName())
					&&!WebConstants.InstanceSpecType.CPU.equals(serviceInstanceSpec.getName())
					&&!WebConstants.InstanceSpecType.MEMORY.equals(serviceInstanceSpec.getName())
					&&!WebConstants.InstanceSpecType.NETS.equals(serviceInstanceSpec.getName())
					){
				serviceInstanceSpec.setValueText(serviceInstanceSpec.getValue());
				sisList.add(serviceInstanceSpec);
			}
		}
		return sisList;
	}

	@Override
	public Long selectValidInstanceSpecByVersion( Long instanceSid) {
		return serviceInstanceSpecMapper.selectValidInstanceSpecByVersion(instanceSid);
	}

	@Override
	public boolean checkInstanceValid(Long instanceSid) {
		boolean result = false;
		//获取实例规格的最大版本号
		Long diskMaxVersion = selectByInstanceSpecMaxVersion(instanceSid);
		//获取当前版本中未生效Vm规格
		List<ServiceInstanceSpec> newDiskInstaceSpec = selectByInstanceSpecByVersion(instanceSid, null, diskMaxVersion);
		//判断最大版本的规格是否是有效的
		for (ServiceInstanceSpec si : newDiskInstaceSpec) {
			if(si.getName().equals("DISK_SIZE")){
				//如果是有效的
				if(si.getStatus()==null||si.getStatus().equals(WebConstants.SpecStatus.valid)){
					//判断新值是不是0，是0表示已删除，不再页面上显示
					if(si.getValue()!=null&&!si.getValue().equals("0")){
						result = true;
					}
				}
				//如果是无效，则去取最大的有效版本
				else{
					//最大的有效规格版本号
					Long validDiskSpec = selectValidInstanceSpecByVersion(instanceSid);
					//如果最大有效版本是null，说明是新加的磁盘，所以没有以往的版本
					if(validDiskSpec!=null){
						//获取上一个版本有效的Vm规格
						List<ServiceInstanceSpec> oldDiskInstaceSpec = selectByInstanceSpecByVersion(instanceSid, null, validDiskSpec);
						if(oldDiskInstaceSpec!=null&&oldDiskInstaceSpec.size()!=0){
							result = true;
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public Long selectValidSpecByInstanceAndVersion(Criteria criteria) {
		return serviceInstanceSpecMapper.selectValidSpecByInstanceAndVersion(criteria);
	}
	
	@Override
	public Long selectVmDiskTotalSize(Long instanceSid) {
		return serviceInstanceSpecMapper.selectVmDiskTotalSize(instanceSid);
	}
}