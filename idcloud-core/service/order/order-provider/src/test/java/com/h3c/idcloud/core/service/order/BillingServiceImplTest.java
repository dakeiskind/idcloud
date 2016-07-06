package com.h3c.idcloud.core.service.order;

import com.h3c.idcloud.core.persist.charge.dao.BillingPlanSpecMapper;
import com.h3c.idcloud.core.persist.order.dao.OrderDetailMapper;
import com.h3c.idcloud.core.persist.order.dao.OrderMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPlanSpec;
import com.h3c.idcloud.core.pojo.dto.charge.Deposite;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.charge.api.BillingPlanService;
import com.h3c.idcloud.core.service.charge.api.PlatformOnlineRechargeOpService;
import com.h3c.idcloud.core.service.charge.provider.observable.PaymentObservable;
import com.h3c.idcloud.core.service.order.provider.observable.OrderPayObservable;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.ClassLoaderUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import javafx.util.Callback;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

/**
 * com.h3c.idcloud.core.service.res
 *
 * @author swq
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {
//                "classpath:META-INF/spring/idc-spring-persistance.xml",
                "classpath:spring-context-test.xml"
        }
)
@Transactional
@TransactionConfiguration(
        transactionManager = "transactionManager",
        defaultRollback = false
)
public class BillingServiceImplTest {

    Logger logger = Logger.getLogger(this.getClass());



    @Autowired
    BillingPlanService billingPlanService;

    @Autowired
    BillingAccountService billingAccountService;

    @Autowired
    PlatformOnlineRechargeOpService platformOnlineRechargeOpService;



    @Test
    public void testPlatformOnlineRechargeOneStep(){
        Deposite deposite = new Deposite();
        deposite.setAccountSid(46L);
        deposite.setUserSid(1725L);
        deposite.setChannel(WebConstants.CHARGE_CHANNEL.ZHICHONG);
        deposite.setAmountDeposited(new BigDecimal(10000L));
        deposite.setAmountReceived(new BigDecimal(10000L));
        Long sid = platformOnlineRechargeOpService.saveDepositeRecordInfo(deposite);
        System.out.println("------------"+sid);
    }

    @Test
    public void testPlatformOnlineRechargeTwoStep(){
//        Map<String,String> rechareMap = platformOnlineRechargeOpService.executeDepositeOperation(1002L,"SWQ10000000001");
//        System.out.println("------------"+rechareMap.toString());

        PaymentObservable payObservable = PaymentObservable.getPayObservable();
        Map<String,String> payInfoMap = new HashMap<>();
        payInfoMap.put("depositeSid","1002");
        payInfoMap.put("thirdPaymentNo","SWQ10000000001");
        payObservable.addPayQueue(payInfoMap,payObservable);
    }
    /**
     * 预加载服务
     */
