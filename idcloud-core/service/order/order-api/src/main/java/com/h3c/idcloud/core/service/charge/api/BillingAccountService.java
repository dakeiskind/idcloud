package com.h3c.idcloud.core.service.charge.api;

import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.charge.ServiceBill;
import com.h3c.idcloud.core.pojo.vo.charge.DepositeVo;
import com.h3c.idcloud.core.pojo.vo.charge.ServiceBillVo;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface BillingAccountService {

    /**
     * 给账号扣款或充值接口
     *
     * @param accountSid 账号Sid
     * @param amount     充值或扣款金额（必须大于0）
     * @param flag       true:扣款	false:充值
     */
    boolean updateAccount(Long accountSid, BigDecimal amount, boolean flag) throws Exception;


    /**
     * 扣除账户款项
     *
     * @param userId 用户Sid
     * @param amount 扣除金额
     * @param type   扣除类型
     */
    boolean updateAccount(Long userId, BigDecimal amount, String type) throws RpcException;

    int countByParams(Criteria example);

    BillingAccount selectByUserId(Long userId);

    BillingAccount selectByPrimaryKey(Long accountSid);

    List<BillingAccount> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long accountSid);

    int updateByPrimaryKeySelective(BillingAccount record);

    int updateByPrimaryKey(BillingAccount record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(BillingAccount record, Criteria example);

    int updateByParams(BillingAccount record, Criteria example);

    int insert(BillingAccount record);

    int insertSelective(BillingAccount record);

    /**
     * 检测账户余额是否可扣除
     */
    boolean checkAccountBalance(BigDecimal amount, Long userId);

    /**
     * 查询用户所有的充值记录
     */
    List<DepositeVo> displayPaymentRecords(Criteria criteria);

    /**
     * 查询用户所有的账单记录信息
     */
    List<ServiceBillVo> displayBillRecords(Criteria criteria);

    /**
     * 查询日总额月总额信息
     * @param criteria
     * @return
     */
    List<ServiceBill> selectStatisticsAmountInfo(Criteria criteria);

    /**
     * 查询余额月份的每日花费信息
     * @param userSid
     * @param timeLine
     * @return
     */
    Map<String,Object> getUserCenterLineChartData(Long userSid,String timeLine);
}
