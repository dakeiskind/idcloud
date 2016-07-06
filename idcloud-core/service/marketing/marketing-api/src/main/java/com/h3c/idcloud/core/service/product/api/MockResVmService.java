package com.h3c.idcloud.core.service.product.api;
import com.h3c.idcloud.core.pojo.dto.res.ResOsSoftware;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResVdInst;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;

import java.util.List;

/**
 * 模拟资源请求接口
 * 
 * @author ChengQi
 *
 */
public interface MockResVmService {

	public ResInstResult createVm(ResVmInst vmInst);


	public ResInstResult reconfigVm(ResVmInst vmInst);

	public ResInstResult deleteVm(String vmResId);

	public ResInstResult createVd(ResVdInst resVdInst);

//	public ResInstResult applyFloatingIP(ResFloatingIpInst resFloatingIpInst) {
//		final ResInstResult instResult = new ResInstResult();
//		try {
//			Random random = new Random();
//			Integer resVdSid = random.nextInt(1000);
//			instResult.setStatus(true);
//			ResFloatingIp floatingIp  = new ResFloatingIp();
//			floatingIp.setResSid(resVdSid.toString());
//			instResult.setData(floatingIp);
//
//			Thread thread = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try{
//						Thread.sleep(5000);
//						floatingIpOpenHandlerImpl.operate(instResult);
//					} catch(Exception e) {
//						logger.error(e.getMessage(), e);
//					}
//				}
//			});
//			thread.start();
//
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		return instResult;
//	}
//
//	public ResInstResult createObjStorage(ResObjStoInst resVdInst) {
//		final ResInstResult instResult = new ResInstResult();
//		try {
//			Random random = new Random();
//			Integer resVdSid = random.nextInt(1000);
//			instResult.setStatus(true);
//			ResObjStorageInst resObjStorageInst = new ResObjStorageInst();
//			resObjStorageInst.setResInstSid(resVdSid.toString());
//			instResult.setData(resObjStorageInst);
//
//			Thread thread = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try{
//						Thread.sleep(5000);
//						storageOpenHandlerImpl.operate(instResult);
//					} catch(Exception e) {
//						logger.error(e.getMessage(), e);
//					}
//				}
//			});
//			thread.start();
//
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		return instResult;
//	}

	public ResInstResult expandVd(String resVdSid, String mgtObjSid, Long size) ;

	public  ResInstResult installSoftware(String resVmSid, List<ResOsSoftware> softwares);
}
