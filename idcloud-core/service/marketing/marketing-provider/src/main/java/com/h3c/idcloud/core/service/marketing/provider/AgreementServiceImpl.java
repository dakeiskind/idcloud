package com.h3c.idcloud.core.service.marketing.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.marketing.dao.AgreementMapper;
import com.h3c.idcloud.core.pojo.dto.marketing.Agreement;
import com.h3c.idcloud.core.service.marketing.api.AgreementService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 大客户协议service实现类
 * @author  chong.wu
 */
@Service(version = "1.0.0")
@Component
public class AgreementServiceImpl implements AgreementService {
    @Autowired
    private AgreementMapper agreementMapper;

    private static final Logger logger = LoggerFactory.getLogger(AgreementServiceImpl.class);

    /**
     * 根据条件查询记录总数
     * @param example
     * @return
     */
    public int countByParams(Criteria example) {
        int count = this.agreementMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    /**
     * 根据主键查询记录
     * @param agreementSid
     * @return
     */
    public Agreement selectByPrimaryKey(Long agreementSid) {
        return this.agreementMapper.selectByPrimaryKey(agreementSid);
    }

    /**
     * 根据条件查询记录集
     * @param example
     * @return
     */
    public List<Agreement> selectByParams(Criteria example) {
        return this.agreementMapper.selectByParams(example);
    }

    /**
     * 根据主键删除记录
     * @param agreementSid
     * @return
     */
    public int deleteByPrimaryKey(Long agreementSid) {
        return this.agreementMapper.deleteByPrimaryKey(agreementSid);
    }

    /**
     * 根据主键更新属性不为空的记录
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(Agreement record) {
//    	WebUtil.prepareUpdateParams(record);
        return this.agreementMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据主键更新记录
     * @param record
     * @return
     */
    public int updateByPrimaryKey(Agreement record) {
        return this.agreementMapper.updateByPrimaryKey(record);
    }

    /**
     * 根据条件删除记录
     * @param example
     * @return
     */
    public int deleteByParams(Criteria example) {
        return this.agreementMapper.deleteByParams(example);
    }

    /**
     * 根据条件更新属性不为空的记录
     * @param record
     * @param example
     * @return
     */
    public int updateByParamsSelective(Agreement record, Criteria example) {
        return this.agreementMapper.updateByParamsSelective(record, example.getCondition());
    }

    /**
     * 根据条件更新记录
     * @param record
     * @param example
     * @return
     */
    public int updateByParams(Agreement record, Criteria example) {
        return this.agreementMapper.updateByParams(record, example.getCondition());
    }

    /**
     * 保存记录,不管记录里面的属性是否为空
     * @param record
     * @return
     */
    public int insert(Agreement record) {
        return this.agreementMapper.insert(record);
    }

    /**
     * 保存属性不为空的记录
     * @param record
     * @return
     */
    public int insertSelective(Agreement record) {
//    	WebUtil.prepareInsertParams(record);
        return this.agreementMapper.insertSelective(record);
    }   
	
	/**
	 * 删除大客户协议
	 */
	public boolean deleteAgreement(Long agreementSid) {
		boolean result = false;
		try {
			Agreement agreement = this.agreementMapper.selectByPrimaryKey(agreementSid);
			Criteria condition = new Criteria();
			condition.put("agreementSid", agreement.getAgreementSid());			
			int agreementResult = this.agreementMapper.deleteByPrimaryKey(agreementSid);
			if (agreementResult == 0) {
				result = false;
			} else {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}		
}