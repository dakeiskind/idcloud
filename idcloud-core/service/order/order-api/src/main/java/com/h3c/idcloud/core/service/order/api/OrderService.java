package com.h3c.idcloud.core.service.order.api;

import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.vo.order.UserOrderVo;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResFloatingIpService;
import com.h3c.idcloud.core.service.res.api.ResVdService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.BaseServiceObj;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface OrderService {
    int countByParams(Criteria example);

    Order selectByPrimaryKey(Long orderSid);

    List<Order> selectByParams(Criteria param);

    int deleteByPrimaryKey(Long orderSid);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Order record, Criteria example);

    int updateByParams(Order record, Criteria example);

    int insert(Order record);

    int insertSelective(Order record);
       
    boolean updateStatus(Order order);
    
    String getTenantAdminId(String ownerId);

    /**
     * 保存订单数据
     * @param models json映射参数
     * @param authUserInfo 用户信息
     * @return OrderID 订单ID
     * @throws RpcException
     */
    String createServiceOrder(ArrayList<Map<String, Object>> models, AuthUser authUserInfo) throws RpcException;

    /**
     * 订单支付服务接口，完成支付后创建服务实例数据跳过activiti流程步骤，最后调用资源服务进行开通
     * @param orderId
     * @return
     * @throws RpcException
     */
    boolean payServiceOrder(String orderId,AuthUser authUserInfo) throws RpcException;

    /**
     * 取消服务订单信息
     * @param orderId
     * @return BaseServiceObj
     */
    BaseServiceObj cancelServiceOrder(String orderId,AuthUser authUserInfo) throws RpcException;
    
    Map<String, Object> updateServiceOrder(ArrayList<Map<String, Object>> models) throws Exception;

    /**
     * 取消订单
     * 
     * @param orderSid
     * @return
     */
    boolean cancelOrder(Long orderSid);
    
    /**
     * 判断某个order下面的所有服务实例是否都是已退订的
     * 
     */
    
    boolean judgementAllServiceInstanceIsCanceled(String orderId);
    
    List<Order> selectByOrderStatusInMonth(Criteria example);
    List<Order> selectByOrderStatusInAll(Criteria example);

    /**
     * 用户中心订单列表
     * @param example
     * @return
     */
    List<UserOrderVo> selectPersonalOrderList(Criteria example);

    /**
     * 获取 ResVmService
     * @return
     */
    ResVmService getResVmService();

    /**
     * 获取 ResFloatingIpService
     * @return
     */
    ResFloatingIpService getResFloatingIpService();

    /**
     * 服务实例服务
     * @return
     */
    ServiceInstanceService getServiceInstanceService();

    /**
     * 获取云盘dubbo service
     * @return
     */
    ResVdService getResVdService();

}