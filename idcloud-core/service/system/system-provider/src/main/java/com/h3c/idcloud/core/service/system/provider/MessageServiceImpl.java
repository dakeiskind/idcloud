package com.h3c.idcloud.core.service.system.provider;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.MessageMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.marketing.Coupon;
import com.h3c.idcloud.core.pojo.dto.system.Message;
import com.h3c.idcloud.core.pojo.dto.user.SystemMessage;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.system.api.MessageService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infra.security.AuthService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    /**
     * 消息Mapper
     */
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 用户Service
     */
    @Reference(version = "1.0.0")
    private UserService userService;

    @Reference(version = "1.0.0")
    private BillingAccountService billingAccountService;

    @Autowired
    private AuthService authService;

    /**
     * 根据条件查询记录总数
     *
     * @return 记录总数
     */
    @Override
    @Transactional
    public int countByParams(Criteria example) {
        int count = this.messageMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    /**
     * 根据主键查询记录
     */
    @Override
    @Transactional
    public Message selectByPrimaryKey(Long msgSid) throws NotFoundException {
        return this.messageMapper.selectByPrimaryKey(msgSid);
    }

    /**
     * 根据条件查询记录集
     */
    @Override
    @Transactional
    public List<Message> selectByParams(Criteria example) {
        return this.messageMapper.selectByParams(example);
    }

    /**
     * 根据主键删除记录
     */
    @Override
    @Transactional
    public int deleteByPrimaryKey(Long msgSid) {
        return this.messageMapper.deleteByPrimaryKey(msgSid);
    }

    /**
     * 根据主键更新属性不为空的记录
     */
    @Override
    @Transactional
    public int updateByPrimaryKeySelective(Message record) {
        return this.messageMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据主键更新记录
     */
    @Override
    @Transactional
    public int updateByPrimaryKey(Message record) {
        return this.messageMapper.updateByPrimaryKey(record);
    }

    /**
     * 根据条件删除记录
     */
    @Override
    @Transactional
    public int deleteByParams(Criteria example) {
        return this.messageMapper.deleteByParams(example);
    }

    /**
     * 根据条件更新属性不为空的记录
     */
    @Override
    @Transactional
    public int updateByParamsSelective(Message record, Criteria example) {
        return this.messageMapper.updateByParams(record, example.getCondition());
    }

    /**
     * 根据条件更新记录
     */
    @Override
    @Transactional
    public int updateByParams(Message record, Criteria example) {
        return this.messageMapper.updateByParams(record, example.getCondition());
    }

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    @Override
    @Transactional
    public int insert(Message record) {
        return this.messageMapper.insert(record);
    }

    /**
     * 保存属性不为空的记录
     */
    @Override
    @Transactional
    public int insertSelective(Message record) {
        return this.messageMapper.insertSelective(record);
    }

    /**
     * 根据主键批量删除
     */
    @Override
    @Transactional
    public int deleteBatchByPks(List<String> msgSids) {
        return this.messageMapper.deleteBatchByPks(msgSids);
    }

    /**
     * 根据主键批量更新
     */
    @Override
    @Transactional
    public int updateBatchByPks(List<Message> messages) {
        return this.messageMapper.updateBatchByPks(messages);
    }

    /**
     * 批量插入消息
     */
    @Override
    @Transactional
    public int insertBatch(List<Message> messages) {
        return this.messageMapper.insertBatch(messages);
    }

    /**
     * 插入系统消息
     */
    @Override
    @Transactional
    public int insertSystemMessages(SystemMessage systemMessage) {
        //1. find users
        Criteria criteria = new Criteria();
        if (!StringUtil.isNullOrEmpty(systemMessage.getUserGroup())) {
            criteria.put("userGroup", systemMessage.getUserGroup());
        } else if (systemMessage.getLevelSid() != null && systemMessage.getLevelSid().length > 0) {
//            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("accountLevelSidArray",systemMessage.getLevelSid());
//            Criteria criterial = new Criteria();
//            criterial.setCondition(hashMap);
            List<BillingAccount> accounts = billingAccountService.selectByParams(new Criteria().put("accountLevelSidArray", systemMessage.getLevelSid()));
            List<Long> accountSids = new ArrayList<Long>();
            for (BillingAccount account : accounts) {
                accountSids.add(account.getAccountSid());
            }
            if (accountSids.size() > 0) {
                criteria.put("accountSidList", accountSids);
            }
        }
        criteria.put("status", WebConstants.UserStatus.AVAILABILITY);
        List<User> users = userService.selectByParams(criteria);

        //2. make messages
        List<Message> messages = new ArrayList<Message>();
        String msgContent = WebUtil.getMessage(systemMessage.getMsgId(), systemMessage.getParams());
        for (User u : users) {
            Message message = new Message();
            message.setMsgTitle("消息提示");
            message.setMsgContent(msgContent);
            message.setToUser(u.getAccount());
            message.setFromUser("Admin");
            message.setSendDate(new Date());
            message.setReadFlag("02");  // unread
            message.setMsgType("01");  // system message

            messages.add(message);
        }

        //3. batch insert

        if (users.size() > 0)
            return insertBatch(messages);
        else return -1;
    }

    /**
     * 插入优惠券消息
     *
     * @return 插入信息的条数
     */
    @Override
    public int insertCouponMessages(Coupon coupon) {
        SystemMessage systemMessage = new SystemMessage();
        if (StringUtils.isNotEmpty(coupon.getUserLevel())) {
            systemMessage.setLevelSid(new Long[]{Long.valueOf(coupon.getUserLevel())});
        }
        if (StringUtils.isNotEmpty(coupon.getUserGroup())) {
            systemMessage.setUserGroup(coupon.getUserGroup());
        }
        systemMessage.setMsgId(WebConstants.MsgCd.SYSTEM_MESSAGE_COUPON);
        systemMessage.setParams(new Object[]{
                coupon.getCouponCode(),
                StringUtil.dateFormat(coupon.getValidStartDt(), StringUtil.DF_YMD_24),
                StringUtil.dateFormat(coupon.getValidToDt(), StringUtil.DF_YMD_24),
                coupon.getDiscountRate()
        });
        return insertSystemMessages(systemMessage);
    }
}