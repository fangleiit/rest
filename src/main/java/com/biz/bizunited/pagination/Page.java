package com.biz.bizunited.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 分页
 * @Description: 
 * @ClassName: com.fashionshow.common.pagination.Page
 * @author: Omar(zp)
 * @date: 2015年8月15日 下午9:14:18 
 *
 */
public class Page<T> implements Serializable{
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/** 内容*/
	@JsonProperty
	private List<T> rows = new ArrayList<T>();
	
	/** 总条数*/
	@JsonProperty
	private long total = 0;
	
	/** 分页信息*/
	@JsonIgnore
	private Pageable pageable = new Pageable();
	
	/** 查询条件*/
	@JsonIgnore
	private Map<String,Object> queryCondition = new HashMap<String,Object>();
	
	/** 搜索*/
	@JsonIgnore
	private CommonSearch searchCondition;
	
	/** 排序*/
	@JsonIgnore
	private CommonSort sortCondition;


	public Page() {
		
	}
	
	public Page(Pageable pageable) {
		super();
		this.pageable = pageable;
	}


	public Page(List<T> rows, Pageable pageable) {
		super();
		this.rows = rows;
		this.pageable = pageable;
	}


	public Page(List<T> rows, long total, Pageable pageable) {
		super();
		this.rows = rows;
		this.total = total;
		this.pageable = pageable;
	}



	/**
	 * 获取  内容
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * 获取  总条数
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * 设置 内容
	 * @param rows the 内容 to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 * 设置 总条数
	 * @param total the 总条数 to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * 获取  分页信息
	 * @return the pageable
	 */
	public Pageable getPageable() {
		return pageable;
	}

	/**
	 * 设置 分页信息
	 * @param pageable the 分页信息 to set
	 */
	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	/**
	 * 获取  查询条件
	 * @return the queryCondition
	 */
	public Map<String, Object> getQueryCondition() {
		if(getPageable() != null && MapUtils.isNotEmpty(getPageable().getQueryCondition())) {
			queryCondition = getPageable().getQueryCondition();
		}
		return queryCondition;
	}

	/**
	 * 搜索 条件
	 * @return the searchCondition
	 */
	public CommonSearch getSearchCondition() {
		if(getPageable() != null && getPageable().getSearchCondition() != null) {
			CommonSearch commonSearch = getPageable().getSearchCondition();
			searchCondition = commonSearch;
		}
		return searchCondition;
	}

	/**
	 * 排序条件
	 * @return the sortCondition
	 */
	public CommonSort getSortCondition() {
		if(getPageable() != null && getPageable().getSortCondition() != null) {
			CommonSort commonSort = getPageable().getSortCondition();
			sortCondition = commonSort;
		}
		return sortCondition;
	}
}
