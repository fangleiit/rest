/**
 * @Description: 
 * @ClassName: com.biz.bizunited.pagination.PageParse
 * @author: Omar(OmarZhang)
 * @date: 2016年4月29日 下午8:45:52 
 */
package com.biz.bizunited.pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 该方法主要解决分页的问题
 * @Description: 
 * @ClassName: com.biz.bizunited.pagination.PageParse
 * @author: Omar(OmarZhang)
 * @param <T>
 * @date: 2016年4月29日 下午8:45:52 
 *
 */
public class DataTablesView<T> implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	/** 总记录条数*/
	private Long iTotalRecords;
	
	private Long iTotalDisplayRecords;
	
	private Long sEcho;
	
	/** 返回对象值*/
	private List<T> data;
	
	/**
	 * Page数据转换
	 * @param <T>
	 * @param <T>
	 * @Description: 
	 */
	public DataTablesView(Page<T> page) {
		if(page != null) {
			setiTotalDisplayRecords(page.getTotal());
			setiTotalRecords(page.getTotal());
			setData(page.getRows());
		}
	}

	/**
	 * @return the iTotalRecords
	 */
	public Long getiTotalRecords() {
		return iTotalRecords;
	}

	/**
	 * @param iTotalRecords the iTotalRecords to set
	 */
	public void setiTotalRecords(Long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	/**
	 * @return the iTotalDisplayRecords
	 */
	public Long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	/**
	 * @param iTotalDisplayRecords the iTotalDisplayRecords to set
	 */
	public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	/**
	 * @return the sEcho
	 */
	public Long getsEcho() {
		return sEcho;
	}

	/**
	 * @param sEcho the sEcho to set
	 */
	public void setsEcho(Long sEcho) {
		this.sEcho = sEcho;
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}
	

}
