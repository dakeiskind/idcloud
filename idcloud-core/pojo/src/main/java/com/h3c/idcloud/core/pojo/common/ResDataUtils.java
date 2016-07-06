package com.h3c.idcloud.core.pojo.common;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.res.ResIp;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.system.MgtObjExt;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资源数据处理工具类
 *
 * @author ChengQi
 */
public class ResDataUtils {

	/**
	 * 根据资源池类型，从IP列表中查询出IP
	 *
	 * @param resIpList   the res ip list
	 * @param resPoolType the res pool type
	 * @return ip
	 */
	public static String getIp(List<ResIp> resIpList, String resPoolType) {
		if (resIpList == null || resIpList.isEmpty()) {
			return null;
		}
		String ipAddress = null;

		for (ResIp ip : resIpList) {
			if (resPoolType.equals(ip.getResPoolType())) {
				ipAddress = ip.getIpAddress();
				break;
			}
		}

		return ipAddress;
	}

	/**
	 * 根据资源池类型, 从IP列表中查询出网络Id
	 *
	 * @param resIpList   the res ip list
	 * @param resPoolType the res pool type
	 * @return net sid
	 */
	public static String getNetSid(List<ResIp> resIpList, String resPoolType) {
		if (resIpList == null || resIpList.isEmpty()) {
			return null;
		}
		String netSid = null;
		for (ResIp ip : resIpList) {
			if (resPoolType.equals(ip.getResPoolType())) {
				netSid = ip.getResNetworkSid();
				break;
			}
		}
		return netSid;
	}

	/**
	 * 从IP列表中查询出内部IP
	 *
	 * @param resIpList the res ip list
	 * @return intranet ip
	 */
	public static String getIntranetIp(List<ResIp> resIpList) {
		return getIp(resIpList, WebConstants.RES_TOPOLOGY_TYPE.PNI);
	}

	/**
	 * 从IP列表中查询出外部IP
	 *
	 * @param resIpList the res ip list
	 * @return internet ip
	 */
	public static String getInternetIp(List<ResIp> resIpList) {
		return getIp(resIpList, WebConstants.RES_TOPOLOGY_TYPE.PNE);
	}

	/**
	 * 从IP列表中查询出内部网络sid
	 *
	 * @param resIpList the res ip list
	 * @return intranet network sid
	 */
	public static String getIntranetNetworkSid(List<ResIp> resIpList) {
		return getNetSid(resIpList, WebConstants.RES_TOPOLOGY_TYPE.PNI);
	}

	/**
	 * 从IP列表中查询出外部网络sid
	 *
	 * @param resIpList the res ip list
	 * @return internet network sid
	 */
	public static String getInternetNetworkSid(List<ResIp> resIpList) {
		return getNetSid(resIpList, WebConstants.RES_TOPOLOGY_TYPE.PNE);
	}

	/**
	 * 从规格列表参数中获取制定规格名的值
	 *
	 * @param specName the spec name
	 * @param specs    the specs
	 * @return spec value
	 */
	public static String getSpecValue(String specName, List<Map<String, Object>> specs) {
		String value = null;
		for(Map<String, Object> spec : specs) {
			String name = (String)spec.get("name");
			if(specName.equals(name)) {
				value = (String)spec.get("value");
			}
		}
		return value;
	}

	/**
	 * 从规格列表参数中获取指定规格名的值
	 *
	 * @param specName      the spec name
	 * @param instanceSpecs the instance specs
	 * @return spec value from specs
	 */
	public static String getSpecValueFromSpecs(String specName, List<ServiceInstanceSpec> instanceSpecs) {
		String value = null;
		for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
			if(specName.equals(instanceSpec.getName())) {
				value = instanceSpec.getValue();
				break;
			}
		}
		return value;
	}

	/**
	 * 从规格列表参数中获取指定规格名的规格对象
	 *
	 * @param specName      the spec name
	 * @param instanceSpecs the instance specs
	 * @return spec object from specs
	 */
	public static ServiceInstanceSpec getSpecObjectFromSpecs(String specName, List<ServiceInstanceSpec> instanceSpecs) {
		ServiceInstanceSpec spec = null;
		for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
			if(specName.equals(instanceSpec.getName())) {
				spec = instanceSpec;
				break;
			}
		}
		return spec;
	}

	/**
	 * 根据资源id从资源拓扑中获取节点信息
	 *
	 * @param resTopologies 所有拓扑信息
	 * @param topologySid   the topology sid
	 * @return res topology
	 */
	public static ResTopology getResTopology(List<ResTopology> resTopologies, String topologySid) {
		ResTopology resTopologyRes = null;
		for (ResTopology resTopology : resTopologies) {		
			if(resTopology.getResTopologySid().equals(topologySid)) {
				resTopologyRes = resTopology;
			}
		}
		return resTopologyRes;
	}


	/**
	 * 获得 idc disk total size.
	 *
	 * @param diskAllSize the disk all size
	 * @return the idc disk total size
	 */
	public static Long getIdcDiskTotalSize(String diskAllSize) {
		String[] diskSizes = diskAllSize.split(",");
		Long totalSize = 0L;
		for (int i = 0; i < diskSizes.length; i++) {
			totalSize += Long.parseLong(diskSizes[i]);
		}
		return totalSize;
	}

	/**
	 * 获得 all ip.
	 *
	 * @param resVm the res vm
	 * @return the all ip
	 */
	public static String getAllIp(ResVm resVm) {
		List<ResIp> resIps = resVm.getResIpList();
		if(resIps == null) return "";
		List<String> ips = new ArrayList<String>();
		for(ResIp resIp : resIps) {
			ips.add(resIp.getIpAddress());
		}
		return StringUtils.join(ips, ",");
	}

	/**
	 * 获得 mgt obj ext value.
	 *
	 * @param attrKey       the attr key
	 * @param mgtObjExtList the mgt obj ext list
	 * @return the mgt obj ext value
	 */
	public static MgtObjExt getMgtObjExtValue(String attrKey, List<MgtObjExt> mgtObjExtList) {
		MgtObjExt resMgtObjExt = null;
		for(MgtObjExt mgtObjExt : mgtObjExtList) {
			if(mgtObjExt.getAttrKey().equals(attrKey)) {
				resMgtObjExt = mgtObjExt;
				break;
			}
		}
		return resMgtObjExt;
	}
}
