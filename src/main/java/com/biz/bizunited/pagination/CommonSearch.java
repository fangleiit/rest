/**
 * @Description: 
 * @ClassName: com.biz.bizunited.pagination.CommonSearch
 * @author: Omar(OmarZhang)
 * @date: 2016年5月5日 下午3:15:45 
 */
package com.biz.bizunited.pagination;

import java.io.Serializable;

/**
 * 搜索
 * @Description: 
 * @ClassName: com.biz.bizunited.pagination.CommonSearch
 * @author: Omar(OmarZhang)
 * @date: 2016年5月5日 下午3:15:45 
 *
 */
public class CommonSearch implements Serializable{
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/** 搜索的值*/
	private String value;
	/** 搜索的正则表达式*/
	private Boolean regex;
	
	/**
	 * @Description: 
	 */
	public CommonSearch() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @Description: 
	 * @param value 搜索的值
	 * @param regex 是否开启正则表达式
	 */
	public CommonSearch(String value, Boolean regex) {
		super();
		this.value = value;
		this.regex = regex;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the regex
	 */
	public Boolean getRegex() {
		return regex;
	}

	/**
	 * @param regex the regex to set
	 */
	public void setRegex(Boolean regex) {
		this.regex = regex;
	}

}
