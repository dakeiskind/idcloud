package com.h3c.idcloud.core.service.res;

import com.google.common.collect.Lists;
import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcRouter;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResVmOptInst;
import com.h3c.idcloud.core.service.res.api.ResFloatingIpService;
import com.h3c.idcloud.core.service.res.api.ResKeypairsService;
import com.h3c.idcloud.core.service.res.api.ResNetworkService;
import com.h3c.idcloud.core.service.res.api.ResVdService;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.res.api.ResVpcRouterService;
import com.h3c.idcloud.core.service.res.provider.task.ResHostSyncTask;
import com.h3c.idcloud.infrastructure.common.cache.JedisUtil;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * com.h3c.idcloud.core.service.res
 *
 * @author Chaohong.Mao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context-test.xml")
public class ResServiceImplTest {

    /**
     * 预加载服务
     */
//    @Before
//    public void setup() {
//        // 使用父类方法暴露provider
//        super.setProvider(ResVmService.class);
//    }
    @Test
    public void createVm() {
        String vmSpec = "{\n" +
                "    \"region\": \"10\",\n" +
                "    \"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "    \"password\": \"\",\n" +
                "    \"hostname\": \"test\",\n" +
                "    \"instance\": [{\n" +
                "        \"instanceCategory\": \"idc-S\",\n" +
                "        \"cpu\": \"1\",\n" +
                "        \"memory\": \"1\"\n" +
                "    }],\n" +
                "    \"systemDisk\": [{\n" +
                "        \"systemDiskCategory\": \"cloud_efficiency\",\n" +
                "        \"systemDiskSize\": \"10\",\n" +
                "        \"systemDiskDevice\": \"/dev/xvda\"\n" +
                "    }],\n" +
                "    \"dataDisk\": [{\n" +
                "        \"dataDiskCategory\": \"cloud_ssd\",\n" +
                "        \"dataDiskSize\": \"10\",\n" +
                "        \"dataDiskSnapshot\": \"\",\n" +
                "        \"dataDiskDevice\": \"\",\n" +
                "        \"dataDiskDeletewithinstance\": \"true\",\n" +
                "        \"dataDiskInstanceId\": null\n" +
                "    }, {\n" +
                "        \"dataDiskCategory\": \"cloud_efficiency\",\n" +
                "        \"dataDiskSize\": \"20\",\n" +
                "        \"dataDiskSnapshot\": \"\",\n" +
                "        \"dataDiskDevice\": \"\",\n" +
                "        \"dataDiskDeletewithinstance\": \"true\",\n" +
                "        \"dataDiskInstanceId\": null\n" +
                "    }],\n" +
                "    \"networkType\": \"vpc\",\n" +
                "    \"networks\": [\"15914629-0127-11e6-9f86-5cb90105be61\"],\n" +
                "    \"bandwidth\": \"0\",\n" +
                "    \"os\": {\n" +
                "        \"imageType\": \"public\",\n" +
                "        \"imageId\": \"test-image\"\n" +
                "    },\n" +
                "    \"keyPair\": \"sk-j4wqqlkt\",\n" +
                "    \"securityGroup\": \"ccd98605-77ba-11e5-b6e5-005056a52fbf\"\n" +
                "}\n";
        ResCommonInst resCommonInst = new ResCommonInst();
        resCommonInst.setMgtObjSid(10022L);
        resCommonInst.setUserAccount("mch_test");
        resCommonInst.setUserPass("1");
        resCommonInst.setZoneId("12e0c825-3ff5-11e5-8c09-005056ba3c46");
        resCommonInst.setResSpecParam(vmSpec);
        ResVmService resVmService = SpringContextHolder.getBean("resVmService");
        try {
            resVmService.createVm(resCommonInst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTask() {
        ResHostSyncTask resHostSyncTask = SpringContextHolder.getBean("resHostSyncTask");
        resHostSyncTask.start("test-host");
    }

    @Test
    public void testMain() {
        JedisUtil.instance().set("test", "testmao");
        System.out.printf(JedisUtil.instance().get("test"));
    }

    @Test
    public void testCreateNet() {
        ResCommonInst resNetInst = new ResCommonInst();
        resNetInst.setMgtObjSid(10022L);
        resNetInst.setUserAccount("mch_test");
        resNetInst.setUserPass("1");
        resNetInst.setZoneId("12e0c825-3ff5-11e5-8c09-005056ba3c46");

        String param = "{\n" +
                "\"zone\":\"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\"vpcName\":\"test-vpc\",\n" +
                "\"vpcDescription\":\"test-vpc\",\n" +
                "\"vpcCidr\":\"10.10.0.0/16\",\n" +
                "\"subNet\":{\n" +
                "     \"subNetName\":\"test-network\",\n" +
                "     \"subNetDescription\":\"test-network\",\n" +
                "     \"subNetCidr\":\"10.10.191.0/24\"\n" +
                "   }" +
                "}";
        resNetInst.setResSpecParam(param);
        ResNetworkService resNetworkService = SpringContextHolder.getBean("resNetworkService");
        String subnetid = null;
        try {
            subnetid = resNetworkService.createNetworkWithSub(resNetInst);
//            Thread.sleep(60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(subnetid);
    }

    @Test
    public void operateVmTest() {
        ResVmOptInst resVmOptInst = new ResVmOptInst();
        resVmOptInst.setMgtObjSid(10022L);
        resVmOptInst.setUserAccount("mch_test");
        resVmOptInst.setUserPass("1");
        resVmOptInst.setZoneId("12e0c825-3ff5-11e5-8c09-005056ba3c46");

        resVmOptInst.setResVmSids(Lists.newArrayList("644e0245-f4b3-11e5-a4f0-5cb90105be61"));
        resVmOptInst.setOpt(WebConstants.VmOperation.RESUME);
        ResVmService resVmService = SpringContextHolder.getBean("resVmService");
        try {
            resVmService.mulitOp(resVmOptInst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void operateVmTest2() {
        ServiceBaseInput baseInput = new ServiceBaseInput();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
//        baseInput.setZoneId("12e0c825-3ff5-11e5-8c09-005056ba3c46");

        ResVmService resVmService = SpringContextHolder.getBean("resVmService");
        String resVmSid = "644e0245-f4b3-11e5-a4f0-5cb90105be61";
        String optType = WebConstants.VmOperation.RESUME;
        String rebootType = null;
        try {
            resVmService.operateVm(resVmSid, baseInput, optType, rebootType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findSubnetByNet() {
        ResNetworkService resNetworkService = SpringContextHolder.getBean("resNetworkService");
        System.out.println(resNetworkService.findSubnetByNetwork("test-vpc"));
    }

    @Test
    public void getWebConsole() {
        ServiceBaseInput baseInput = new ServiceBaseInput();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        String resVmSid = "09540b06-f73e-11e5-a4f0-5cb90105be61";
        ResVmService resVmService = SpringContextHolder.getBean("resVmService");
        System.out.println(resVmService.getWebConsole(resVmSid, baseInput));
    }

    @Test
    public void createkeypairs() {
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setResSpecParam("{\"keypairsName\" : \"testKeypairs22222\", \"description\": \"test\"}");
        ResKeypairsService resKeypairsService = SpringContextHolder.getBean("resKeypairsService");
        String privateKey = resKeypairsService.createKeypairs(baseInput);
        System.out.println(privateKey);
    }

    @Test
    public void applyFloatingIp() {
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(
                "{\"region\":\"10\",\"zone\":\"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\"eip_bandwidth\":\"1\"}");
        ResFloatingIpService resFloatingIpService = SpringContextHolder.getBean("resFloatingIpService");
        ResInstResult resInstResult = resFloatingIpService.applyFloatingIP(baseInput);
        System.out.println(JsonUtil.toJson(resInstResult));
    }

    @Test
    public void deleteFloatingIp() {
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(
                "{\"region\":\"10\",\"zone\":\"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\"resSid\":\"9d112b67-fe18-11e5-9171-5cb90105be61\"}");
        ResFloatingIpService resFloatingIpService = SpringContextHolder.getBean("resFloatingIpService");
        ResInstResult resInstResult = resFloatingIpService.deleteFloatingIP(baseInput);
        System.out.println(JsonUtil.toJson(resInstResult));
    }

    @Test
    public void attachFloatingIp() {
        String params = "{\n" +
                "\"region\": \"10\",\n" +
                "\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\"floatingIpSid\": \"7f9f2f57-0129-11e6-9f86-5cb90105be61\",\n" +
                "\"subNetSid\": \"15914629-0127-11e6-9f86-5cb90105be61\",\n" +
                "\"vmIp\": \"10.10.197.3\",\n" +
                "\"resVmSid\": \"24436fca-0129-11e6-9f86-5cb90105be61\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResFloatingIpService resFloatingIpService = SpringContextHolder.getBean("resFloatingIpService");
        ResInstResult resInstResult = resFloatingIpService.attachFloatingIp(baseInput);
        System.out.println(JsonUtil.toJson(resInstResult));
    }

    @Test
    public void dettachFloatingIp() {
        String params = "{\n" +
                "\"region\": \"10\",\n" +
                "\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\"floatingIpSid\": \"7f9f2f57-0129-11e6-9f86-5cb90105be61\",\n" +
                "\"resVmSid\": \"24436fca-0129-11e6-9f86-5cb90105be61\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResFloatingIpService resFloatingIpService = SpringContextHolder.getBean("resFloatingIpService");
        ResInstResult resInstResult = resFloatingIpService.detachFloatingIp(baseInput);
        System.out.println(JsonUtil.toJson(resInstResult));
    }

    @Test
    public void createRouter() {
        String params = "{\n" +
                "\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\"routerName\": \"test-router-222\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResVpcRouterService resVpcRouterService = SpringContextHolder.getBean("resVpcRouterService");
        ResVpcRouter vpcRouter = resVpcRouterService.createVpcRouter(baseInput);
        System.out.println(JsonUtil.toJson(vpcRouter));
    }

    @Test
    public void deleteRouter() {
        String params = "{\n" +
                "\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\"resRouterSid\": \"fe6a61d2-0531-11e6-9f86-5cb90105be61\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResVpcRouterService resVpcRouterService = SpringContextHolder.getBean("resVpcRouterService");
        int result = resVpcRouterService.removeVpcRouter(baseInput);
        System.out.println(result);
    }

    @Test
    public void attachRouter() {
        String params = "{\n" +
                "\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\"resRouterSid\": \"996e9b13-0618-11e6-9f86-5cb90105be61\",\n" +
                "\"resNetworkSid\":\"0a391c9e-06cb-11e6-9f86-5cb90105be61\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResVpcRouterService resVpcRouterService = SpringContextHolder.getBean("resVpcRouterService");
        int result = resVpcRouterService.attachSubNetWithRouter(baseInput);
        System.out.println(result);
    }

    @Test
    public void dettachRouter() {
        String params = "{\n" +
                "\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\"resRouterSid\": \"996e9b13-0618-11e6-9f86-5cb90105be61\",\n" +
                "\"resNetworkSid\":\"0a391c9e-06cb-11e6-9f86-5cb90105be61\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResVpcRouterService resVpcRouterService = SpringContextHolder.getBean("resVpcRouterService");
        int result = resVpcRouterService.dettachSubNetWithRouter(baseInput);
        System.out.println(result);
    }

    @Test
    public void createRouterGateway() {
        String params = "{\n" +
                "\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\"resRouterSid\": \"996e9b13-0618-11e6-9f86-5cb90105be61\",\n" +
                "\"resExtNetworkSid\":\"test-floating\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResVpcRouterService resVpcRouterService = SpringContextHolder.getBean("resVpcRouterService");
        int result = resVpcRouterService.attachExtNetWithRouter(baseInput);
        System.out.println(result);
    }

    @Test
    public void createVd() {
        String params = "{\n" +
                "     \"region\": \"10\",\n" +
                "     \"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "     \"dataDisk\": [{\n" +
                "         \"dataDiskCategory\": \"cloud_ssd\",\n" +
                "         \"dataDiskSize\": \"1\"\n" +
                "     }]\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        baseInput.setInstId("qewasdqwe");
        ResVdService resVdService = SpringContextHolder.getBean("resVdService");
        ResInstResult vd = resVdService.createVd(baseInput);
        System.out.println(JsonUtil.toJson(vd));
    }

    @Test
    public void attachVd() {
        String params = "{\n" +
                "     \"region\": \"10\",\n" +
                "     \"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "     \"resVdSid\": \"f14d8baa-07a7-11e6-b5da-5cb90105be61\",\n" +
                "     \"resVmSid\": \"24436fca-0129-11e6-9f86-5cb90105be61\",\n" +
                "     \"mountPath\": \"/dev/vdb\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResVdService resVdService = SpringContextHolder.getBean("resVdService");
        ResInstResult vd = resVdService.attachVd(baseInput);
        System.out.println(JsonUtil.toJson(vd));
    }

    @Test
    public void dettachVd() {
        String params = "{\n" +
                "     \"region\": \"10\",\n" +
                "     \"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "     \"resVdSid\": \"f14d8baa-07a7-11e6-b5da-5cb90105be61\",\n" +
                "     \"resVmSid\": \"24436fca-0129-11e6-9f86-5cb90105be61\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResVdService resVdService = SpringContextHolder.getBean("resVdService");
        ResInstResult vd = resVdService.detachVd(baseInput);
        System.out.println(JsonUtil.toJson(vd));
    }

    @Test
    public void deleteVd() {
        String params = "{\n" +
                "     \"region\": \"10\",\n" +
                "     \"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "     \"resVdSid\": \"f14d8baa-07a7-11e6-b5da-5cb90105be61\"\n" +
                "}";
        ResCommonInst baseInput = new ResCommonInst();
        baseInput.setMgtObjSid(10022L);
        baseInput.setUserAccount("mch_test");
        baseInput.setUserPass("1");
        baseInput.setResSpecParam(params);
        ResVdService resVdService = SpringContextHolder.getBean("resVdService");
        ResInstResult vd = resVdService.deleteVd(baseInput);
        System.out.println(JsonUtil.toJson(vd));
    }

    @Test
    public void scanVe() {
        ResVeService resVeService = SpringContextHolder.getBean("resVeService");
        ResVe resVe = resVeService.selectByPrimaryKey("2d70f5cc-77c6-11e5-abf0-005056ba3c46");
        try {
            resVeService.findAllByVe(resVe);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
