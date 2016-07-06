package com.h3c.idcloud.infrastructure.common.pojo;

import java.util.List;

/**
 * Grid返回对象
 * 
 * @author 刘洋
 */
public class BaseGridReturn {

	/**
	 * 总数据条擿
	 */
	private int totalRows;

	/**
	 * 显示数据列表
	 */
	private List<?> dataList;

	public BaseGridReturn() {
	}

	public BaseGridReturn(int totalRows, List<?> dataList) {
		this.totalRows = totalRows;
		this.dataList = dataList;
	}

	/**
	 * 取得总数据条擿
	 * 
	 * @return 总数据条擿
	 */
	public int getTotalRows() {
		return totalRows;
	}

	/**
	 * 设置总数据条擿
	 * 
	 * @param totalRows
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * 取得显示数据列表
	 * 
	 * @return data
	 */
	public List<?> getDataList() {
		return dataList;
	}

	/**
	 * 设置显示数据列表
	 * 
	 * @param dataList
	 */
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

}
