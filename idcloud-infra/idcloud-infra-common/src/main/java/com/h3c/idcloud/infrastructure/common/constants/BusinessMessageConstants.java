package com.h3c.idcloud.infrastructure.common.constants;

/**
 * 业务异常代码定义
 * Created by swq on 3/3/2016.
 */
public class BusinessMessageConstants {

    /**
     * 操作拦截头描述定义
     */
    public interface ActionTrace{
        String COMMON_ARGS_IS_NULL= "common.action.trace.args.null";
        String ORDER_MANAGEMENT = "order.action.trace.title.management";
        String ORDER_CANCEL = "order.action.trace.title.cancel";
        String ORDER_SUBMIT = "order.action.trace.title.submit";
        String ORDER_PAY= "order.action.trace.title.pay";
    }

    /**
     * 订单相关业务消息
     */
    public interface OrderMessage {
        String CAN_NOT_FIND_ORDER_INFO = "order.business.msg.1001";
        String CAN_NOT_FIND_ORDER_DETAIL_INFO = "order.business.msg.1002";
        String ACCOUNT_BALANCE_ERROR = "order.business.msg.1003";
        String SPECIFICATION_FORMAT_ERROR = "order.business.msg.1004";
        String SERVICE_INSTANCE_CREATE_ERROR = "order.business.msg.1005";
        String VM_CREATE_ERROR = "order.business.msg.1006";
        String FLOW_START_ERROR = "order.business.msg.1007";
        String ERROR_KEY = "order.business.msg.1008";
        String ORDER_STATUS_ERROR = "order.business.msg.1009";
    }

    /**
     * 资费相关业务消息
     */
    public interface BillingMessage{
        String CAN_NOT_FIND_BILLING_PLAN = "billing.business.msg.1001";
        String CAN_NOT_FIND_BILLING_PRICE_CONFIG = "billing.business.msg.1002";
        String CAN_NOT_FIND_BILLING_PRICE_DETAIL_CONFIG = "billing.business.msg.1003";
    }


    /**
     * 充值相关业务消息
     */
    public interface PaymentMessage{
        String CAN_NOT_FIND_PAYMENT_RECORD = "payment.business.msg.1001";
    }

    public interface ServiceInstance{
        //无法找到服务实例id信息
        String CAN_NOT_FIND_SERVICE_INSTANCE = "service.instance.msg.1001";
        //无法找到服务实例与资源的关联信息
        String CAN_NOT_FIND_SERVICE_INSTANCE_RES = "service.instance.msg.1002";
    }
    /**
     * 云主机操作
     */
    public interface VmMessage{
        String VM_START="info.vm.start";
        String VM_STOP="info.vm.stop";
        String VM_RESTART="info.vm.restart";
        String VM_DELETE="info.vm.delete";
    }
    /**
     * 弹性公网IP
     */
    public interface EipMessage{
        String EIP_DELETE="info.eip.delete";
    }
}
