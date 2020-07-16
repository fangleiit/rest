package com.biz.bizunited.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataTransferVO{
	/**
	 * 表名称
	 */
	private String tableName;
	/**
	 * 动作，添加，修改
	 */
	private String action;
	/**
	 * 唯一标识名称
	 */
	private String uniqueName;
	/**
	 * 唯一标识值
	 */
	private String uniqueValue;
	private List<Map<String, Object>> columnNameAndValue = new ArrayList<>();
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Map<String, Object>> getColumnNameAndValue() {
		return columnNameAndValue;
	}
	public void setColumnNameAndValue(List<Map<String, Object>> columnNameAndValue) {
		this.columnNameAndValue = columnNameAndValue;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getUniqueValue() {
		return uniqueValue;
	}
	public void setUniqueValue(String uniqueValue) {
		this.uniqueValue = uniqueValue;
	}
}
