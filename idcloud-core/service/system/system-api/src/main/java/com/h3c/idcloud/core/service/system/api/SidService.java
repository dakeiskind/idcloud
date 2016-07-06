package com.h3c.idcloud.core.service.system.api;

import com.h3c.idcloud.core.pojo.dto.system.Sid;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface SidService {

	/**
	 * 取得最大的流水号
	 * 
	 * @param sidCategory
	 *            流水号类别
	 * @return
	 */
	public String getMaxSid(String sidCategory);

	/**
	 * 查询最大的流水号
	 * 
	 * @param sidCategory
	 *            流水号类别
	 * @return 最大的流水号
	 */
	public String searchMaxSid(String sidCategory);

	int countByParams(Criteria example);

	Sid selectByPrimaryKey(Long sid);

	List<Sid> selectByParams(Criteria example);

	int deleteByPrimaryKey(Long sid);

	int updateByPrimaryKeySelective(Sid record);

	int updateByPrimaryKey(Sid record);

	int deleteByParams(Criteria example);

	int updateByParamsSelective(Sid record, Criteria example);

	int updateByParams(Sid record, Criteria example);

	int insert(Sid record);

	int insertSelective(Sid record);
}