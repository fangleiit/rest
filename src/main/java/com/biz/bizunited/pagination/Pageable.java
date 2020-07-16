package com.biz.bizunited.pagination;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页
 * @Description: 
 * @ClassName: com.fashionshow.common.pagination.Pageable
 * @author: Omar(zp)
 * @date: 2015年8月15日 下午9:19:19 
 *
 */
public class Pageable implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6102605810901056372L;
	

	/** 默认页码 */
	private static final int DEFAULT_PAGE_NUMBER = 1;

	/** 默认每页记录数 */
	private static final int DEFAULT_PAGE_SIZE = 5;
	
	private static final int MAX_PAGE_SIZE = 100;

	/** 页码 */
	private int page = DEFAULT_PAGE_NUMBER;

	/** 每页记录数 */
	private int rows = DEFAULT_PAGE_SIZE;
	
	/** 搜索*/
	private CommonSearch searchCondition;
	
	/** 排序*/
	private CommonSort sortCondition;
	
	/** 查询条件*/
	private Map<String,Object> queryCondition = new HashMap<String,Object>();
	
	public Pageable() {
		
	}
	
	/**
	 * 初始化一个新创建的Pageable对象
	 * 
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            每页记录数
	 */
	public Pageable(Integer page, Integer rows) {
		if (page != null && page >= 1) {
			//this.page = page;
			//此处修改为了整合分页控件
			this.page = (page / rows) + 1;
		}
		if (rows != null && rows >= 1 && rows <= MAX_PAGE_SIZE) {
			this.rows = rows;
		}
	}
	
	/**
	 * 初始化一个新创建的Pageable对象
	 * 
	 * @param pageNumber 页码
	 * @param pageSize  每页记录数
	 * @param {@link #com.biz.bizunited.pagination.CommonSearch()} 搜索
	 * @param {@link #com.biz.bizunited.pagination.CommonSort()} 排序
	 */
	public Pageable(Integer page, Integer rows,CommonSearch commonSearch,CommonSort commonSort) {
		if (page != null && page >= 1) {
			//this.page = page;
			//此处修改为了整合分页控件
			this.page = (page / rows) + 1;
		}
		if (rows != null && rows >= 1 && rows <= MAX_PAGE_SIZE) {
			this.rows = rows;
		}
		this.searchCondition = commonSearch;
		this.sortCondition = commonSort;
	}


	/**
	 * 设置 页码
	 * @param pageNumber the 页码 to set
	 */
	public void setPage(int page) {
		if (page < 1) {
			page = DEFAULT_PAGE_NUMBER;
		}
		this.page = page;
	}

	/**
	 * 设置 每页记录数
	 * @param pageSize the 每页记录数 to set
	 */
	public void setRows(int rows) {
		if (rows < 1 || rows > MAX_PAGE_SIZE) {
			rows = DEFAULT_PAGE_SIZE;
		}
		this.rows = rows;
	}

	/**
	 * 获取  查询条件
	 * @return the queryCondition
	 */
	public Map<String, Object> getQueryCondition() {
		return queryCondition;
	}

	/**
	 * 设置 查询条件
	 * @param queryCondition the 查询条件 to set
	 */
	public void setQueryCondition(Map<String, Object> queryCondition) {
		this.queryCondition = queryCondition;
	}

	/**
	 * 获取  页码
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 获取  每页记录数
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return the searchCondition
	 */
	public CommonSearch getSearchCondition() {
		return searchCondition;
	}

	/**
	 * @param searchCondition the searchCondition to set
	 */
	public void setSearchCondition(CommonSearch searchCondition) {
		this.searchCondition = searchCondition;
	}

	/**
	 * @return the sortCondition
	 */
	public CommonSort getSortCondition() {
		return sortCondition;
	}

	/**
	 * @param sortCondition the sortCondition to set
	 */
	public void setSortCondition(CommonSort sortCondition) {
		this.sortCondition = sortCondition;
	}
	
}