//    @Before
//    public void setup() {
//        // 使用父类方法暴露provider
//        super.setProvider(ResVmService.class);
//    }
    @Test
    public void swqBaseTest(){

//        String json = "{\"region\":\"A1\"}";
//        try {
//            Map<String,Object> configNameMap = JsonUtil.fromJson(json, HashMap.class);
//            System.out.println("============================="+configNameMap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String configName = "{\"region\":\"A1\",\"zone\":\"A101\",\"instance\":\"yes\"," +
//                "\"systemDisk\":\"yes\",\"dataDisk\":\"yes\"}";
//        String configDetail = "{\"region\":\"A1\",\"zone\":\"A101\",\"cpu\":1,\"memory\":1,\"systemDisk\":60,\"dataDisk\":200}";

//        price = billingPlanService.getBillingPrice(100L,"YM",configName,configDetail);
        Calendar time = Calendar.getInstance();
        System.out.println("getActualMinimum=============="+time.getActualMinimum(Calendar.DAY_OF_MONTH));
        System.out.println("getActualMaximum=============="+time.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("getMinimum=============="+time.getMinimum(Calendar.DAY_OF_MONTH));
        System.out.println("getMaximum=============="+time.getMaximum(Calendar.DAY_OF_MONTH));

    }

    /**
     * 并发测试
     * @throws InterruptedException
     */
    @Test
    public void concurrentTest() throws InterruptedException {
        int count = 20;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count,new FinishTask());
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++)
            executorService.execute(new BillingServiceImplTest().new Task(cyclicBarrier,i));

        executorService.shutdown();
        Thread.sleep(60000);
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isMatched(String matchKey,List<BillingPlanSpec> billingPlanSpecs){
        BillingPlanSpec[] swq = (BillingPlanSpec[])billingPlanSpecs.toArray(new BillingPlanSpec[billingPlanSpecs.size()]);
        Observable<BillingPlanSpec> booleanObservable = Observable.from(swq);
        booleanObservable
                .flatMap(bps ->Observable.just(bps))
                .filter(bps -> {return matchKey.equals(bps.getSpecName());})
                .subscribe(billingPlanSpec -> {

                });

        for(BillingPlanSpec bps:billingPlanSpecs){
            if(matchKey.equals(bps.getSpecName()))
                return true;
        }
        return false;
    }

    @Test
    public void rxJavaMapTest(){
        List<Map<String,String>> mapList = new ArrayList<>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("cpu","1");
        map1.put("memory","2");
        Map<String,String> map2 = new HashMap<>();
        map2.put("cpu","1");
        map2.put("memory","2");
        mapList.add(map1);
        mapList.add(map2);

        Observable<Map<String,String>> mapObservable = Observable.from(mapList.toArray(new Map[mapList.size()]));
        mapObservable.flatMap(map -> Observable.from(map.entrySet()))
                .filter(entry -> entry.getKey().equals("cpu")).subscribe(subentry ->{
            System.out.println("---------"+subentry.getKey()+"---"+subentry.getValue());
        });

    }

    @Test
    public void rxJavaTest(){
//        Observable<String> myObservable = Observable.just("swq,Test");
//        myObservable.map(s->{return s+" operator 1";}).subscribe(onNextAction,onErrorAction,onCompletedAction);
        Observable<String> myObservable = Observable.from(new String[]{"swq1","swq2","swq3"});
        myObservable.flatMap(srt ->Observable.just(srt))
                .filter(str -> str.equals("swq1")).subscribe(sttr ->{
            System.out.println("==========================="+sttr);
        });

        BillingPlanSpecMapper billingPlanSpecMapper = SpringContextHolder.getBean("billingPlanSpecMapper");
        Criteria para = new Criteria();
        para.put("billingPlanSid",1004);
        List<BillingPlanSpec> billingPlanSpecs = billingPlanSpecMapper.selectByParams(para);

        this.querySpecMaps()
                .flatMap(map -> Observable.from(map.entrySet()))
                .filter(entry ->{
                    return isMatched(entry.getKey(),billingPlanSpecs);
                })
                .flatMap(entry -> {
                    Map<String,Object> billingConfigMap = new HashMap<>();
                    List<Map<String,String>> specItems = (List<Map<String,String>>)entry.getValue();
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
                    billingConfigMap.put(entry.getKey(),filteredItems);

                    Observable<Map<String,Object>> map = Observable.just(billingConfigMap);
                    return map;
                }).subscribe(entry ->{
                     System.out.println("swq--------"+JsonUtil.toJson(entry));
               });

    }

    private Observable<Map<String,Object>> querySpecMaps(){
        String specJson = "{\n" +
                "\t\t\"region\": \"10\",\n" +
                "\t\t\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\t\t\"password\": \"\",\n" +
                "\t\t\"hostname\": \"test\",\n" +
                "\t\t\"instance\": [{\n" +
                "\t\t\t\"instanceCategory\": \"idc-S\",\n" +
                "\t\t\t\"cpu\": \"1\",\n" +
                "\t\t\t\"memory\": \"1\"\n" +
                "\t\t}],\n" +
                "\t\t\"systemDisk\": [{\n" +
                "\t\t\t\"systemDiskCategory\": \"cloud_efficiency\",\n" +
                "\t\t\t\"systemDiskSize\": \"40\",\n" +
                "\t\t\t\"systemDiskDevice\": \"/dev/xvda\"\n" +
                "\t\t}],\n" +
                "\t\t\"dataDisk\": [{\n" +
                "\t\t\t\"dataDiskCategory\": \"cloud_ssd\",\n" +
                "\t\t\t\"dataDiskSize\": \"100\",\n" +
                "\t\t\t\"dataDiskSnapshot\": \"\",\n" +
                "\t\t\t\"dataDiskDevice\": \"\",\n" +
                "\t\t\t\"dataDiskDeletewithinstance\": \"true\",\n" +
                "\t\t\t\"dataDiskInstanceId\": null\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"dataDiskCategory\": \"cloud_efficiency\",\n" +
                "\t\t\t\"dataDiskSize\": \"150\",\n" +
                "\t\t\t\"dataDiskSnapshot\": \"\",\n" +
                "\t\t\t\"dataDiskDevice\": \"\",\n" +
                "\t\t\t\"dataDiskDeletewithinstance\": \"true\",\n" +
                "\t\t\t\"dataDiskInstanceId\": null\n" +
                "\t\t}],\n" +
                "\t\t\"networkType\": \"vpc\",\n" +
                "\t\t\"networks\": [\"e581e6fa-efe3-11e5-9f4e-005056a50931\"],\n" +
                "\t\t\"bandwidth\": \"0\",\n" +
                "\t\t\"os\": {\n" +
                "\t\t\t\"osType\": \"public\",\n" +
                "\t\t\t\"os\": \"605cdc9a-585c-11e5-8dea-005056a5742a\"\n" +
                "\t\t},\n" +
                "\t\t\"keyPair\": \"ccd98605-77ba-11e5-b6e5-005056a52fbf\",\n" +
                "\t\t\"securityGroup\": \"ccd98605-77ba-11e5-b6e5-005056a52fbf\"\n" +
                "\t}";
        Map<String,Object>  specJsonMap = JsonUtil.fromJson(specJson,HashMap.class);
        return Observable.just(specJsonMap);
    }



    private Action1<String> onNextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            System.out.println("onNextAction----------------"+s);
        }
    };

    private Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable s) {
//            System.out.println("onErrorAction----------------"+s);
        }
    };

    private Action0 onCompletedAction = new Action0() {
        @Override
        public void call() {
//            System.out.println("onCompletedAction----------------");
        }
    };

    @Test
    public void billingPriceTest(){
        BigDecimal price = BigDecimal.ZERO;
//        String billingConfig = "{\n" +
//                "\t\"region\": \"10\",\n" +
//                "\t\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
//                "\t\"instance\": [{\n" +
//                "\t\t\"instanceCategory\": \"idc-S\",\n" +
//                "\t\t\"cpu\": \"1\",\n" +
//                "\t\t\"memory\": \"1\"\n" +
//                "\t}],\n" +
//                "\t\"systemDisk\": [{\n" +
//                "\t\t\"systemDiskCategory\": \"cloud_efficiency\",\n" +
//                "\t\t\"systemDiskSize\": \"40\"\n" +
//                "\t}],\n" +
//                "\t\"dataDisk\": [{\n" +
//                "\t\t\"dataDiskCategory\": \"cloud_ssd\",\n" +
//                "\t\t\"dataDiskSize\": \"100\"\n" +
//                "\t},\n" +
//                "\t{\n" +
//                "\t\t\"dataDiskCategory\": \"cloud_efficiency\",\n" +
//                "\t\t\"dataDiskSize\": \"150\"\n" +
//                "\t}]\n" +
//                "}";
        String billingConfig = "{\n" +
                "\t\t\"region\": \"10\",\n" +
                "\t\t\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\t\t\"instance\": [{\n" +
                "\t\t\t\"instanceCategory\": \"idc-N\",\n" +
                "\t\t\t\"cpu\": \"1\",\n" +
                "\t\t\t\"memory\": \"1\"\n" +
                "\t\t}],\n" +
                "\t\t\"systemDisk\": [{\n" +
                "\t\t\t\"systemDiskCategory\": \"cloud_efficiency\",\n" +
                "\t\t\t\"systemDiskSize\": \"30\"\n" +
                "\t\t}],\n" +
                "\t\t\"dataDisk\": []\n" +
                "\t}";
        price = billingPlanService.getBillingPrice(100L,"Month",billingConfig);
        System.out.println("============================="+price);
    }



    @Test
    public void billingPriceTestEIP(){
        BigDecimal price = BigDecimal.ZERO;
        String billingConfig = "{\n" +
                "\t\t\"region\": \"10\",\n" +
                "\t\t\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\t\t\"eip_bandwidth\": \"1\"\n" +
                "\t}";
        price = billingPlanService.getBillingPrice(106L,"Month",billingConfig);
        System.out.println("============================="+price);
    }

    @Test
    public void billingPriceTestCBS(){
        BigDecimal price = BigDecimal.ZERO;
        String billingConfig = "{\n" +
                "\t\t\"region\": \"10\",\n" +
                "\t\t\"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "\t\t\"dataDisk\": [{\n" +
                "\t\t\t\"dataDiskCategory\": \"cloud_normal\",\n" +
                "\t\t\t\"dataDiskSize\": \"20\"\n" +
                "\t\t}]\n" +
                "\t}";
        price = billingPlanService.getBillingPrice(105L,"Month",billingConfig);
        System.out.println("============================="+price);
    }

    @Test
    public void userCenterLineChart(){
        Map<String,Object> lineChartData = billingAccountService.getUserCenterLineChartData(1740L,"currentMonth");
        System.out.println("------------"+lineChartData.get("xAxisData"));
        System.out.println("------------"+lineChartData.get("seriesData"));
    }

    @Test
    public void testFormatJson(){
        String formatedJson = StringUtil.formatJson("[{\"unitPrice\":\"0\",\"moduleCode\":\"STORAGE_TYPE\",\"moduleValue\":\"SSD\",\"billingChargeType\":\"00\",\"moduleName\":\"块存储类型\"},{\"unitPrice\":\"10\",\"moduleCode\":\"DISK_SIZE\",\"moduleValue\":\"1~10*2\",\"billingChargeType\":\"01\",\"moduleName\":\"块存储容量\",\"initPrice\":\"10\"},{\"unitPrice\":\"0\",\"moduleCode\":\"STORAGE_PURPOSE\",\"moduleValue\":\"SYSTEM\",\"billingChargeType\":\"00\",\"moduleName\":\"快存储用途\"}]");
        System.out.println(formatedJson);
        List<Map<String,Object>> jsons = JsonUtil.fromJson(formatedJson,List.class);
        System.out.println(jsons.size());
    }

    @Test
    public void testPackageClassLoader(){
//      Set<Class<?>> classes =  ClassLoaderUtil.getClassesByPackage("com.h3c.idcloud.core.pojo");
//        System.out.println(classes);

//        ClassPathScanningCandidateComponentProvider provider =  new  ClassPathScanningCandidateComponentProvider( true );
//        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents( "com/h3c/idcloud/core/pojo" );
//        for (BeanDefinition beanDefinition : beanDefinitions) {
//            System.out.println("-------"+beanDefinition.getBeanClassName()
//                    +  "\t"  + beanDefinition.getResourceDescription()
//                    +  "\t"  + beanDefinition.getClass());
//        }
    }


    @Test
    public void testResRxCallback() throws InterruptedException {

        Observable.just("Hello!")
                .map(input -> {
//                    throw new RuntimeException();
                    return "asdasdasd";
                })
                .subscribe(
                        System.out::print,
                        error -> System.out.println("Error!")
                );

        System.out.println("测试调用开始--------------");
        Observable<String> test = this.resVmExecute(new Callback<String, String>() {
            @Override
            public String call(String param) {
                System.out.println("------"+param);
                return "我是testResRxCallback";
            }
        });
        test.subscribe(vmId -> {
            System.out.println("开始调用资源层重试vmId------"+vmId);
        }, throwable -> {
            System.out.println("资源层异常处理------");
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("调用结束------");
            }
        });

        System.out.println("测试调用调用结束--------------");
    }

    private Observable<String> resVmExecute(Callback<String,String> callback) throws InterruptedException {
        String callResult = callback.call("我是callback");
        System.out.println("callResult------"+callResult);
        String vmSid = "419db509-02d4-11e6-852e-0242ac110004";
        Thread.sleep(10000);
        return Observable.just(vmSid);
    }




    public class FinishTask implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            logger.info("---------------执行最终任务------------------------");
        }

    }

    public class Task implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private int i;

        public Task(CyclicBarrier cyclicBarrier,int i) {
            this.cyclicBarrier = cyclicBarrier;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                String orderId = "OD2016040100010";
                OrderMapper orderMapper = SpringContextHolder.getBean("orderMapper");
                Order order = orderMapper.selectOrderByOrderId(orderId);
                Criteria orderDetailParam = new Criteria();
                orderDetailParam.put("orderId", orderId);
                OrderDetailMapper orderDetailMapper = SpringContextHolder.getBean("orderDetailMapper");
                List<OrderDetail> orderDetails = orderDetailMapper.selectByParams(orderDetailParam);
                order.setOrderDetail(orderDetails);
                AuthUser authUser = new AuthUser();
                authUser.setAccount("swq");
                authUser.setUserSid(1725L);
                authUser.setMgtObjSid(1427L);
                order.setAuthUserInfo(authUser);

                // TODO 等待所有任务准备就绪 并发测试
                cyclicBarrier.await();
                OrderPayObservable orderPayObservable = OrderPayObservable.getOrderPayObservable();
                orderPayObservable.addOrderIdQueue(order,orderPayObservable);

//                PayObservable payObservable = PayObservable.getPayObservable();
//          		Map<String,String> payQueueMap = new HashMap<String, String>();
//          		payQueueMap.put("depositeSid", "11207");
//          		payQueueMap.put("thirdPaymentNo", "1222222220120123123123");
//          		payObservable.addPayQueue(payQueueMap,payObservable);

//                  String url = "http://127.0.0.1:8080/xrender-ws/rest/billingOperation/executeDepositeOperation";
////                  String url = "http://10.22.205.101/longrender-ws/rest/billingOperation/executeDepositeOperation";
//  				Map<String,String> para = new HashMap<String, String>();
//  				String trade_no = "20150528000010710054663469";
////  				para.put("depositeSid", "11296");
//  				para.put("depositeSid", "2");
//  				para.put("tradeNo", trade_no);
//  				para.put("tradeNoPrefix", trade_no.substring(0, trade_no.length()/2));
//  				para.put("tradeNoSuffix", trade_no.substring(trade_no.length()/2,trade_no.length()));
//  				RESTHttpResponse str = RSETClientUtil.post(url, JsonUtil.toJson(para));
//  				System.out.println("==============================="+str.getContent());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



}
