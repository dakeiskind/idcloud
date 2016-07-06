package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.res.ResIp;
import com.h3c.idcloud.core.pojo.dto.res.ResOsSoftware;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResVdInst;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
import com.h3c.idcloud.core.service.product.api.MockResVmService;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Apple on 2016/2/23.
 */
@Service(version = "1.0.0")
@Component
public class MockResVmServiceImpl implements MockResVmService {

    @Autowired
    @Qualifier("vmOpenHandlerImpl")
    private ResourceRequestHanlder vmOpenHanlder;

    @Autowired
    @Qualifier("vmChangeHandlerImpl")
    private ResourceRequestHanlder vmChangeHanlder;

    @Autowired
    @Qualifier("vmDeleteHandlerImpl")
    private ResourceRequestHanlder vmDeleteHanlder;

    @Autowired
    @Qualifier("storageOpenHandlerImpl")
    private ResourceRequestHanlder storageOpenHandlerImpl;

    @Autowired
    @Qualifier("floatingIpOpenHandlerImpl")
    private ResourceRequestHanlder floatingIpOpenHandlerImpl;

    @Autowired
    @Qualifier("vdExpandHandlerImpl")
    private ResourceRequestHanlder vdExpandHandlerImpl;

    @Autowired
    @Qualifier("softwareAutoInstallHandlerImpl")
    private ResourceRequestHanlder softwareAutoInstallHandlerImpl;

    private static final Logger logger = LoggerFactory
            .getLogger(MockResVmService.class);

