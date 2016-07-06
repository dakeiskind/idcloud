package com.h3c.idcloud.core.service.charge.api;

import java.util.Map;

import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.pojo.dto.charge.Deposite;

/**
 * 
 * 在线充值平台操作服务类，整个充值流程如下
 * 用户充值--》插入未支付的充值记录--》进入第三方平台支付交易---》交易成功----》更新对应充值记录状态“已支付”---》更新当前用户余额表----》充值流程完成  
 *                                         ---》交易失败，不进行任何处理，平台不提供“未支付”状态的充值记录进行重新支付的操作，页面过滤掉“未支付”的充值记录。
 * @author swq
 *
 */
public interface PlatformOnlineRechargeOpService {


	/**
	 * 当在第三方充值成功后调用此方法，更新充值记录状态，增加积分，奖励优惠赠送，更新用户等级以及当前用户余额信息
	 * 此接口方法内部实现为观察者队列，专为rest方法使用
	 * @param payInfoMap
	 * @return key1/充值状态  isSuccess true/false      key2/充值金额  amount  key3/赠送金额   gift
     */
	public Map<String,String> executeDepositeOperationForRest(Map<String,String> payInfoMap);
	
	/**
	 * 创建充值记录状态为“未支付”的数据信息，
	 * 返回充值记录sid作为唯一标识用户第三方支付系统
	 * 
	 * @param deposite 充值记录bean
	 * @return 充值记录sid
	 * @throws RpcException
	 */
	public Long saveDepositeRecordInfo(Deposite deposite);
	
	
	/**
	 * 当在第三方充值成功后调用此方法，更新充值记录状态，增加积分，奖励优惠赠送，更新用户等级以及当前用户余额信息
	 * 此方法不可直接使用！外部调用使用executeDepositeOperationForRest
	 * @param depositeSid 充值记录sid/商户唯一订单号
	 * @param thirdPaymentNo 第三方支付交易号
	 * @return  key1/充值状态  isSuccess true/false      key2/充值金额  amount  key3/赠送金额   gift
	 * @throws RpcException
	 */
	public Map<String,String> executeDepositeOperation(Long depositeSid, String thirdPaymentNo);

}
