package com.h3c.idcloud.core.service.product.api;

import com.h3c.idcloud.core.pojo.dto.product.FreeResDisk;
import com.h3c.idcloud.core.pojo.dto.product.FreeResTotal;
import com.h3c.idcloud.core.pojo.dto.product.FreeRes;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface FreeResService {
    int countByParams(Criteria example);

    FreeRes selectByPrimaryKey(Long fresSid);

    List<FreeRes> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long fresSid);

    int updateByPrimaryKeySelective(FreeRes record);

    int updateByPrimaryKey(FreeRes record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(FreeRes record, Criteria example);

    int updateByParams(FreeRes record, Criteria example);

    int insert(FreeRes record);

    int insertSelective(FreeRes record);
    
    List<FreeRes> selectByFreeInstance(Criteria example);
    
    /**
     * 用于导出报表的查询
     * @param example
     * @return
     * @author liufujun
     */
    List<FreeRes> selectByParamsForFresReport(Criteria example);
    
    void createFresExcelForEMail(Criteria example);

	List<FreeResTotal> selectByParamsForRecoveryReport(Criteria criteria);
	
	void createRecoveryExcelForEMail(Criteria example);

	void createFresExcelForEMailTest(Criteria criteria);

	void createRecoveryExcelForEMailTest(Criteria criteria);

	void insertFresAndRel(Criteria criteria);

	List<FreeRes> selectByParamsForRecovery(Criteria criteria);
	
	public List<FreeResDisk> selectByParamsForFreeDisk(Criteria criteria);
	
	/**
	 * 通过服务实例id，取得闲置资源列表
	 * @param criteria
	 * @return
	 */
	List<FreeRes> selectByInstSid(Criteria criteria);

	/**
	 * 按业务名称条件查询并统计
	 * @param example
	 * @return
	 */
	List<FreeRes> selectByParamsForSum(Criteria example);
}