    public ResInstResult createVm(ResVmInst vmInst) {
        final ResInstResult instResult = new ResInstResult();
        try {
            Random random = new Random();
            Integer resSid = random.nextInt(1000);
            instResult.setStatus(true);
            ResVm resVm = new ResVm();
            resVm.setResVmSid("040fb476-2149-11e5-911b-000c2955d99a");
            List<ResVd> vdList = new ArrayList<ResVd>();
            ResVd resVd1 = new ResVd();
            resVd1.setDeviceName("sysDisk");
            resVd1.setResVdSid("1");
            vdList.add(resVd1);
            ResVd resVd2 = new ResVd();
            resVd2.setDeviceName("2");
            resVd2.setResVdSid("2");
            vdList.add(resVd2);
            resVm.setResVdList(vdList);
            instResult.setReSend(false);
            List<ResOsSoftware> softwares = vmInst.getSoftwares();
            for(ResOsSoftware software : softwares) {
                software.setCanAutoDeploy(false);
                software.setStatus(WebConstants.OsSoftwareStatus.WAITING);
            }
            resVm.setSoftwares(softwares);
            instResult.setData(resVm);
            if(instResult.getStatus()) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(5000);
                            instResult.setReSend(false);
//							instResult.setMessage("磁盘存储空间不足!");
                            instResult.setStatus(true);
                            vmOpenHanlder.operate(instResult);
                        } catch(Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                });
                thread.start();
            }


        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return instResult;
    }


    public ResInstResult reconfigVm(ResVmInst vmInst) {
        final ResInstResult instResult = new ResInstResult();
        try {
            instResult.setStatus(true);
            ResVm resVm = new ResVm();
            resVm.setResVmSid(vmInst.getResVmInstId());
            List<ResVd> vdList = new ArrayList<ResVd>();
            List<ResVdInst> vds = vmInst.getDataDisks();

            for (ResVdInst vd : vds) {
                ResVd resVd = new ResVd();
//				resVd.setResVdSid("2138c8ac-2127-11e5-911b-000c2955d99a");
                if(vd.getResVdInstId() != null) {
                    resVd.setResVdSid(vd.getResVdInstId());
                } else {
                    Random random = new Random();
                    Integer resSid = random.nextInt(1000);
                    resVd.setResVdSid("2138c8ac-2127-11e5-911b-000c2955d99a");
                }
                resVd.setVdName(vd.getResVdInstName());
                resVd.setOperate(vd.getOperate());
                vdList.add(resVd);
            }
            resVm.setResVdList(vdList);


//			List<ResIp> netList = new ArrayList<ResIp>();
//			ResIp resIp1 = new ResIp();
//			resIp1.setIpAddress("192.168.1.100");
//			resIp1.setResPoolType(WebConstants.RES_TOPOLOGY_TYPE.PNI);
//			netList.add(resIp1);
//
//			ResIp resIp2 = new ResIp();
//			resIp2.setIpAddress("120.210.211.100");
//			resIp2.setResPoolType(WebConstants.RES_TOPOLOGY_TYPE.PNE);
//			netList.add(resIp2);
//
//			resVm.setResIpList(netList);

//			instResult.setMessage("磁盘存储空间不足!");
            instResult.setData(resVm);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(5000);
                        instResult.setReSend(true);
                        instResult.setStatus(false);
                        vmChangeHanlder.operate(instResult);
                    } catch(Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            thread.start();

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return instResult;
    }

    public ResInstResult deleteVm(String vmResId) {
        final ResInstResult instResult = new ResInstResult();
        try {
            instResult.setStatus(true);
            ResVm resVm = new ResVm();
            resVm.setResVmSid(vmResId);
            List<ResVd> vdList = new ArrayList<ResVd>();
            ResVd resVd1 = new ResVd();
            resVd1.setDeviceName("sysDisk");
            resVd1.setResVdSid("1");
            vdList.add(resVd1);
            ResVd resVd2 = new ResVd();
            resVd2.setDeviceName("2");
            resVd2.setResVdSid("2");
            vdList.add(resVd2);
            resVm.setResVdList(vdList);


            List<ResIp> netList = new ArrayList<ResIp>();
            ResIp resIp1 = new ResIp();
            resIp1.setIpAddress("192.168.1.100");
            resIp1.setResPoolType(WebConstants.RES_TOPOLOGY_TYPE.PNI);
            netList.add(resIp1);

            ResIp resIp2 = new ResIp();
            resIp2.setIpAddress("120.210.211.100");
            resIp2.setResPoolType(WebConstants.RES_TOPOLOGY_TYPE.PNE);
            netList.add(resIp2);

            resVm.setResIpList(netList);

//			instResult.setMessage("磁盘存储空间不足!");
            instResult.setData(resVm);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(5000);
                        instResult.setReSend(true);
                        instResult.setStatus(false);
                        vmDeleteHanlder.operate(instResult);
                    } catch(Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            thread.start();

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return instResult;
    }

    public ResInstResult createVd(ResVdInst resVdInst) {
        final ResInstResult instResult = new ResInstResult();
        try {
            Random random = new Random();
            Integer resVdSid = random.nextInt(1000);
            instResult.setStatus(true);
            ResVd resVd = new ResVd();
            resVd.setDeviceName("sysDisk");
            resVd.setResVdSid(resVdSid.toString());
            instResult.setData(resVd);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(5000);
                        storageOpenHandlerImpl.operate(instResult);
                    } catch(Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            thread.start();

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return instResult;
    }

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

    public ResInstResult expandVd(String resVdSid, String mgtObjSid, Long size) {
        final ResInstResult instResult = new ResInstResult();
        try {
            ResVd resVd = new ResVd();
            resVd.setResVdSid(resVdSid);
            instResult.setStatus(true);
            instResult.setData(resVd);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(5000);
                        vdExpandHandlerImpl.operate(instResult);
                    } catch(Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            thread.start();

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return instResult;
    }

    public  ResInstResult installSoftware(String resVmSid, List<ResOsSoftware> softwares) {
        final ResInstResult instResult = new ResInstResult();
        try {
            ResVm resVm = new ResVm();
            resVm.setResVmSid(resVmSid);
            instResult.setStatus(true);

            for(ResOsSoftware software : softwares) {
                software.setCanAutoDeploy(true);
                software.setStatus(WebConstants.OsSoftwareStatus.EXCEPTION);
            }
            resVm.setSoftwares(softwares);
            instResult.setData(resVm);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(5000);
                        softwareAutoInstallHandlerImpl.operate(instResult);
                    } catch(Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            thread.start();

        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return instResult;
    }
}
